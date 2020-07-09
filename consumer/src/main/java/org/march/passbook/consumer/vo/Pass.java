package org.march.passbook.consumer.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户领取的优惠券
 *
 * @author March
 * @date 2020/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pass {

    /** Pass 在 HBase 中的 RowKwy */
    private String id;

    /** 用户 id */
    private Long userId;

    /** PassTemplate 在 HBase 中的 RowKey */
    private String passTemplateId;

    /** 优惠券 Token 有可能为 Null, 则填充-1 */
    private String passToken;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    /** 领取优惠券时间 */
    private Date assignedDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    /** 优惠券使用时间 */
    private Date consumerDate;
}
