package com.example.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2020/2/22 0:06
 * @description
 */
@Slf4j
@Component
public final class SftpUtil {

    private static String sftpAddress;

    private static Integer sftpPort;

    private static String sftpUserName;

    private static String ftpPassword;

    private static Integer sftpTimeOut;

    private static String filePath;

    @Value("${sftp-server.address}")
    private void setSftpAddress(String sftpAddress) {
        SftpUtil.sftpAddress = sftpAddress;
    }

    @Value("${sftp-server.port}")
    private void setSftpPort(Integer sftpPort) {
        SftpUtil.sftpPort = sftpPort;
    }

    @Value("${sftp-server.user-name}")
    private void setSftpUserName(String sftpUserName) {
        SftpUtil.sftpUserName = sftpUserName;
    }

    @Value("${sftp-server.password}")
    private void setFtpPassword(String ftpPassword) {
        SftpUtil.ftpPassword = ftpPassword;
    }

    @Value("${sftp-server.timeout}")
    private void setSftpTimeOut(Integer sftpTimeOut) {
        SftpUtil.sftpTimeOut = sftpTimeOut;
    }

    @Value("${upload-file-path}")
    private void setFilePath(String filePath) {
        SftpUtil.filePath = "/" + filePath;
    }

    /**
     * 私有化构造方法
     */
    private SftpUtil() {
    }

    /**
     * sftp上传文件
     */
    public static Map<String, String> sftpUploadFile(MultipartFile file) {

        // 取得当前上传文件的文件名称
        String fileName = file.getOriginalFilename();

        // 名称为"" 说明文件不存在
        if (fileName.trim().isEmpty()) {
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

        ChannelSftp sftp;

        try {
            // linux服务器地址 账号 密码
            sftp = getChannel(sftpAddress, sftpPort, sftpUserName, ftpPassword, sftpTimeOut);

            // 判断目录文件夹是否存在 不存在即创建
            try {
                sftp.stat(filePath);
            } catch (SftpException e) {
                e.printStackTrace();
                sftp.mkdir(filePath);
                log.info("创建目录 : " + filePath);
            }

            // 进入linux服务器文件目录
            sftp.cd(filePath);

            try (
                    InputStream is = file.getInputStream()
            ) {
                // 把文件流命名成文件名称推送到linux
                sftp.put(is, newFileName, new ProgressMonitor());
            } catch (IOException e) {
                e.printStackTrace();
            }
            closeChannel(); // 关闭连接
        } catch (JSchException e) {
            e.printStackTrace();
            log.error("上传文件失败 请检查连接参数", e);
        } catch (SftpException e) {
            e.printStackTrace();
            log.error("上传文件失败 请检查连接参数", e);
        }

        log.info("文件上传信息[{}]", filePath + newFileName);
        return new HashMap<String, String>() {{
            put("message", "文件上传成功");
            put("fileName", newFileName);
        }};
    }

    static class ProgressMonitor implements SftpProgressMonitor {

        private long count;

        @Override
        public boolean count(long count) {
            this.count = this.count + count;
            System.out.println("已传输字节 -> " + this.count + "bytes");
            return true;
        }

        @Override
        public void end() {
            System.out.println("结束传输");
        }

        @Override
        public void init(int op, String src, String dest, long max) {
            System.out.println("开始传输");
        }
    }

    /**
     * sftp下载文件 要下载的文件名 下载到客户端文件名
     */
    public static void sftpDownloadFile(HttpServletRequest request, HttpServletResponse response
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

        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + localFileName);
        ChannelSftp sftp = null;
        try {
            // linux服务器地址 账号 密码
            sftp = getChannel(sftpAddress, 22, sftpUserName, ftpPassword, 60000);

            // 获得输出流 通过response获得的输出流 用于向客户端写内容
            ServletOutputStream out = response.getOutputStream();

            // 获取输出流 并自动输出到客户端 路径名 + 文件名
            sftp.get(filePath + fileName, out);

            out.close(); // 关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.quit(); // 退出连接
            closeChannel(); // 关闭连接
        }
    }

    // ------------------------------------------------------------
    private static Session session = null;

    private static Channel channel = null;

    /**
     * 使用SFTP方法
     *
     * @param ftpHost     ip地址
     * @param ftpPort     端口
     * @param ftpUserName 用户名
     * @param ftpPassword 密码
     * @param timeout     超时时间
     * @return
     * @throws JSchException
     */
    private static ChannelSftp getChannel(String ftpHost, int ftpPort
            , String ftpUserName, String ftpPassword, int timeout) throws JSchException {

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名 主机ip 端口获取一个Session对象
        log.debug("Session created.");
        session.setPassword(ftpPassword); // 设置密码

        Properties config = new Properties();
        // 不验证host-key 验证会失败
        config.put("StrictHostKeyChecking", "no");

        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间

        // 设置登陆超时时间 不设置可能会报错
        session.connect(1500); // 通过Session建立链接
        log.debug("Session connected.");

        channel = session.openChannel("sftp"); // 打开SFTP通道
        log.debug("Opening Channel.");

        channel.connect(); // 建立SFTP通道的连接
        log.debug("ftpHost = " + ftpHost + ", ftpUserName = " + ftpUserName + ", returning: " + channel);

        return (ChannelSftp) channel;
    }

    private static void closeChannel() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
    // ------------------------------------------------------------

    public static void main(String[] args) {

        String filePath = "/data/file/test.pdf";

        // 截取后缀名 pdf
        System.out.println(filePath.substring(filePath.lastIndexOf(".") + 1));

        // 截取后缀名 .pdf
        System.out.println(filePath.substring(filePath.lastIndexOf(".")));

        // 截取文件名 test.pdf
        System.out.println(filePath.substring(filePath.lastIndexOf("/") + 1));
    }
}