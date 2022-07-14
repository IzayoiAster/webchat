package com.aster.webchat.service;

import com.aster.webchat.api.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    public List<Comment> getAllCommentByMomentsId(int mid);
    public void newComment(Comment comment);
}
