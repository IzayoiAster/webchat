package com.aster.webchat.controller;

import com.aster.webchat.api.entity.UserState;
import com.aster.webchat.service.UserStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户状态API")
@RestController
@RequestMapping("userState")
public class UserStateController {
    /**
     * 服务对象
     */
    @Resource
    private UserStateService userStateService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("根据id查询用户状态")
    @GetMapping("selectOne")
    public UserState selectOne(Integer id) {
        return this.userStateService.queryById(id);
    }

}
