package org.march.passbook.consumer.vo;

import com.google.common.base.Enums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.march.passbook.consumer.constant.FeedbackType;

/**
 * 用户评论
 *
 * @author March
 * @date 2020/6/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    /** 评论在 Feedback HBase 表中的 rowKey */
    private String id;

    /** 用户 id */
    private Long userId;

    /** 评论类型 */
    private String type;

    /** PassTemplate RowKey, 如果是 APP 类型的评论则为 -1 */
    private String passTemplateId = "-1";

    /** 评论内容 */
    private String comment;


    /**
     * 校验评论的有效性
     * @return boolean
     */
    public boolean validate() {

        /** */
        FeedbackType feedbackType = Enums.getIfPresent(
                FeedbackType.class, this.type.toUpperCase()
        ).orNull();

        return !(null == feedbackType || null == comment);
    }
}
