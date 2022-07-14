package com.aster.webchat.service;

import com.aster.webchat.api.entity.Moments;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MomentsService {
    public List<Moments> getAllMoments();
    public void newMoments(Moments moments);
    public void deleteMomentsById(int id);
}
