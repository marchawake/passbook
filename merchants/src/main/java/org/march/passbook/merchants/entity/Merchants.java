package org.march.passbook.merchants.entity;

import lombok.*;
import javax.persistence.*;

/**
 * 定义商户对象信息
 *
 * @author March
 * @date 2020/6/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchants {

    /** 商户id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "id", nullable = false)
    private Integer id;

    /** 商户名称*/
    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /** 商户名称*/
    @Basic
    @Column(name = "logo_url", nullable = false)
    private String logoUrl;

    /** 商户名称*/
    @Basic
    @Column(name = "business_license_url", nullable = false)
    private String businessLicenseUrl;

    /** 商户名称*/
    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    /** 商户名称*/
    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    /** 商户名称*/
    @Basic
    @Column(name = "is_audit", nullable = false)
    private Boolean isAudit = false;
}
