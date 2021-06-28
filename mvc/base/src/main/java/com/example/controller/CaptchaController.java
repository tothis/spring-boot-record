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
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李磊
 */
@RequestMapping("captcha")
@Controller
public class CaptchaController extends BaseController {

    private final String KEY_CAPTCHA = "KEY_CAPTCHA";

    private final String FORMAT_NAME = "JPG";

    @ResponseBody
    @GetMapping(value = "jpg", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] jpg() {
        // 不缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            StringBuffer code = new StringBuffer();
            BufferedImage image = new CaptchaUtil().genRandomCodeImage(code);
            session.setAttribute(KEY_CAPTCHA, code.toString());
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, FORMAT_NAME, out);
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生成的验证码并返回图片 Base64 码
     */
    @ResponseBody
    @GetMapping("base64")
    public String base64(HttpSession session) {
        return "<img src=\"data:image/jpg;base64,"
                + Base64.getEncoder().encodeToString(jpg()) + "\">";
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
            return "<img src=\"data:image/jpg;base64," + base64Img + "\">";
        } catch (IOException e) {
            e.getStackTrace();
            return null;
        }
    }

    @ResponseBody
    @PostMapping(value = "base64-to-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] base64ToImage(String base64) {
        byte[] data;
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
        // 图片 此处只提取前六位作为魔数
        TYPES.put("FFD8FF", "jpg");
        TYPES.put("89504E", "png");
        TYPES.put("474946", "gif");
    }

    /**
     * 根据文件的字节数据获取文件类型
     *
     * @param data 文件字节数组数据
     * @return
     */
    public static String getFileType(byte[] data) {
        // 提取前六位作为魔数
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
        // 提取文件的魔数
        StringBuilder magicNumber = new StringBuilder();
        // 一个字节对应魔数的两位
        int magicNumberByteLength = magicNumberLength / 2;
        for (int i = 0; i < magicNumberByteLength; i++) {
            magicNumber.append(Integer.toHexString(data[i] >> 4 & 0xF));
            magicNumber.append(Integer.toHexString(data[i] & 0xF));
        }

        return magicNumber.toString().toUpperCase();
    }
}
