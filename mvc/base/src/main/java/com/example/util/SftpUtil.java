package com.example.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * @author 李磊
 * @datetime 2020/2/22 0:06
 * @description
 */
@Slf4j
@Component
public final class SftpUtil {

    private static String host;

    private static int port;

    private static String userName;

    private static String password;

    private static int timeout;

    private static String basePath;

    @Value("${sftp-server.host}")
    public void setHost(String host) {
        SftpUtil.host = host;
    }

    @Value("${sftp-server.port}")
    public void setPort(int port) {
        SftpUtil.port = port;
    }

    @Value("${sftp-server.user-name}")
    public void setUserName(String userName) {
        SftpUtil.userName = userName;
    }

    @Value("${sftp-server.password}")
    public void setPassword(String password) {
        SftpUtil.password = password;
    }

    @Value("${sftp-server.timeout}")
    public void setTimeout(int timeout) {
        SftpUtil.timeout = timeout;
    }

    @Value("${upload-file-path}")
    public void setBasePath(String basePath) {
        SftpUtil.basePath = basePath;
    }

    /**
     * 私有化构造方法
     */
    private SftpUtil() {
    }

    /**
     * sftp上传文件
     */
    public static void upload(String fileName, InputStream file) {

        ChannelSftp sftp;

        try {
            // linux服务器地址 账号 密码
            sftp = getChannel(host, port, userName, password, timeout);

            // 判断目录文件夹是否存在 不存在即创建
            try {
                sftp.stat(basePath);
            } catch (SftpException e) {
                e.printStackTrace();
                sftp.mkdir(basePath);
                log.info("创建目录 : " + basePath);
            }

            // 进入linux服务器文件目录
            sftp.cd(basePath);

            // 把文件流命名成文件名称推送到linux
            sftp.put(file, fileName, new ProgressMonitor());
            closeChannel(); // 关闭连接
        } catch (JSchException e) {
            e.printStackTrace();
            log.error("上传文件失败 请检查连接参数", e);
        } catch (SftpException e) {
            e.printStackTrace();
            log.error("上传文件失败 请检查连接参数", e);
        }

        log.info("文件上传信息[{}]", basePath + fileName);
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

        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + localFileName);
        ChannelSftp sftp = null;
        try {
            // linux服务器地址 账号 密码
            sftp = getChannel(host, 22, userName, password, 60000);

            // 获得输出流 通过response获得的输出流 用于向客户端写内容
            ServletOutputStream out = response.getOutputStream();

            // 获取输出流 并自动输出到客户端 路径名 + 文件名
            sftp.get(basePath + fileName, out);

            out.close(); // 关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.quit(); // 退出连接
            closeChannel(); // 关闭连接
        }
    }

    /**
     * sftp下载文件 要下载的文件名 下载到客户端文件名 输出流
     */
    public static void download(String fileName, OutputStream out) {

        ChannelSftp sftp = null;
        try {
            // linux服务器地址 账号 密码
            sftp = getChannel(host, 22, userName, password, 60000);

            // 获取输出流 并自动输出到客户端 路径名 + 文件名
            sftp.get(basePath + fileName, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.quit(); // 退出连接
            closeChannel(); // 关闭连接
            try {
                out.close(); // 关闭输出流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void webDownload(HttpServletResponse response, String fileName, String localFileName) {
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
        // 设置强制下载不打开
        response.setContentType("application/force-download");
        // 设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + localFileName);
        ChannelSftp sftp = null;
        try (
                // 获得输出流 通过response获得的输出流 用于向客户端写内容
                ServletOutputStream out = response.getOutputStream()
        ) {
            // linux服务器地址 账号 密码
            sftp = getChannel(host, 22, userName, password, 60000);

            // 获取输出流 并自动输出到客户端 路径名 + 文件名
            sftp.get(basePath + fileName, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.quit(); // 退出连接
            closeChannel(); // 关闭连接
        }
    }

    // ----- ----- ----- ----- -----
    private static Session session = null;

    private static Channel channel = null;

    /**
     * 使用SFTP方法
     *
     * @param host     ip地址
     * @param port     端口
     * @param userName 用户名
     * @param password 密码
     * @param timeout  超时时间
     * @return
     * @throws JSchException
     */
    private static ChannelSftp getChannel(String host, int port
            , String userName, String password, int timeout) throws JSchException {

        if (channel != null) return (ChannelSftp) channel;

        JSch jsch = new JSch();
        session = jsch.getSession(userName, host, port); // 根据用户名 主机ip 端口获取一个session对象
        log.debug("Session created.");
        session.setPassword(password); // 设置密码

        Properties config = new Properties();
        // 不验证host-key 验证会失败
        config.put("StrictHostKeyChecking", "no");

        session.setConfig(config); // 为session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间

        // 设置登陆超时时间 不设置可能会报错
        session.connect(1500); // 通过session建立链接
        log.debug("Session connected.");

        channel = session.openChannel("sftp"); // 打开sftp通道
        log.debug("Opening Channel.");

        channel.connect(); // 建立sftp通道的连接
        log.debug("host = " + host + ", userName = " + userName + ", returning: " + channel);

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
    // ----- ----- ----- ----- -----

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