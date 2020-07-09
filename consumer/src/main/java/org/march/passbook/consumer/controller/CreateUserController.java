package org.march.passbook.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.march.passbook.consumer.log.LogConstants;
import org.march.passbook.consumer.log.LogGenerator;
import org.march.passbook.consumer.service.IUserService;
import org.march.passbook.consumer.vo.Response;
import org.march.passbook.consumer.vo.User;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * <h1>创建用户控制器</h1>
 *
 * @author March
 * @date 2020/6/20
 */
@Slf4j
@RestController
@RequestMapping("/consumer")
public class CreateUserController {

    /** 创建用户服务接口 */
    private final IUserService userService;

    /** HttpServletRequest */
    private final HttpServletRequest request;

    public CreateUserController(IUserService userService, HttpServletRequest request) {
        this.userService = userService;
        this.request = request;
    }

    /**
     * <h2>创建用户</h2>
     * @param user {@link User}
     * @return {@link Response}
     */
    @ResponseBody
    @PostMapping("/register")
    Response createUser(@RequestBody User user) throws Exception {
        LogGenerator.generateLog(
                request,
                -1L,
                LogConstants.ActionName.REGISTER,
                user
        );
        return userService.createUser(user);
    }

}
