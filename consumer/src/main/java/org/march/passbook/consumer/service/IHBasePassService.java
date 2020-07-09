package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.PassTemplate;

/**
 * Pass HBase 服务
 *
 * @author March
 * @date 2020/6/19
 */
public interface IHBasePassService {

    /**
     * 将 PassTemplate 写入 HBase 数据库
     * @param template {@link PassTemplate}
     * @return boolean
     */
    boolean dropPassTemplateToHBase(PassTemplate template);
}
