package org.march.passbook.merchants.dao;

import org.march.passbook.merchants.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 商户数据访问接口
 *
 * @author March
 * @date 2020/6/17
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    /**
     * @param name {@link String}
     * @return {@link Merchants}
     */
    Merchants findByName(String name);
}
