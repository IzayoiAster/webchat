package com.aster.webchat.api.entity;

import java.util.List;

/**
 * 分页实体类
 */
public class RespPageBean {
    private Long total; // 数据总数
    private List<?> data; // 数据实体列表

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
