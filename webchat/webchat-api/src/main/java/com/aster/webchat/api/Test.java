package com.aster.webchat.api;

import com.alibaba.excel.util.IoUtils;
import com.aster.webchat.api.utils.ImgUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.UUID;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("os.name"));
    }

//    public static String test(String value) throws IOException {
//        if (value.toLowerCase().startsWith("http")) {
//            InputStream inputStream = null;
//            URL imageUrl = new URL(value);
//            try {
//                //开启连接
//                URLConnection uc = imageUrl.openConnection();
//                URL url = null;
//                //获取响应状态
//                int statusCode = ((HttpURLConnection) uc).getResponseCode();
//                switch (statusCode) {
//                    case 200:
//                        inputStream = imageUrl.openStream();
//                        break;
//                    default:
//                        //直接当成String处理
//                        return value;
//                }
//                byte[] bytes = IoUtils.toByteArray(inputStream);
//                //压缩图片
//                byte[] compressBytes = ImgUtil.compressPicForScale(bytes, 200, UUID.randomUUID().toString());
//                return "success";
//            } catch (Exception exception) {
//                //捕获下链接异常
//                return value;
//            } finally {
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//            }
//
//        } else {
//            return value;
//        }
//    }
}
