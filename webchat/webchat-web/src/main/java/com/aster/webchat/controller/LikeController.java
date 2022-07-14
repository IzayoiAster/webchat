package com.aster.webchat.controller;

import com.alibaba.fastjson.JSON;
import com.aster.webchat.api.entity.Mlike;
import com.aster.webchat.api.utils.UserUtil;
import com.aster.webchat.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "点赞API")
@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @ApiOperation("获取id为mid的朋友圈的点赞")
    @GetMapping("/{mid}")
    public String getLike(@PathVariable int mid) {
        return JSON.toJSONString(likeService.getAllLikeByMomentsId(mid));
    }

    @ApiOperation("在id为mid的朋友圈新增一个点赞")
    @PostMapping("/")
    public void newLike(@RequestParam int mid) {
        Mlike mlike = new Mlike();
        mlike.setMid(mid);
        mlike.setUsername(UserUtil.getCurrentUser().getUsername());
        likeService.newLike(mlike);
    }

}
