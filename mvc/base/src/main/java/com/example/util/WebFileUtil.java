package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2020/5/2 21:55
 * @description
 */
@Slf4j
public class WebFileUtil {

    // windows测试
    private static String basePath = "D:/data/upload/file/";

    /**
     * 上传文件
     */
    public static String uploadFile(MultipartFile file) {
        // 获取当前上传文件的文件名称
        String fileName = file.getOriginalFilename();

        // 如果名称不为"" 说明该文件存在 否则说明该文件不存在
        if (StringUtil.isBlank(fileName)) {
            return null;
        }

        // 文件新名称 没有'-'的uuid
        String newFileName = newFileName(fileName);

        String uploadFilePath = basePath + newFileName;
        File uploadFile = new File(uploadFilePath);

        // 判断文件父目录是否存在 不存在就创建
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFileName;
    }

    /**
     * 下载文件
     */
    public static void downloadFile(HttpServletResponse response
            , String fileName, String localFileName) {
        if (StringUtil.isBlank(fileName)) {
            return;
        }

        // 不传客户端本地文件名称 则使用服务器上的名称
        if (StringUtil.isBlank(localFileName)) {
            localFileName = fileName;
        } else {
            // 使用客户端传递的名称 + 服务器上的文件后缀名
            localFileName += fileName.substring(fileName.lastIndexOf("."));
        }

        log.info("下载文件 -> [{}]", fileName);

        response.setContentType("application/force-download"); // 设置强制下载不打开
        // attachment设置以附件方式下载 fileName设置文件名称
        response.addHeader("content-disposition", "attachment;fileName=" + localFileName);
        try (
                // 获取文件输入流
                InputStream in = new FileInputStream(basePath + fileName);

                // 获得输出流 通过response获得的输出流 用于向客户端写内容
                ServletOutputStream out = response.getOutputStream();
        ) {

            // 前端通过此值生成下载进度条
            response.setHeader("content-length", String.valueOf(in.available()));

            // 文件拷贝的模板代码
            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String newFileName(String fileName) {
        // 获取文件扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf("."));

        // 打印文件名称
        log.info("文件名称 -> [{}]", fileName);

        // 文件新名称 没有'-'的uuid
        return UUID.randomUUID().toString().replace("-", "") + fileExtName;
    }
}