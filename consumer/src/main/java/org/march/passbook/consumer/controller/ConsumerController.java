package org.march.passbook.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.march.passbook.consumer.log.LogConstants;
import org.march.passbook.consumer.log.LogGenerator;
import org.march.passbook.consumer.service.IFeedbackService;
import org.march.passbook.consumer.service.IGainPassTemplateService;
import org.march.passbook.consumer.service.IInventoryService;
import org.march.passbook.consumer.service.IUserPassService;
import org.march.passbook.consumer.vo.Feedback;
import org.march.passbook.consumer.vo.GainPassTemplateRequest;
import org.march.passbook.consumer.vo.Pass;
import org.march.passbook.consumer.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Consumer Rest Controller 用户总控制器
 *
 * @author March
 * @date 2020/6/20
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    /** 用户优惠券服务 */
    private final IUserPassService userPassService;

    /** 优惠券库存服务 */
    private final IInventoryService inventoryService;

    /** 用户领取优惠券服务 */
    private final IGainPassTemplateService gainPassTemplateService;

    /** 用户评论服务 */
    private final IFeedbackService feedbackService;

    /** */
    private final HttpServletRequest httpServletRequest;

    @Autowired
    public ConsumerController(IUserPassService userPassService,
                              IInventoryService inventoryService,
                              IGainPassTemplateService gainPassTemplateService,
                              IFeedbackService feedbackService,
                              HttpServletRequest httpServletRequest) {
        this.userPassService = userPassService;
        this.inventoryService = inventoryService;
        this.gainPassTemplateService = gainPassTemplateService;
        this.feedbackService = feedbackService;
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * <h2>用户获取优惠券库存</h2>
     * @param userId 用户优惠券
     * @return {@link Response}
     */
    @ResponseBody
    @GetMapping("/getInventory")
    Response getInventory(Long userId) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.INVENTORY_INFO,
                null
        );
        return inventoryService.getInventoryInfo(userId);
    }

    /**
     * <h2>用户领取优惠券</h2>
     * @param request {@link GainPassTemplateRequest}
     * @return {@link Response}
     */
    @ResponseBody
    @PostMapping("/gainPassTemplate")
    Response gainPassTemplate(@RequestBody GainPassTemplateRequest request) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                request.getUserId(),
                LogConstants.ActionName.GAIN_PASS_TEMPLATE,
                request
        );
        return gainPassTemplateService.gainPassTemplate(request);
    }

    /**
     * <h2>获取用户个人的有效优惠券信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    @ResponseBody
    @GetMapping("/getPassInfo")
    Response getPassInfo(Long userId) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_PASS_INFO,
                null
        );
        return userPassService.getUserPassInfo(userId);
    }

    /**
     * <h2>获取使用的优惠券</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    @ResponseBody
    @GetMapping("/getUsedPassInfo")
    Response getUsedPassInfo(Long userId) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_USED_PASS_INFO,
                null
        );
        return userPassService.getUserUsedPassInfo(userId);
    }

    /**
     * <h2>获取用户个人的所有优惠券信息</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    @ResponseBody
    @GetMapping("/getAllPassInfo")
    Response getAllPassInfo(Long userId) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.USER_ALL_PASS_INFO,
                null
        );
        return userPassService.getUserAllPassInfo(userId);
    }

    /**
     * <h2>用户使用优惠券</h2>
     * @param pass 用户优惠券
     * @return {@link Response}
     */
    @ResponseBody
    @PostMapping("/usePass")
    Response usePass(@RequestBody Pass pass) throws Exception {
        LogGenerator.generateLog(
                httpServletRequest,
                pass.getUserId(),
                LogConstants.ActionName.USER_USE_PASS,
                pass
        );
        return userPassService.userUsePass(pass);
    }



    /**
     * <h2>用户创建评论</h2>
     * @param feedback
     * @return {@link Response}
     */
    @ResponseBody
    @PostMapping("/createFeedback")
    Response createFeedback(@RequestBody Feedback feedback) {
        LogGenerator.generateLog(
                httpServletRequest,
                feedback.getUserId(),
                LogConstants.ActionName.CREATE_FEEDBACK,
                feedback
        );
        return feedbackService.createFeedback(feedback);
    }

    /**
     * <h2>用户获取评论</h2>
     * @param userId 用户 id
     * @return {@link Response}
     */
    @ResponseBody
    @GetMapping("/getFeedback")
    Response getFeedback(Long userId) {
        LogGenerator.generateLog(
                httpServletRequest,
                userId,
                LogConstants.ActionName.GET_FEEDBACK,
                null
        );
        return feedbackService.getFeedback(userId);
    }
}
