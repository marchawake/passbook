package org.march.passbook.consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 商户投放优惠券模板对象定义
 *
 * @author March
 * @date 2020/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassTemplate {

    /** PassTemplate 在 HBase 中的 RowKwy */
    private String id;

    /** 所属商户 id */
    private Integer merchantsId;

    /** 优惠券标题 */
    private String title;

    /** 优惠券摘要 */
    private String summary;

    /** 优惠券详细描述信息 */
    private String desc;

    /** 优惠券最大个数限制 无上限默认 -1*/
    private Long limit;

    /** 优惠券是否有token */
    private Boolean hasToken;

    /** 优惠券的背景颜色 */
    private Integer background;

    /** 优惠券生效时间 */
    private Date start;

    /** 优惠券截至时间 */
    private Date end;

}
