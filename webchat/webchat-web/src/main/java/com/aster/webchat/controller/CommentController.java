package com.aster.webchat.controller;

import com.alibaba.fastjson.JSON;
import com.aster.webchat.api.entity.Comment;
import com.aster.webchat.api.utils.UserUtil;
import com.aster.webchat.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "回复API")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ApiOperation("获取id为mid的朋友圈的评论")
    @GetMapping("/{mid}")
    public String getComment(@PathVariable int mid) {
        return JSON.toJSONString(commentService.getAllCommentByMomentsId(mid));
    }

    @ApiOperation("在id为mid的朋友圈新增一条评论")
    @PostMapping ("/")
    public void newComment(@RequestParam int mid, @RequestParam String content) {
        System.out.println("----------------------------回复发送中----------------------------");
        Comment comment = new Comment();
        comment.setMid(mid);
        comment.setUsername(UserUtil.getCurrentUser().getUsername());
        comment.setContent(content);
        commentService.newComment(comment);
    }

}
