package com.example.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * 验证码工具类
 *
 * @author 李磊
 */
public class CaptchaUtil {

    // 随机产生的字符串
    private static final String RANDOM_STRS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // 设置字体family
    private static final String FONT_NAME = "Comic Sans MS";
    private static final int FONT_SIZE = 20;

    private Random random = new Random();

    private int width = 110; // 图片宽
    private int height = 25; // 图片高
    private int lineNum = 50; // 干扰线数量
    private int strNum = 6; // 随机产生字符数量

    /**
     * 生成随机图片
     */
    public BufferedImage genRandomCodeImage(StringBuffer randomCode) {
        // BufferedImage类是具有缓冲区的Image类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 获取Graphics对象 便于对图像进行各种绘制操作
        Graphics graphics = image.getGraphics();

        // 设置背景色
        graphics.setColor(getRandColor(200, 250));
        graphics.fillRect(0, 0, width, height);

        // 设置干扰线的颜色
        graphics.setColor(getRandColor(110, 120));
        // 绘制干扰线 lineNum是线条个数
        for (int i = 0; i <= lineNum; i++) {
            drowLine(graphics);
        }

        // 绘制随机字符
        graphics.setFont(new Font(FONT_NAME, Font.ROMAN_BASELINE, FONT_SIZE));
        for (int i = 1; i <= strNum; i++) {
            graphics.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                    .nextInt(121)));
            randomCode.append(drowString(graphics, i));
        }
        graphics.dispose();
        return image;
    }

    /**
     * 给定范围获得随机颜色
     */
    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 绘制字符串
     */
    private String drowString(Graphics graphics, int i) {
        String rand = getRandomString(random.nextInt(RANDOM_STRS.length()));
        graphics.translate(random.nextInt(3), random.nextInt(3));
        graphics.drawString(rand, 13 * i, 16);
        return rand;
    }

    /**
     * 绘制干扰线
     */
    private void drowLine(Graphics graphic) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int w = random.nextInt(16);
        int h = random.nextInt(16);
        final int signA = random.nextBoolean() ? 1 : -1;
        final int signB = random.nextBoolean() ? 1 : -1;
        graphic.drawLine(x, y, x + w * signA, y + h * signB);
    }

    /**
     * 获取随机的字符
     */
    private String getRandomString(int num) {
        return String.valueOf(RANDOM_STRS.charAt(num));
    }

    public static void main(String[] args) {
        CaptchaUtil tool = new CaptchaUtil();
        StringBuffer code = new StringBuffer();
        BufferedImage image = tool.genRandomCodeImage(code);
        System.out.println("random code = " + code);
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "jpg", new FileOutputStream(new File(
                    // "/Users/wangsaichao/Desktop/random-code.jpg")));
                    "D:/data/random-code.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}