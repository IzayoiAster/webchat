package com.aster.webchat.service.impl;

import com.aster.webchat.api.entity.Mlike;
import com.aster.webchat.dao.mapper.LikeMapper;
import com.aster.webchat.service.LikeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeMapper likeMapper;

    @Override
    public List<Mlike> getAllLikeByMomentsId(int mid) {
        LambdaQueryWrapper<Mlike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Mlike::getMid, mid);
        return likeMapper.selectList(queryWrapper);
    }

    @Override
    public void newLike(Mlike mlike) {
        likeMapper.insert(mlike);
    }
}
