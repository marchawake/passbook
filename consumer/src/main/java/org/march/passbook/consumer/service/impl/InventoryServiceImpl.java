package org.march.passbook.consumer.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.dao.MerchantsDao;
import org.march.passbook.consumer.entity.Merchants;
import org.march.passbook.consumer.mapper.PassTemplateTableRowMapper;
import org.march.passbook.consumer.service.IInventoryService;
import org.march.passbook.consumer.service.IUserPassService;
import org.march.passbook.consumer.utils.RowKeyGenerateUtil;
import org.march.passbook.consumer.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1>获取库存信息，只返回用户没有领取的优惠券</h1>
 *
 * @author March
 * @date 2020/6/19
 */
@Service
@Slf4j
public class InventoryServiceImpl implements IInventoryService {

    /** HBase 客户端接口 */
    private final HbaseTemplate hbaseTemplate;

    /** Merchants Dao 接口 */
    private final MerchantsDao merchantsDao;

    /** 用户优惠券服务接口 */
    private final IUserPassService userPassService;

    @Autowired
    public InventoryServiceImpl(HbaseTemplate hbaseTemplate,
                                MerchantsDao merchantsDao,
                                IUserPassService userPassService
    ) {
        this.hbaseTemplate = hbaseTemplate;
        this.merchantsDao = merchantsDao;
        this.userPassService = userPassService;
    }

    @Override
    public Response getInventoryInfo(Long userId) throws Exception {

        /** 获取当前用户拥有的所有优惠券 */
        Response userAllPassInfo = userPassService.getUserAllPassInfo(userId);

        List<PassInfo> passInfoList  = (List<PassInfo>) userAllPassInfo.getData();

        /** 需要排除的用户领取优惠券 */
        List<PassTemplate> excludePassTemplateList = passInfoList.stream().map(
                PassInfo::getPassTemplate
        ).collect(Collectors.toList());

        List<String> excludeIdList = new ArrayList<String>();

        /** 给需要排除的优惠券生成 id */
        excludePassTemplateList.forEach(passTemplate ->
                excludeIdList.add(RowKeyGenerateUtil.generatePassTemplateRowKey(passTemplate)));

        return new Response(new InventoryResponse(userId,
                buildPassTemplateInfo(getAvailablePassTemplate(excludeIdList))));
    }
    
    /**
     * <h2>获取系统中可用的优惠券</h2>
     * @param excludeIdList 需要排除的优惠券 ids(排除过期，排除未开始)
     * @return {@link PassTemplate}
     */
    private List<PassTemplate> getAvailablePassTemplate(List<String> excludeIdList) {

        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ONE);

        filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                CompareFilter.CompareOp.GREATER,
                Bytes.toBytes(0L)

        ));
        filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                        Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                        CompareFilter.CompareOp.EQUAL,
                        Bytes.toBytes("-1")
        ));

        Scan scan = new Scan();
        scan.setFilter(filterList);

        List<PassTemplate> validTemplates =
                hbaseTemplate.find(Constants.PassTemplateTable.TABLE_NAME, scan, new PassTemplateTableRowMapper());

        Date current = new Date();

        List<PassTemplate> availablePassTemplate = new ArrayList<PassTemplate>();

        /** 遍历获取有效的优惠券  */
        for (PassTemplate validTemplate : validTemplates
            ) {

            if (excludeIdList.contains(RowKeyGenerateUtil.generatePassTemplateRowKey(validTemplate))) {
                continue;
            }

            if (current.getTime() >= validTemplate.getStart().getTime()
                    && current.getTime() <= validTemplate.getEnd().getTime()) {
                availablePassTemplate.add(validTemplate);
            }
        }

        return availablePassTemplate;
    }

    /**
     * <h2>构建优惠券信息</h2>
     * @param passTemplateList {@link PassTemplate}
     * @return java.util.List<org.march.passbook.consumer.vo.PassTemplateInfo>
     */
    private List<PassTemplateInfo> buildPassTemplateInfo(List<PassTemplate> passTemplateList) {

        Map<Integer, Merchants> merchantsMap = new HashMap<Integer, Merchants>();

        /** 获取所有 passTemplateList 的商户 id */
        List<Integer> merchantsIdList = passTemplateList.stream().map(
                PassTemplate::getMerchantsId
        ).collect(Collectors.toList());

        List<Merchants> merchantsList = merchantsDao.findByIdIn(merchantsIdList);

        /** 将 merchantsList 添加到 merchantsMap 中 */
        merchantsList.forEach(merchants -> merchantsMap.put(merchants.getId(), merchants));

        List<PassTemplateInfo> result = new ArrayList<>();

        for (PassTemplate passTemplate : passTemplateList) {
            Merchants merchants = merchantsMap.getOrDefault(passTemplate.getMerchantsId(),null);

            if (null == merchants) {
                log.error("Merchants Error:{}",passTemplate.getMerchantsId());
                continue;
            }
            result.add(new PassTemplateInfo(passTemplate, merchants));
        }

        return result;
    }
 }
