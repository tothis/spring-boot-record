package com.example.controller;

import com.example.util.CaptchaUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: 李磊
 * @datetime: 2018/5/26 22:10:26
 * @description:
 */
@RequestMapping("captcha")
@Controller
public class CaptchaController {

    public static final String KEY_CAPTCHA = "KEY_CAPTCHA";

    public static final String FORMAT_NAME = "JPG";

    // 返回值为void produces属性不生效
    // 返回值不为void response.setContentType不生效
    @GetMapping("jpg")
    public void captcha(HttpSession session, HttpServletResponse response) {
        // 设置相应类型 告诉浏览器输出的内容为图片
        // response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        // 不缓存此内容
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
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
    public String captchaBase64(HttpServletResponse response, HttpSession session) {

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

    @GetMapping("upload")
    public String upload() {
        return "upload";
    }

    @ResponseBody
    @PostMapping("image-to-base64")
    public String imageToBase64(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            BufferedImage img = ImageIO.read(is);
            // bufferImage -> base64
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            // 此处formatName必须直接为格式 不能为image/格式
            ImageIO.write(img, FORMAT_NAME, outputStream);
            String base64Img = new String(Base64.getEncoder().encode(outputStream.toByteArray()))
                    .replace("\n", "").replace("\r", "");
            outputStream.close();
            return "data:image/jpg;base64," + base64Img;
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping(value = "base64-to-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] base64ToImage(String base64) {
        byte[] data = null;
        String base64Prefix = "data:image/jpg;base64,";
        if (base64.indexOf(base64Prefix) == 0)
            base64 = base64.substring(base64Prefix.length());

        data = Base64.getDecoder().decode(base64);
        System.out.println("当前文件魔数为 " + getFileType(data));
        return data;
    }

    /**
     * 魔数到文件类型的映射集合
     */
    public static final Map<String, String> TYPES = new HashMap<>();

    static {
        // 图片，此处只提取前六位作为魔数
        TYPES.put("FFD8FF", "jpg");
        TYPES.put("89504E", "png");
        TYPES.put("474946", "gif");
        TYPES.put("524946", "webp");
    }

    /**
     * 根据文件的字节数据获取文件类型
     *
     * @param data 文件字节数组数据
     * @return
     */
    public static String getFileType(byte[] data) {
        //提取前六位作为魔数
        String magicNumberHex = getHex(data, 6);
        return TYPES.get(magicNumberHex);
    }

    /**
     * 获取16进制表示的魔数
     *
     * @param data              字节数组形式的文件数据
     * @param magicNumberLength 魔数长度
     * @return
     */
    public static String getHex(byte[] data, int magicNumberLength) {
        //提取文件的魔数
        StringBuilder magicNumber = new StringBuilder();
        //一个字节对应魔数的两位
        int magicNumberByteLength = magicNumberLength / 2;
        for (int i = 0; i < magicNumberByteLength; i++) {
            magicNumber.append(Integer.toHexString(data[i] >> 4 & 0xF));
            magicNumber.append(Integer.toHexString(data[i] & 0xF));
        }

        return magicNumber.toString().toUpperCase();
    }

//    @ResponseBody
//    @GetMapping(value = "captcha2", produces = MediaType.IMAGE_JPEG_VALUE)
//    public byte[] captcha3() throws IOException {
//        FileInputStream inputStream = new FileInputStream("E:/user/Pictures/panda.jpg");
//        int available = inputStream.available(); // 获取字节数
//        byte[] bytes = new byte[available];
//        inputStream.read(bytes, 0, available);
//        return bytes;
//    }

//    todo 返回BufferedImage失效 待解决
//    @ResponseBody
//    @GetMapping(value = "captcha3", produces = MediaType.IMAGE_JPEG_VALUE)
//    public BufferedImage captcha4() {
//        BufferedImage read = null;
//        try {
//            read = ImageIO.read(new FileInputStream(new File("E:/user/Pictures/panda.jpg")));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return read;
//    }
}