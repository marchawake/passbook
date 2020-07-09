package org.march.passbook.consumer.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户对象定义
 * 对应用户 HBase 表
 * @author March
 * @date 2020/6/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /** 当前用户在 'pb:user'HBase 表的 rowKey */
    private Long id;

    /** 用户基本信息 */
    private BaseInfo baseInfo;

    /** 用户额外的信息 */
    private OtherInfo otherInfo;

    /**
     * 用户基本信息定义
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseInfo {

        /** 用户姓名 */
        private String name;

        /** 用户年龄 */
        private Integer age;

        /** 用户性别 */
        private String sex;
    }

    /**
     * 用户其他信息定义
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OtherInfo {

        /** 用户手机号码 */
        private String phone;

        /** 用户住址 */
        private String address;
    }
}
