package org.march.passbook.consumer.dao;

import org.march.passbook.consumer.entity.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商户数据访问接口定义
 *
 * @author March
 * @date 2020/6/18
 */
public interface MerchantsDao extends JpaRepository<Merchants, Integer> {

    /**
     * 根据商户名称获取商户对象信息
     * @param name {@link String}
     * @return {@link Merchants}
     */
    Merchants findByName(String name);

    /**
     * 根据商户ids 获取多个商户对象
     * @param ids {@link List<Integer>}
     * @return {@link List<Merchants>}
     */
    List<Merchants> findByIdIn(List<Integer> ids);
}
