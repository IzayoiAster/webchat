package com.aster.webchat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aster.webchat.api.entity.User;
import com.aster.webchat.service.UserService;

import java.util.List;

@Api(tags = "聊天室API")
@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    UserService userService;

    @ApiOperation("获取用户列表（除当前用户）")
    @GetMapping("/users")
    public List<User> getUsersWithoutCurrentUser() {
        return userService.getUsersWithoutCurrentUser();
    }
}
