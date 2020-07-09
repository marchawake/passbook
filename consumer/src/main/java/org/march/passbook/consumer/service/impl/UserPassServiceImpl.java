package org.march.passbook.consumer.service.impl;

import com.alibaba.fastjson.JSON;
import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.constant.PassStatus;
import org.march.passbook.consumer.dao.MerchantsDao;
import org.march.passbook.consumer.entity.Merchants;
import org.march.passbook.consumer.mapper.PassTableRowMapper;
import org.march.passbook.consumer.service.IUserPassService;
import org.march.passbook.consumer.utils.CommonDateFormatUtil;
import org.march.passbook.consumer.vo.Pass;
import org.march.passbook.consumer.vo.PassInfo;
import org.march.passbook.consumer.vo.PassTemplate;
import org.march.passbook.consumer.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户优惠券相关功能接口实现
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
@Service
public class UserPassServiceImpl implements IUserPassService {

    /** HBase 客户端 */
    private final HbaseTemplate hbaseTemplate;

    /** MerchantsDao 访问 MySQL */
    private final MerchantsDao merchantsDao;

    @Autowired
    public UserPassServiceImpl(HbaseTemplate hbaseTemplate, MerchantsDao merchantsDao) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
    }

    @Override
    public Response getUserPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.UNUSED);
    }

    @Override
    public Response getUserUsedPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId, PassStatus.USED);
    }

    @Override
    public Response getUserAllPassInfo(Long userId) throws Exception {
        return getPassInfoByStatus(userId,PassStatus.ALL);
    }

    @Override
    public Response userUsePass(Pass pass) {

        /** 根据 userId 构建行键的前缀 */
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(pass.getUserId()))
                        .reverse()
                        .toString()
        );

        /** 创建扫描器 */
        Scan scan = new Scan();

        FilterList filterList = new FilterList();

        filterList.addFilter(new PrefixFilter(rowPrefix));
        filterList.addFilter(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.PASS_TEMPLATE_ID.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes(pass.getPassTemplateId())
        ));
        filterList.addFilter(new SingleColumnValueFilter(
                Constants.PassTable.FAMILY_I.getBytes(),
                Constants.PassTable.CONSUMER_DATE.getBytes(),
                CompareFilter.CompareOp.EQUAL,
                Bytes.toBytes("-1")
        ));

        /** 设置过滤器 */
        scan.setFilter(filterList);

        List<Pass> passList = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassTableRowMapper());

        if (null == passList || passList.size() == 0) {
            log.error("UserUsePass Error :{}", JSON.toJSONString(pass));
            return Response.failure("UserUsePass Error");
        }

        /** 创建对应列的字节数组 */
        byte[] FAMILY_I = Bytes.toBytes(Constants.PassTable.FAMILY_I);
        byte[] CONSUMER_DATE = Bytes.toBytes(Constants.PassTable.CONSUMER_DATE);

        Put put = new Put(Bytes.toBytes(passList.get(0).getId()));

        put.addColumn(
                FAMILY_I,
                CONSUMER_DATE,
                Bytes.toBytes(CommonDateFormatUtil.getFormatDate(new Date()))
        );

        hbaseTemplate.saveOrUpdate(Constants.PassTable.TABLE_NAME, put);

        return new Response();
    }

    /**
     * 根据优惠券状态获取优惠券信息
     * @param userId 用户id
     * @param status {@link PassStatus}
     * @return {@link Response}
     */
    private Response getPassInfoByStatus(Long userId, PassStatus status) throws Exception {

        /**根据 userId 构造行键前缀 */
        byte[] rowPrefix = Bytes.toBytes(new StringBuilder(String.valueOf(userId)).reverse().toString());

        Scan scan = new Scan();

        FilterList filterList = new FilterList();

        /** 行键前缀过滤器，找到特定用户的优惠券 */
        filterList.addFilter(new PrefixFilter(rowPrefix));

        /** 基于列单元值的过滤器，找到未使用的优惠券 */
        if (status != PassStatus.ALL) {

            CompareFilter.CompareOp compareOp =
                    status == PassStatus.UNUSED ? CompareFilter.CompareOp.EQUAL : CompareFilter.CompareOp.NOT_EQUAL;

            filterList.addFilter(new SingleColumnValueFilter(
                    Bytes.toBytes(Constants.PassTable.FAMILY_I),
                    Bytes.toBytes(Constants.PassTable.CONSUMER_DATE),
                    compareOp,
                    Bytes.toBytes("-1")
            ));
        }

        /** 扫描过滤 */
        scan.setFilter(filterList);

        /** 通过扫描获取 List<Pass> */
        List<Pass> passList = hbaseTemplate.find(Constants.PassTable.TABLE_NAME, scan, new PassTableRowMapper());

        /** 通过 PassList 获取 passTemplateMap */
        Map<String, PassTemplate> passTemplateMap = buildPassTemplateMap(passList);

        /** 通过 PassTemplateList 获取 MerchantsMap */
        Map<Integer, Merchants> merchantsMap = buildMerchantsMap(
                new ArrayList<>(passTemplateMap.values())
        );

        List<PassInfo> result = new ArrayList<PassInfo>();

        /** 通过遍历 passList 构建 PassInfo 对象存在到 List 中 */
        for (Pass pass : passList) {
            PassTemplate passTemplate = passTemplateMap.getOrDefault(pass.getPassTemplateId(), null);
            if (null == passTemplate) {
                log.error("PassTemplate Null :{}",pass.getPassTemplateId());
                continue;
            }
            Merchants merchants = merchantsMap.getOrDefault(passTemplate.getMerchantsId(), null);
            if (null == merchants) {
                log.error("Merchants Null :{}",passTemplate.getMerchantsId());
                continue;
            }
            result.add(new PassInfo(pass, passTemplate, merchants));
        }

        return new Response(result);
    }

    /**
     * <h2>通过获取的 PassList 对象构造 PassTemplate对象 Map</h2>
     * @param passList {@link Pass}
     * @return Map {@link PassTemplate}
     */
    private Map<String, PassTemplate> buildPassTemplateMap(List<Pass> passList) throws Exception {


        /** 创建列对应的字节数组 */
        byte[] FAMILY_B = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B);
        byte[] ID = Bytes.toBytes(Constants.PassTemplateTable.MERCHANTS_ID);
        byte[] TITLE = Bytes.toBytes(Constants.PassTemplateTable.TITLE);
        byte[] SUMMARY = Bytes.toBytes(Constants.PassTemplateTable.SUMMARY);
        byte[] DESC = Bytes.toBytes(Constants.PassTemplateTable.DESC);
        byte[] HAS_TOKEN = Bytes.toBytes(Constants.PassTemplateTable.HAS_TOKEN);
        byte[] BACKGROUND = Bytes.toBytes(Constants.PassTemplateTable.BACKGROUND);
        byte[] FAMILY_C = Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C);
        byte[] LIMIT = Bytes.toBytes(Constants.PassTemplateTable.LIMIT);
        byte[] START = Bytes.toBytes(Constants.PassTemplateTable.START);
        byte[] END = Bytes.toBytes(Constants.PassTemplateTable.END);

        /** 通过 passList 获取到所有 Pass 的 id */
        List<String> templateIdList = passList.stream().map(
                Pass::getPassTemplateId
        ).collect(Collectors.toList());

        List<Get> templateGet = new ArrayList<>(templateIdList.size());

        /** 通过遍历 templateIdList 创建对应 id 的字节数组存放在 templateGet 中 */
        templateIdList.forEach(t -> templateGet.add(new Get(Bytes.toBytes(t))));

        /** 根据 templateGet 获取 HBase 数据 */
        Result[] templateResults = hbaseTemplate.getConnection().getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                .get(templateGet);

        /** 构造 PassTemplateId -> PassTemplate Object 的 Map, 用于构造PassInfo */
        Map<String, PassTemplate> passTemplateMap = new HashMap<>();

        for (Result item : templateResults
             ) {

            PassTemplate passTemplate = new PassTemplate();
            passTemplate.setMerchantsId(Bytes.toInt(item.getValue(FAMILY_B, ID)));
            passTemplate.setTitle(Bytes.toString(item.getValue(FAMILY_B, TITLE)));
            passTemplate.setSummary(Bytes.toString(item.getValue(FAMILY_B, SUMMARY)));
            passTemplate.setDesc(Bytes.toString(item.getValue(FAMILY_B, DESC)));
            passTemplate.setHasToken(Bytes.toBoolean(item.getValue(FAMILY_B, HAS_TOKEN)));
            passTemplate.setBackground(Bytes.toInt(item.getValue(FAMILY_B,BACKGROUND)));
            passTemplate.setLimit(Bytes.toLong(item.getValue(FAMILY_C, LIMIT)));
            passTemplate.setStart(CommonDateFormatUtil.getFormatDate(Bytes.toString(item.getValue(FAMILY_C, START))));
            passTemplate.setEnd(CommonDateFormatUtil.getFormatDate(Bytes.toString(item.getValue(FAMILY_C, END))));
            passTemplate.setId(Bytes.toString(item.getRow()));
            passTemplateMap.put(Bytes.toString(item.getRow()), passTemplate);

        }

        return passTemplateMap;
    }

    /**
     * 根据 PassTemplate 对象构造 Merchants Map
     * @param passTemplateList {@link List<PassTemplate>}
     * @return {@link Merchants}
     */
    private Map<Integer, Merchants> buildMerchantsMap(List<PassTemplate> passTemplateList) {

        Map<Integer, Merchants> merchantsMap = new HashMap<Integer, Merchants>();

        /** 获取所有 Merchants id 存放到 List 中 */
        List<Integer> merchantsIdList = passTemplateList.stream().map(
                PassTemplate::getMerchantsId
        ).collect(Collectors.toList());

        List<Merchants> merchantsList = merchantsDao.findByIdIn(merchantsIdList);

        for (Merchants merchants : merchantsList) {
            merchantsMap.put(merchants.getId(), merchants);
        }

        return merchantsMap;
    }
}
