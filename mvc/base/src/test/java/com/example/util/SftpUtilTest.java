package com.example.util;

import com.example.BaseApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest(classes = BaseApplication.class)
class SftpUtilTest {

    private InputStream localFileInput;
    private OutputStream localFileOutput;

    {
        try {
            localFileInput = new FileInputStream("D:/data/upload/file/file1.jpg");
            localFileOutput = new FileOutputStream("D:/data/upload/file/file2.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void upload() {
        SftpUtil.upload("file1.jpg", localFileInput);
    }

    @Test
    void download() {
        SftpUtil.download("file1.jpg", localFileOutput);
    }
}