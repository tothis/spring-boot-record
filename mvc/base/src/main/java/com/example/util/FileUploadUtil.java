package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2020/2/22 14:37
 * @description
 */
@Slf4j
@Component
public class FileUploadUtil {

    private static String filePath;

    @Value("${upload-file-path}")
    private void setFilePath(String filePath) {
        FileUploadUtil.filePath = filePath;
    }

    /**
     * 普通上传文件
     */
    public static Map<String, String> uploadFile(MultipartFile file) {

        // 获取当前上传文件的文件名称
        String fileName = file.getOriginalFilename();
        // 如果名称不为"" 说明该文件存在 否则说明该文件不存在
        if (fileName == null || fileName.trim().isEmpty()) {
            return new HashMap<String, String>() {{
                put("message", "文件上传失败");
            }};
        }

        // 获取文件扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf("."));

        // 打印文件名称
        log.info("文件名称 -> [{}]", fileName);

        // 文件新名称 没有'-'的uuid
        String newFileName = UUID.randomUUID().toString().replace("-", "") + fileExtName;

        String uploadFilePath = filePath + newFileName;
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

        return new HashMap<String, String>() {{
            put("message", "文件上传成功");
            put("fileName", newFileName);
        }};
    }

    /**
     * 普通下载文件
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response
            , String fileName, String localFileName) {

        // 不传客户端本地文件名称 则使用服务器上的名称
        if (localFileName == null || localFileName.trim().isEmpty()) {
            localFileName = fileName;
        } else {
            // 使用客户端传递的名称 + 服务器上的文件后缀名
            localFileName += fileName.substring(fileName.lastIndexOf("."));
        }

        if (fileName == null && fileName.trim().isEmpty()) {
            return;
        }

        // 获得请求头中的User-Agent
        String agent = request.getHeader("User-Agent");

        // 根据不同浏览器进行不同的编码
        try {
            // 解决中文文件名乱码问题 firefox 和 chrome 解码相同
            if (agent.toLowerCase().indexOf("firefox") != -1 || agent.toUpperCase().indexOf("CHROME") != -1) {
                localFileName = new String(localFileName.getBytes("UTF-8"), "ISO8859-1");
            } else if (agent.toUpperCase().indexOf("MSIE") != -1) {
                localFileName = URLEncoder.encode(localFileName, "UTF-8"); // IE浏览器采用URL编码lee
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        log.info("下载文件 -> [{}]", fileName);

        response.setContentType("application/force-download"); // 设置强制下载不打开
        // attachment设置以附件方式下载 fileName设置文件名称
        response.addHeader("content-disposition", "attachment;fileName=" + localFileName);
        try (
                // 获取文件输入流
                InputStream in = new FileInputStream(filePath + fileName);
                // 获得输出流 通过response获得的输出流 用于向客户端写内容
                ServletOutputStream out = response.getOutputStream();
        ) {

            // 文件拷贝的模板代码
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}