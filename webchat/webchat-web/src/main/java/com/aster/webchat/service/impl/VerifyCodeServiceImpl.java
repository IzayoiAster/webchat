package com.aster.webchat.service.impl;

import org.springframework.stereotype.Service;
import com.aster.webchat.service.VerifyCodeService;

import java.util.Random;

@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Override
    public String getVerifyCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(new Random().nextInt(10));
        }
        return code.toString();
    }

}
