package com.example.controller;

import com.example.util.FileUploadUtil;
import com.example.util.OpenOfficeUtil;
import com.example.util.SftpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 李磊
 * @datetime 2019/12/27 21:58
 * @description
 */
@Slf4j
// 跨域处理
// response.setHeader("Access-Control-Allow-Origin","*");
@CrossOrigin // 允许跨域
@RequestMapping("file")
@Controller
public class FileController {

    @Value("${upload-file-path}")
    private String filePath;

    @GetMapping("1")
    public String page1() {
        return "file-upload1";
    }

    @GetMapping("2")
    public String page2() {
        return "file-upload2";
    }

    @ResponseBody
    @PostMapping("image")
    public Map<String, String> image(MultipartFile file) {
        return FileUploadUtil.uploadFile(file);
    }

    /**
     * sftp上传文件
     */
    @ResponseBody
    @PostMapping("sftp")
    public Map<String, String> sftp(MultipartFile file) {
        return SftpUtil.sftpUploadFile(file);
    }

    @PostMapping("pdf")
    public String pdf(Model model, String fileName) {
        model.addAttribute("fileName", fileName);
        return "pdf";
    }

    /**
     * 上传office文档
     */
    @ResponseBody
    @PostMapping("document")
    public String document(MultipartFile file) {
        Map<String, String> uploadFile = FileUploadUtil.uploadFile(file);
        return OpenOfficeUtil.documentConvert(uploadFile.get("fileName"));
    }

    @GetMapping("download")
    public void download(HttpServletRequest request, HttpServletResponse response
            , String fileName, String localFileName) {
        FileUploadUtil.downloadFile(request, response, fileName, localFileName);
    }

    @GetMapping("sftp-download")
    public void sftpDownload(HttpServletRequest request, HttpServletResponse response
            , String fileName, String localFileName) {
        SftpUtil.sftpDownloadFile(request, response, fileName, localFileName);
    }

    @ResponseBody
    @PostMapping("split")
    public Map<String, String> split(HttpServletRequest request) {

        Map<String, String> data = new HashMap<>();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件分片数据
        MultipartFile file = multipartRequest.getFile("chunk");

        int index = Integer.parseInt(multipartRequest.getParameter("fileIndex"));
        log.info("当前文件分片下标 -> [{}]", index);

        // 新的文件名称 没有的'-'的uuid
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "-" + index + ".tmp";

        // 文件分片的新的文件路径
        File uploadFile = new File(filePath, newFileName);

        // 判断文件父目录是否存在 不存在就创建
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }

        // 保存文件
        try {
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 上传的文件分片名称
        data.put("tempFile", newFileName);
        return data;
    }

    /**
     * 合并所有文件
     */
    @ResponseBody
    @PostMapping("merge")
    public Map<String, String> merge(String tempFile, String originalFileName) {

        Map<String, String> data = new HashMap<>();
        String[] tempFiles = tempFile.split(",");

        log.info("分片文件数组 -> {}", "\n" + tempFile.replace(",", "\n"));
        // 文件名后缀 有'.'
        String fileExtName = originalFileName.substring(originalFileName.lastIndexOf("."));
        // 新文件名称 没有'-'的UUID
        String fileName = UUID.randomUUID().toString().replace("-", "") + fileExtName;
        boolean flag = mergeFiles(tempFiles, fileName);

        if (flag) {
            data.put("recordVedio", fileName);
        }

        data.put("flag", String.valueOf(flag));
        return data;
    }

    /**
     * 利用nio FileChannel合并多个文件
     *
     * @param _files     分片文件数组
     * @param resultPath 合并后的文件全路径名
     * @return
     */
    public boolean mergeFiles(String[] _files, String resultPath) {

        if (_files == null || _files.length == 0) {
            return false;
        }
        resultPath = filePath + resultPath;

        if (_files.length == 1) {
            return new File(_files[0]).renameTo(new File(resultPath));
        }

        // 按照文件的索引进行冒泡排序
        for (int i = 0; i < _files.length; i++) {
            for (int j = 0; j < _files.length - i - 1; j++) {
                int varJ = Integer.parseInt(_files[j].substring(_files[j].lastIndexOf("-") + 1, _files[j].lastIndexOf(".")));
                int varJ1 = Integer.parseInt(_files[j + 1].substring(_files[j + 1].lastIndexOf("-") + 1
                        , _files[j + 1].lastIndexOf(".")));
                if (varJ > varJ1) { // 即这两个相邻的数是逆序的 交换
                    String temp = _files[j];
                    _files[j] = _files[j + 1];
                    _files[j + 1] = temp;
                }
            }
        }

        File[] files = new File[_files.length];
        for (int i = 0; i < _files.length; i++) {
            files[i] = new File(filePath + _files[i]);
            // 不存在或不是文件
            if (_files[i] == null || !files[i].exists() || !files[i].isFile()) {
                return false;
            }
        }
//        // 逆序排序 前端使用异步ajax上传较快但不能包装文件先后顺序 数据并不是有序的此方法不可用
//        for (int min = 0, max = files.length - 1; min < max; min++, max--) {
//            // 对数组的元素进行位置交换
//            File temp = files[min]; // 定义空变量 保存下标为min的元素 min就为空
//            files[min] = files[max];
//            files[max] = temp;
//        }

        File resultFile = new File(resultPath);
        try {
            // FileOutputStream会造成内存泄露 每次操作文件内容 会先把文件读到内存
            // FileChannel fileChannel = new FileOutputStream(resultFile, true).getChannel();

            // RandomAccessFile可随机访问文件 即支持任意位置读写 操作时零内存
            FileChannel fileChannel = new RandomAccessFile(resultFile, "rw").getChannel();

            for (int i = 0; i < _files.length; i++) {
                // 虚拟内存 并不是物理内存 这个暂未搞定 暂时使用fileChannel合成文件
                // 使用内存映射文件能让你创建和修改那些因为太大而无法放入内存的文件 可认为文件已经全部读进了内存 然后把它当成大数组来访问
                // MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileDate.length);

                FileChannel blk = new FileInputStream(files[i]).getChannel();
                fileChannel.transferFrom(blk, fileChannel.size(), blk.size());
                blk.close();
            }
            fileChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 删除临时文件
        for (int i = 0; i < _files.length; i++) {
            files[i].delete();
        }
        log.info("文件合并成功文件为[{}]", resultFile);
        return true;
    }
}