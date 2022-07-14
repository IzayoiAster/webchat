package com.aster.webchat.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 这里没用dfs文件系统
 * 直接在服务器配了tomcat，远程访问和上传文件到配置的路径
 */
@Api(tags = "文件API")
@RestController
public class FileController {

    // private final String SERVER_PATH = "http://localhost:8082/upload/";

    private String BASE_PATH() {

        System.out.println("当前系统类型为：" + System.getProperty("os.name"));

        if (System.getProperty("os.name").equals("Windows 10")) {
            return "C:\\test\\";
        } else {
            return "/home/aster/jars/upload/";
        }
    }

    private String SERVER_PATH() {
        if (System.getProperty("os.name").equals("Windows 10")) {
            return "C:\\test\\";
        } else {
            return "http://服务器:8080/upload/";
        }
    }

    @ApiOperation("上传文件并获取上传的文件的url")
    @PostMapping("/file")
    public String uploadFile(MultipartFile file) throws IOException, MyException {

        System.out.println("----------------------------文件上传中----------------------------");

        // 获取前端上传的文件名称
        String filename = file.getOriginalFilename();
        // 为文件名加上UUID避免重复
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFilename = uuid + "-" + filename;

        // 新建目标文件
        File newFile = new File(BASE_PATH() + newFilename);
        // 将接收的到的multipartFile类型的文件转为 file
        file.transferTo(newFile);
        // 返回接收到的并存在项目本地的文件路径
        return SERVER_PATH() + newFilename;
    }

}
