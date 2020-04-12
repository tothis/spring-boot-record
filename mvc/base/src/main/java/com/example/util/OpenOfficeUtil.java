package com.example.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2020/2/22 0:24
 * @description openOffice转化文档工具类
 */
@Slf4j
@Component
public class OpenOfficeUtil {

    private static String openOfficePath;

    private static String filePath;

    @Value("${open-office-path}")
    private void setOpenOfficePath(String _openOfficePath) {
        openOfficePath = _openOfficePath;
    }

    @Value("${upload-file-path}")
    private void setViewPdfPath(String _filePath) {
        filePath = _filePath;
    }

    /**
     * 根据已有的office文件生成pdf文件 返回生成的文件名称
     */
    public static String documentConvert(String fileName) {

        // 文件后缀名 xls
        String fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        // 文件新名称 没有'-'的uuid
        String pdfFileName = UUID.randomUUID().toString().replace("-", "") + ".pdf";

        String pdfFilePath = filePath + pdfFileName;

        // 目标文件 准备存进哪里 转换后的文件路径
        File pdfFile = new File(pdfFilePath);

        if (!pdfFile.getParentFile().exists()) {
            pdfFile.getParentFile().mkdirs();
        }

        if (fileSuffix.equals("pdf")) {
            // 复制文件
            FileUtil.copyFile(fileName, pdfFilePath);

            return pdfFileName;
        } else {
            // ----- 生成pdf文件预览开始

            // 来自微软官网 https://docs.microsoft.com/zh-cn/deployoffice/compat/office-file-format-reference
            String[] officeTypes = {"doc", "docx", "xls", "xlsx", "ppt", "pptx"};

            boolean flag = false;

            // 判断文件扩展名是否在office的文件类型中
            for (String officeType : officeTypes) {
                if (fileSuffix.equals(officeType)) flag = true;
            }

            if (!flag) return null;

            Process process = null;
            try {
                // 需要转换的文件路径
                File inFile = new File(filePath + fileName);

                if (inFile.exists()) {
                    log.info("源文件名称 -> [{}]", inFile.getName());
                } else {
                    log.info(">>> 找不到源文件 <<<");
                }

                // 启动openOffice的服务 如果已经启动则不用执行
                process = Runtime.getRuntime().exec(openOfficePath); // 执行exe文件

                // 通过ip地址和端口号连接openOffice
                OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
                connection.connect();
                // 转换
                // DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
                DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                // 开始转换
                converter.convert(inFile, pdfFile);
                // 关闭连接资源
                connection.disconnect();
                log.info("转换为pdf成功 文件名称[{}]", pdfFile);
                // ----- 生成文件pdf预览结束

                // ----- 生成文件swf预览开始
//                String command = "/usr/local/swftools/bin/pdf2swf ";
//                try {
//                    // 执行exe文件
//                    Runtime.getRuntime().exec(command + pdfFilePath + " -o " + swfFilePath +
//                            " -z -s flashversion=9 -s languagedir=\"xpdf-chinese-simplified\" -s storeallcharacters -f -j 100");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                log.info("转换为swf成功 文件名称[{}]", outFile);
                // ----- 生成文件swf预览结束
            } catch (Exception e) {
                e.printStackTrace();
                log.error("生成预览文件失败 请检查连接参数", e);
            } finally {
                // 关闭openOffice服务的进程
                if (process != null)
                    process.destroy();
            }
            return pdfFileName;
        }
    }
}