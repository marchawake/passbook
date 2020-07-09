package org.march.passbook.consumer.constant;

/**
 * 通用常量的定义
 *
 * @author March
 * @date 2020/6/18
 */
public class Constants {

    /** 商户优惠券 Kafka Topic */
    public static final String TEMPLATE_TOPIC = "merchants-template";

    /** token 文件存储目录 */
    public static final String TOKEN_DIR = "/tmp/token/";

    /** 已使用的 token 文件名后缀*/
    public static final String USED_TOKEN_SUFFIX = "-";

    /** 用户数的 redis key */
    public static final String USER_COUNT_REDIS_KEY = "march-passbook-user-count";

    /**
     * 用户 HBase 表
     */
    public class UserTable{

        /** User HBase 表名*/
        public static final String TABLE_NAME = "pb:user";

        /** 基本信息列族 */
        public static final String FAMILY_B = "b";

        /** 用户名 */
        public static final String NAME = "name";

        /** 用户年龄 */
        public static final String AEG = "age";

        /** 用户性别 */
        public static final String SEX = "sex";

        /** 额外信息列族 */
        public static final String FAMILY_O = "o";

        /** 用户手机号码 */
        public static final String PHONE = "phone";

        /** 用户住址 */
        public static final String ADDRESS = "address";

    }

    /**
     * 优惠券 HBase 表
     */
    public class PassTemplateTable {

        /** PassTemplate HBase 表名 */
        public static final String TABLE_NAME = "pb:pass_template";

        /** 基本信息列族 */
        public static final String FAMILY_B = "b";

        /** 商户 id */
        public static final String MERCHANTS_ID = "merchants_id";

        /** 优惠券标题 */
        public static final String TITLE = "title";

        /** 优惠券摘要 */
        public static final String SUMMARY = "summary";

        /** 优惠券详细描述 */
        public static final String DESC = "desc";

        /** 优惠券是否有 Token */
        public static final String HAS_TOKEN = "hasToken";

        /** 优惠券背景颜色 */
        public static final String BACKGROUND = "background";

        /** 约束信息列族 */
        public static final String FAMILY_C = "c";

        /** 优惠券最大数量限制 */
        public static final String LIMIT = "limit";

        /** 优惠券开始时间 */
        public static final String START = "start";

        /** 优惠券结束时间 */
        public static final String END = "end";
    }

    /**
     * Pass HBase 表
     */
    public class PassTable {

        /** Pass HBase 表名 */
        public static final String TABLE_NAME = "pb:pass";

        /** 信息列族 */
        public static final String FAMILY_I = "i";

        /** 用户id */
        public static final String USER_ID = "user_id";

        /** 优惠券 id */
        public static final String PASS_TEMPLATE_ID = "pass_template_id";

        /** 优惠券识别码 */
        public static final String PASS_TOKEN = "pass_token";

        /** 领取日期 */
        public static final String ASSIGN_DATE = "assigned_date";

        /** 消费日期 */
        public static final String CONSUMER_DATE = "consumer_date";
    }

    /**
     * 用户评论 HBase 表
     */
    public class FeedbackTable {

        /** Feedback HBase 表名  */
        public static final String TABLE_NAME = "pb:feedback";

        /** 信息列族 */
        public static final String FAMILY_I = "i";

        /** 用户 id */
        public static final String USER_ID = "user_id";

        /** 评论类型 */
        public static final String TYPE = "type";

        /** 优惠券(类别，商户投放) id, 如果是 APP 则为-1 */
        public static final String PASS_TEMPLATE_ID = "pass_template_id";

        /** 评论内容 */
        public static final String COMMENT = "comment";
    }

}
