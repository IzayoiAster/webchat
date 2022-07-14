package com.aster.webchat.api.entity;

import java.io.Serializable;

/**
 * UserState实体类
 */
public class UserState implements Serializable {
    private static final long serialVersionUID = -38130170610280885L;

    private Integer id;
    /**
     * 状态名
     */
    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
