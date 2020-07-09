package org.march.passbook.consumer.service.impl;

import com.spring4all.spring.boot.starter.hbase.api.HbaseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.march.passbook.consumer.constant.Constants;
import org.march.passbook.consumer.service.IHBasePassService;
import org.march.passbook.consumer.utils.CommonDateFormatUtil;
import org.march.passbook.consumer.utils.RowKeyGenerateUtil;
import org.march.passbook.consumer.vo.PassTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;


/**
 * IHBasePassService 服务实现者
 *
 * @author March
 * @date 2020/6/19
 */
@Slf4j
@Service
public class HBasePassServiceImpl implements IHBasePassService {

    /** HBase 客户端 */
    private final HbaseTemplate hbaseTemplate;

    @Autowired
    public HBasePassServiceImpl(HbaseTemplate hbaseTemplate) {
        this.hbaseTemplate = hbaseTemplate;
    }

    @Override
    public boolean dropPassTemplateToHBase(PassTemplate template) {

        if (null == template) {
            return false;
        }

        /** 生成RowKey */
        String rowKey = RowKeyGenerateUtil.generatePassTemplateRowKey(template);

        try {
            if (hbaseTemplate.getConnection()
                    .getTable(TableName.valueOf(Constants.PassTemplateTable.TABLE_NAME))
                    .exists(new Get(Bytes.toBytes(rowKey)))
            ){

                log.warn("RowKey {} Is Already Exist", rowKey);
                return false;
            }
        } catch (IOException e) {
           log.error("DropPassTemplateToHBase Error:{}",e.getMessage());
           return false;
        }
         Put put = new Put(Bytes.toBytes(rowKey));

        /** 添加列信息 */

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                 Bytes.toBytes(Constants.PassTemplateTable.MERCHANTS_ID),
                 Bytes.toBytes(template.getMerchantsId()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.TITLE),
                Bytes.toBytes(template.getTitle()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.SUMMARY),
                Bytes.toBytes(template.getSummary()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.DESC),
                Bytes.toBytes(template.getDesc()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.HAS_TOKEN),
                Bytes.toBytes(template.getHasToken()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_B),
                Bytes.toBytes(Constants.PassTemplateTable.BACKGROUND),
                Bytes.toBytes(template.getBackground()));

        put.addColumn(Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.PassTemplateTable.LIMIT),
                Bytes.toBytes(template.getLimit()));

        put.addColumn(
                Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.PassTemplateTable.START),
                Bytes.toBytes(CommonDateFormatUtil.getFormatDate(template.getStart()))
        );

        put.addColumn(
                Bytes.toBytes(Constants.PassTemplateTable.FAMILY_C),
                Bytes.toBytes(Constants.PassTemplateTable.END),
                Bytes.toBytes(CommonDateFormatUtil.getFormatDate(template.getEnd()))
        );

        /** 保存或者更新操作 */
        hbaseTemplate.saveOrUpdate(Constants.PassTemplateTable.TABLE_NAME, put);

        return true;
    }
}
