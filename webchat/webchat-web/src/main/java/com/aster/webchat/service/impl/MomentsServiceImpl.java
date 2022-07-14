package com.aster.webchat.service.impl;

import com.aster.webchat.api.entity.Moments;
import com.aster.webchat.dao.mapper.MomentsMapper;
import com.aster.webchat.service.MomentsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.Kernel;
import java.util.List;

@Service
public class MomentsServiceImpl implements MomentsService {

    @Autowired
    private MomentsMapper momentsMapper;

    /**
     * 获取所有朋友圈
     *
     * @return 朋友圈List
     */
    @Override
    public List<Moments> getAllMoments() {
        LambdaQueryWrapper<Moments> queryWrapper = new LambdaQueryWrapper<>();
        return momentsMapper.selectList(queryWrapper);
    }

    /**
     * 新建朋友圈
     *
     * @param moments
     */
    @Override
    public void newMoments(Moments moments) {
        momentsMapper.insert(moments);
    }

    /**
     * 删除朋友圈
     *
     * @param id
     */
    @Override
    public void deleteMomentsById(int id) {
        momentsMapper.deleteById(id);
    }
}
