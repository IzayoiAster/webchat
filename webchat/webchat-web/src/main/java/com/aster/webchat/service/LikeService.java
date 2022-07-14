package com.aster.webchat.service;

import com.aster.webchat.api.entity.Mlike;

import java.util.List;

public interface LikeService {
    public List<Mlike> getAllLikeByMomentsId(int mid);
    public void newLike(Mlike mlike);
}
