package org.march.passbook.consumer.service;

import org.march.passbook.consumer.vo.Feedback;
import org.march.passbook.consumer.vo.Response;

/**
 * 评论功能：及用户评论相关业务服务
 *
 * @author March
 * @date 2020/6/19
 */
public interface IFeedbackService {

    /**
     * 创建评论
     * @param feedback {@link Feedback}
     * @return {@link Response}
     */
    Response createFeedback(Feedback feedback);

    /**
     * 获取用户评论
     * @param userId {@link Long}
     * @return {@link Response}
     */
    Response getFeedback(Long userId);
}
