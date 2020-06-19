package com.example.controller;

import com.example.util.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * @author: 李磊
 * @datetime: 2018/5/26 22:10:26
 * @description:
 */
@RequestMapping("captcha")
@Controller
public class CaptchaController {

    private final String KEY_CAPTCHA = "KEY_CAPTCHA";

    private final String FORMAT_NAME = "JPG";

    @GetMapping("jpg")
    public void captcha(HttpSession session, HttpServletResponse response) {
        // 不缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        try {
            StringBuffer code = new StringBuffer();
            BufferedImage image = new CaptchaUtil().genRandomCodeImage(code);
            session.setAttribute(KEY_CAPTCHA, code.toString());
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, FORMAT_NAME, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成的验证码 返回图片base64码
     */
    @ResponseBody
    @GetMapping("base64")
    public String captchaBase64(HttpSession session) {
        StringBuffer code = new StringBuffer();
        BufferedImage image = new CaptchaUtil().genRandomCodeImage(code);
        session.setAttribute(KEY_CAPTCHA, code.toString());
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // 将内存中的图片通过流动形式输出自己数组输出流
            ImageIO.write(image, FORMAT_NAME, byteArrayOutputStream);
            // 将图片转为base64字符
            byte[] bytes = byteArrayOutputStream.toByteArray();//转换成字节
            // 转换成base64串 删除\r\n
            String base64 = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(bytes)
                    .replace("\n", "").replace("\r", "");
            return base64;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}