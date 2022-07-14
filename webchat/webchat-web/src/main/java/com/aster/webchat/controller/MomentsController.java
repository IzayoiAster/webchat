package com.aster.webchat.controller;

import com.alibaba.fastjson.JSON;
import com.aster.webchat.api.entity.Moments;
import com.aster.webchat.api.utils.UserUtil;
import com.aster.webchat.service.MomentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "朋友圈API")
@RestController
@RequestMapping("/moments")
public class MomentsController {

    @Autowired
    private MomentsService momentsService;

    @ApiOperation("获取所有朋友圈")
    @GetMapping("/")
    public String allMoments() {
        return JSON.toJSONString(momentsService.getAllMoments());
    }

    @ApiOperation("发布朋友圈")
    @PostMapping("/")
    public void newMoments(@RequestParam String content, @RequestParam String url) {
        Moments moments = new Moments();
        moments.setUsername(UserUtil.getCurrentUser().getUsername());
        moments.setContent(content);
        moments.setUrl(UserUtil.getCurrentUser().getUserProfile());
        moments.setPicurl(url);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY年MM月dd日");
        moments.setTime(dateFormat.format(new Date()));
        momentsService.newMoments(moments);
    }

    @ApiOperation("删除朋友圈")
    @DeleteMapping("/{id}")
    public void deleteMoments(@PathVariable int id) {
        momentsService.deleteMomentsById(id);
    }

}
