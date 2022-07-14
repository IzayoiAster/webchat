package com.aster.webchat.service.impl;

import com.aster.webchat.api.entity.Comment;
import com.aster.webchat.dao.mapper.CommentMapper;
import com.aster.webchat.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据朋友圈id获取评论列表
     *
     * @param mid
     * @return
     */
    @Override
    public List<Comment> getAllCommentByMomentsId(int mid) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getMid, mid);
        return commentMapper.selectList(queryWrapper);
    }

    /**
     * 新建评论
     *
     * @param comment
     */
    @Override
    public void newComment(Comment comment) {
        commentMapper.insert(comment);
    }
}
