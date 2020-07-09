package org.march.passbook.consumer.log;

/**
 * 日志记录常量定义
 *
 * @author March
 * @date 2020/6/18
 */
public class LogConstants {

    /**
     * 用户动作名称
     */
    public class ActionName {

        /** 用户查看有效优惠券信息 */
        public static final String USER_PASS_INFO = "UserPassInfo";

        /** 用户查看已使用优惠券信息 */
        public static final String USER_USED_PASS_INFO = "UserUsedPassInfo";

        /** 用户查看所有优惠券信息 */
        public static final String USER_ALL_PASS_INFO = "UserAllPassInfo";

        /** 用户使用优惠券 */
        public static final String USER_USE_PASS = "UserUsedPass";

        /** 用户获取库存信息 */
        public static final String INVENTORY_INFO = "InventoryInfo";

        /** 用户领取优惠券 */
        public static final String GAIN_PASS_TEMPLATE = "GainPassTemplate";

        /** 用户创建评论 */
        public static final String CREATE_FEEDBACK = "CreateFeedback";

        /** 用户获取评论 */
        public static final String GET_FEEDBACK = "GetFeedback";

        /** 用户注册 */
        public static final String REGISTER = "Register";
    }
}
