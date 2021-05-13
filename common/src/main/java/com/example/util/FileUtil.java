package com.example.util;

import cn.hutool.core.io.IORuntimeException;

import java.io.*;

/**
 * @author 李磊
 */
public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 拷贝文件
     *
     * @param in  输入文件
     * @param out 输出文件
     * @return
     * @throws Exception
     */
    public static void copyFile(File in, File out) {
        try (
                FileInputStream inputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out)
        ) {
            byte[] buffer = new byte[1024];
            int i;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拷贝文件
     *
     * @param infile  输入文件
     * @param outfile 输出文件
     * @return
     * @throws Exception
     */
    public static void copyFile(String infile, String outfile) {
        copyFile(new File(infile), new File(outfile));
    }

    /**
     * 创建文件
     *
     * @param files
     */
    public static void createFile(File... files) {
        for (File file : files) {
            // 文件所在目录不存在无法直接创建文件
            if (!file.exists()) {
                createDirectory(file.getParentFile());
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createFile(String... files) {
        createFile(files(files));
    }

    /**
     * 创建目录
     *
     * @param files
     */
    public static void createDirectory(File... files) {
        for (File file : files) {
            if (!file.exists()) {
                file.mkdir();
            } else if (file.exists() && !file.isDirectory()) {
                throw new RuntimeException(file.getName() + "文件已存在");
            }
        }
    }

    public static void createDirectory(String... files) {
        createDirectory(files(files));
    }

    /**
     * 把String数组转为File数组
     *
     * @param files
     * @return
     */
    private static File[] files(String... files) {
        File[] _files = new File[files.length];
        for (int i = 0; i < files.length; i++) {
            _files[i] = new File(files[i]);
        }
        return _files;
    }

    public static String readString(File file) {
        try (InputStream in = new FileInputStream(file);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = in.read(bytes)) != -1) {
                output.write(bytes, 0, length);
            }
            return new String(output.toByteArray());
        } catch (FileNotFoundException e) {
            throw new IORuntimeException("文件不存在");
        } catch (IOException e) {
            throw new IORuntimeException("文件读取失败");
        }
    }
}