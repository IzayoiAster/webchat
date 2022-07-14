package com.aster.webchat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aster.webchat.config.VerificationCode;
import com.aster.webchat.api.entity.RespBean;
import com.aster.webchat.service.VerifyCodeService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Api(tags = "登录API")
@RestController
public class LoginController {

    @Autowired
    VerifyCodeService verifyCodeService;

    /**
     * 获取验证码图片写到响应的输出流中，保存验证码到session
     *
     * @param response
     * @param session
     * @throws IOException
     */
    @ApiOperation("获取验证码图片写到响应的输出流中，保存验证码到session")
    @GetMapping("/verifyCode")
    public void getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        session.setAttribute("verify_code", text);
        VerificationCode.output(image, response.getOutputStream());
    }

}
