package org.march.passbook.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.omg.PortableInterceptor.INACTIVE;

import javax.persistence.*;

/**
 * 商户对象模型
 *
 * @author March
 * @date 2020/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {

    /** 商户 id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** 商户名称 */
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /** 商户 logo 地址*/
    @Basic
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    /** 商户营业执照 */
    @Basic
    @Column(name = "business_license_url")
    private String businessLicenseUrl;

    /** 商户联系电话 */
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /** 商户地址 */
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    /** 商户检验是否通过 */
    @Basic
    @Column(name = "is_audit", nullable = false)
    private Boolean isAudit;
}
