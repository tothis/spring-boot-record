package com.example.util;

import lombok.SneakyThrows;
import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @author 李磊
 * @time 2018/4/10 23:50
 * @description
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void main(String[] args) {
        testPath("common/pom.xml");
        System.out.println("----- -----");

        // classpath下的文件
        InputStream resource = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("application.yml");
        byte[] result1 = new byte[4];
        resource.read(result1);
        resource.close();
        System.out.println("成功 -> " + new String(result1));

        File file = ResourceUtils.getFile("classpath:test.json");
        byte[] result2 = new byte[4];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(result2);
        inputStream.close();
        System.out.println("成功 -> " + new String(result2));
    }

    /**
     * 测试文件路径
     */
    public static void testPath(String filePath) {
        String suffix = "/" + filePath;

        testRead(suffix); // 失败
        testRead(filePath);
        testRead(System.getProperty("user.dir") + suffix);

        // 相对路径
        // read(new File("../").getPath() + suffix);
        testRead(new File("./").getPath() + suffix);
        testRead(new File(".").getPath() + suffix);
        testRead(new File("").getPath() + suffix); // 失败
        testRead(new File("").getPath() + filePath);

        // 绝对路径
        // read(new File("../").getAbsolutePath() + suffix);
        testRead(new File("./").getAbsolutePath() + suffix);
        testRead(new File(".").getAbsolutePath() + suffix);
        testRead(new File("").getAbsolutePath() + suffix);

        try {
            // getCanonicalPath()+suffix '.'表示当前目录 '..'表示当前目录的上一级目录
            // read(new File("../").getCanonicalPath() + suffix);
            testRead(new File("./").getCanonicalPath() + suffix);
            testRead(new File(".").getCanonicalPath() + suffix);
            testRead(new File("").getCanonicalPath() + suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testRead(String path) {
        byte[] result = new byte[12];
        try (InputStream in = new FileInputStream(new File(path))) {
            in.read(result);
            System.out.println("成功 -> " + new String(result));
        } catch (FileNotFoundException e) {
            System.out.println("失败 -> " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}