package com.aster.webchat.api.utils;

import com.aster.webchat.api.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    /**
     * 获取当前登录用户实体
     *
     * @return
     */
    public static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
