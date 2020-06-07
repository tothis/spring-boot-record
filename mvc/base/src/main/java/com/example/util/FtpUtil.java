package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author 李磊
 * @datetime 2020/5/2 19:32
 * @description ftp工具类
 */
@Slf4j
@Component
public class FtpUtil {

    /**
     * FTPClient.enterLocalPassiveMode()作用 每次连接前ftp client告诉ftp server开通一个端口传输数据
     * ftp server可能每次开启不同端口传输数据 但linux由于安全限制 可能某些端口没有开启 就会出现阻塞
     * <p>
     * ftp工作原理 ftp要用到两个tcp连接即要使用两个端口 一个是命令链路 用来传递命令 一个是数据链路 用来上传下载数据
     * 连接ftp server时有active和passive两种模式 通常使用主动模式完成上传下载功能
     * 主动模式工作原理 客户端使用命令链路主动告诉服务端 我打开了XX端口 来连接我
     * 被动模式工作原理 与主动模式相反 服务端告诉客户端 我打开了XX端口 来连接我 被动模式用于有防火墙情况
     */
    private static String host;
    /**
     * 端口 默认21
     */
    private static int port;
    private static String userName;
    private static String password;
    private static String basePath;
    private static FTPClient ftp;

    @Value("${ftp-server.host}")
    public void setHost(String host) {
        FtpUtil.host = host;
    }

    @Value("${ftp-server.port}")
    public void setPort(int port) {
        FtpUtil.port = port;
    }

    @Value("${ftp-server.user-name}")
    public void setUserName(String userName) {
        FtpUtil.userName = userName;
    }

    @Value("${ftp-server.password}")
    public void setPassword(String password) {
        FtpUtil.password = password;
    }

    @Value("${upload-file-path}")
    public void setBasePath(String basePath) {
        FtpUtil.basePath = basePath;
    }

    public static FTPClient getFtp() {
        if (ftp != null) {
            return ftp;
        }
        try {
            ftp = new FTPClient();
            ftp.setRemoteVerificationEnabled(false);
            // 连接ftp服务器
            ftp.connect(host/*, port*/);
            // 登录
            ftp.login(userName, password);
            ftp.setBufferSize(10240);
            // 设置传输超时时间为60秒
            ftp.setDataTimeout(600000);
            // 连接超时为60秒
            ftp.setConnectTimeout(600000);
            // 以二进制形式传输
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                ftp.disconnect();
                log.error("连接失败 用户名或密码错误");
            }
            return ftp;
        } catch (IOException e) {
            throw new RuntimeException("ftp连接失败");
        }
    }

    /**
     * 私有化构造方法
     */
    private FtpUtil() {
    }

    public static void close() {
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void upload(String fileName, InputStream input) {
        FTPClient ftp = getFtp();
        try {
            ftp.enterLocalPassiveMode();
            // 切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath)) {
                // 如果目录不存在创建目录
                ftp.makeDirectory(basePath);
                ftp.changeWorkingDirectory(basePath);
            }
            // 上传文件
            ftp.enterLocalPassiveMode();
            if (!ftp.storeFile(fileName, input)) {
                log.info("上传失败");
            }
            ftp.logout();
            log.info("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void download(String fileName, OutputStream outputStream) {
        FTPClient ftp = getFtp();
        try {
            // 切换目录
            ftp.changeWorkingDirectory(fileName);
            ftp.enterLocalPassiveMode();
            ftp.retrieveFile(fileName, outputStream);
            outputStream.close();
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
            try {
                outputStream.close();
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
        FTPClient ftp = getFtp();
        try (
                // 获得输出流 通过response获得的输出流 用于向客户端写内容
                ServletOutputStream out = response.getOutputStream()
        ) {
            // 切换目录
            ftp.changeWorkingDirectory(fileName);
            ftp.enterLocalPassiveMode();

            ftp.retrieveFile(fileName, out);
            out.close();
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}