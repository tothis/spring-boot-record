package com.example.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author 李磊
 * @time 2018/4/10 23:50
 * @description
 */
public final class FileUtil {
    private FileUtil() {
    }

    /**
     * 修改文件的最后访问时间 如果文件不存在则创建该文件
     *
     * @param file
     */
    public static boolean touch(File file) {
        long currentTime = System.currentTimeMillis();
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("succeeded");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file.setLastModified(currentTime);
    }

    public static boolean touch(String fileName) {
        return touch(new File(fileName));
    }

    public static boolean touch(File[] files) {
        for (int i = 0; i < files.length; i++) {
            if (!touch(files[i])) return false;
        }
        return true;
    }

    public static boolean touch(String[] fileNames) {
        File[] files = new File[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            files[i] = new File(fileNames[i]);
        }
        return touch(files);
    }

    /**
     * 判断指定的文件是否存在
     *
     * @param fileName 要判断的文件的文件名
     * @return 存在时返回true 否则返回false
     */
    public static boolean isExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 创建指定的目录
     * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录
     * <b>注意 可能会在返回false的时候创建部分父目录</b>
     *
     * @param file 要创建的目录
     * @return 完全创建成功时返回true 否则返回false
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * 创建指定的目录
     * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录
     * <b>注意 可能会在返回false的时候创建部分父目录</b>
     *
     * @param fileName 要创建的目录的目录名
     * @return 完全创建成功时返回true 否则返回false
     */
    public static boolean makeDirectory(String fileName) {
        File file = new File(fileName);
        return makeDirectory(file);
    }

    /**
     * 清空指定目录中的文件
     * 这个方法将尽可能删除所有的文件 但是只要有一个文件没有被删除都会返回false
     * 另外这个方法不会迭代删除 即不会删除子目录及其内容
     *
     * @param directory 要清空的目录
     * @return 目录下的所有文件都被成功删除时返回true 否则返回false
     */
    public static boolean emptyDirectory(File directory) {
        boolean result = true;
        File[] entries = directory.listFiles();
        for (int i = 0; i < entries.length; i++) {
            if (!entries[i].delete()) {
                result = false;
            }
        }
        return result;
    }

    public static boolean emptyDirectory(String directoryName) {
        File dir = new File(directoryName);
        return emptyDirectory(dir);
    }

    /**
     * 删除指定目录及其中的所有内容
     *
     * @param dirName 要删除的目录的目录名
     * @return 删除成功时返回true 否则返回false
     */
    public static boolean deleteDirectory(String dirName) {
        return deleteDirectory(new File(dirName));
    }

    /**
     * 删除指定目录及其中的所有内容
     *
     * @param dir 要删除的目录
     * @return 删除成功时返回true 否则返回false
     */
    public static boolean deleteDirectory(File dir) {
        if ((dir == null) || !dir.isDirectory()) {
            throw new IllegalArgumentException("Argument " + dir +
                    " is not a directory");
        }

        File[] entries = dir.listFiles();
        int sz = entries.length;

        for (int i = 0; i < sz; i++) {
            if (entries[i].isDirectory()) {
                if (!deleteDirectory(entries[i])) {
                    return false;
                }
            } else {
                if (!entries[i].delete()) {
                    return false;
                }
            }
        }

        if (!dir.delete()) {
            return false;
        }
        return true;
    }

    /**
     * 列出目录中的所有内容 包括其子目录中的内容
     *
     * @param file   要列出的目录
     * @param filter 过滤器
     * @return 目录内容的文件数组
     */
    public static File[] listAll(File file, javax.swing.filechooser.FileFilter filter) {
        ArrayList list = new ArrayList();
        File[] files;
        if (!file.exists() || file.isFile()) {
            return null;
        }
        list(list, file, filter);
        files = new File[list.size()];
        list.toArray(files);
        return files;
    }

    /**
     * 将目录中的内容添加到列表
     *
     * @param list   文件列表
     * @param filter 过滤器
     * @param file   目录
     */
    private static void list(ArrayList list, File file,
                             javax.swing.filechooser.FileFilter filter) {
        if (filter.accept(file)) {
            list.add(file);
            if (file.isFile()) {
                return;
            }
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                list(list, files[i], filter);
            }
        }

    }

    /**
     * 返回文件的URL地址
     *
     * @param file 文件
     * @return 文件对应的的URL地址
     * @throws MalformedURLException
     * @deprecated 在实现的时候没有注意到File类本身带一个toURL方法将文件路径转换为URL
     * 请使用File.toURL方法
     */
    public static URL getURL(File file) throws MalformedURLException {
        String fileURL = "file:/" + file.getAbsolutePath();
        URL url = new URL(fileURL);
        return url;
    }

    /**
     * 从文件路径得到文件名
     *
     * @param filePath 文件的路径 可以是相对路径也可以是绝对路径
     * @return 对应的文件名
     */
    public static String getFileName(String filePath) {
        return new File(filePath).getName();
    }

    /**
     * 从文件名得到文件绝对路径
     *
     * @param fileName 文件名
     * @return 对应的文件路径
     */
    public static String getFilePath(String fileName) {
        return new File(fileName).getAbsolutePath();
    }

    /**
     * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径
     * 其实就是将路径中的"\"全部换为"/" 因为在某些情况下我们转换为这种方式比较方便
     * 某中程度上说"/"比"\"更适合作为路径分隔符 而且DOS/Windows也将它当作路径分隔符
     *
     * @param filePath 转换前的路径
     * @return 转换后的路径
     */
    public static String toUNIXpath(String filePath) {
        return filePath.replace('\\', '/');
    }

    /**
     * 从文件名得到UNIX风格的文件绝对路径
     *
     * @param fileName 文件名
     * @return 对应的UNIX风格的文件路径
     * @see #toUNIXpath(String filePath) toUNIXpath
     */
    public static String getUNIXfilePath(String fileName) {
        return toUNIXpath(new File(fileName).getAbsolutePath());
    }

    /**
     * 得到文件的类型
     * 实际上就是得到文件名中最后一个"."后面的部分
     *
     * @param fileName 文件名
     * @return 文件名中的类型部分
     */
    public static String getTypePart(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 得到文件的类型
     * 实际上就是得到文件名中最后一个"."后面的部分
     *
     * @param file 文件
     * @return 文件名中的类型部分
     */
    public static String getFileType(File file) {
        return getTypePart(file.getName());
    }

    /**
     * 得到文件的名字部分
     * 实际上就是路径中的最后一个路径分隔符后的部分
     *
     * @param fileName 文件名
     * @return 文件名中的名字部分
     */
    public static String getNamePart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                } else {
                    return fileName.substring(0, point);
                }
            } else {
                return fileName.substring(secondPoint + 1, point);
            }
        } else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * 得到文件名中的父路径部分
     * 对两种路径分隔符都有效
     * 不存在时返回""
     * 如果文件名是以路径分隔符结尾的则不考虑该分隔符 例如"/path/"返回""
     *
     * @param fileName 文件名
     * @return 父路径 不存在或者已经是父目录时返回""
     */
    public static String getPathPart(String fileName) {
        int point = getPathLsatIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return "";
        } else if (point == length - 1) {
            int secondPoint = getPathLsatIndex(fileName, point - 1);
            if (secondPoint == -1) {
                return "";
            } else {
                return fileName.substring(0, secondPoint);
            }
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * 得到路径分隔符在文件路径中首次出现的位置
     * 对于DOS或者UNIX风格的分隔符都可以
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中首次出现的位置 没有出现时返回-1
     */
    public static int getPathIndex(String fileName) {
        int point = fileName.indexOf('/');
        if (point == -1) {
            point = fileName.indexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置后首次出现的位置
     * 对于DOS或者UNIX风格的分隔符都可以
     *
     * @param fileName  文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置后首次出现的位置 没有出现时返回-1
     */
    public static int getPathIndex(String fileName, int fromIndex) {
        int point = fileName.indexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.indexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置
     * 对于DOS或者UNIX风格的分隔符都可以
     *
     * @param fileName 文件路径
     * @return 路径分隔符在路径中最后出现的位置 没有出现时返回-1
     */
    public static int getPathLsatIndex(String fileName) {
        int point = fileName.lastIndexOf('/');
        if (point == -1) {
            point = fileName.lastIndexOf('\\');
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置
     * 对于DOS或者UNIX风格的分隔符都可以
     *
     * @param fileName  文件路径
     * @param fromIndex 开始查找的位置
     * @return 路径分隔符在路径中指定位置前最后出现的位置 没有出现时返回-1
     */
    public static int getPathLsatIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf('/', fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf('\\', fromIndex);
        }
        return point;
    }

    /**
     * 将文件名中的类型部分去掉
     *
     * @param filename 文件名
     * @return 去掉类型部分的结果
     */
    public static String trimType(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    /**
     * 得到相对路径
     * 文件名不是目录名的子节点时返回文件名
     *
     * @param pathName 目录名
     * @param fileName 文件名
     * @return 得到文件名相对于目录名的相对路径 目录下不存在该文件时返回文件名
     */
    public static String getSubpath(String pathName, String fileName) {
        int index = fileName.indexOf(pathName);
        if (index != -1) {
            return fileName.substring(index + pathName.length() + 1);
        } else {
            return fileName;
        }
    }

    /**
     * 检查给定目录的存在性
     * 保证指定的路径可用 如果指定的路径不存在 那么建立该路径 可以为多级路径
     *
     * @param path
     * @return 真假值
     */
    public static boolean pathValidate(String path) {
        String[] arraypath = path.split("/");
        String tmppath = "";
        for (int i = 0; i < arraypath.length; i++) {
            tmppath += "/" + arraypath[i];
            File d = new File(tmppath.substring(1));
            if (!d.exists()) { // 检查Sub目录是否存在
                System.out.println(tmppath.substring(1));
                if (!d.mkdir()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 读取文件的内容
     * 读取指定文件的内容
     *
     * @param path 为要读取文件的绝对路径
     * @return 以行读取文件后的内容
     */
    public static String getFileContent(String path) throws IOException {
        String filecontent = "";
        try {
            File f = new File(path);
            if (f.exists()) {
                FileReader fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr); // 建立BufferedReader对象 并实例化为br
                String line = br.readLine(); // 从文件读取一行字符串
                // 判断读取到的字符串是否不为空
                while (line != null) {
                    filecontent += line + "\n";
                    line = br.readLine(); // 从文件中继续读取一行数据
                }
                br.close(); // 关闭BufferedReader对象
                fr.close(); // 关闭文件
            }
        } catch (IOException e) {
            throw e;
        }
        return filecontent;
    }

    /**
     * 根据内容生成文件
     *
     * @param path          要生成文件的绝对路径
     * @param modulecontent 文件的内容
     * @return 真假值
     */
    public static boolean genModuleTpl(String path, String modulecontent) throws IOException {

        path = getUNIXfilePath(path);
        String[] patharray = path.split("\\/");
        String modulepath = "";
        for (int i = 0; i < patharray.length - 1; i++) {
            modulepath += "/" + patharray[i];
        }
        File d = new File(modulepath.substring(1));
        if (!d.exists()) {
            if (!pathValidate(modulepath.substring(1))) {
                return false;
            }
        }
        try {
            FileWriter fw = new FileWriter(path); // 建立FileWriter对象 并实例化fw
            // 将字符串写入文件
            fw.write(modulecontent);
            fw.close();
        } catch (IOException e) {
            throw e;
        }
        return true;
    }

    /**
     * 获取图片文件的扩展名 发布系统专用
     *
     * @param picPath 为图片名称加上前面的路径不包括扩展名
     * @return 图片的扩展名
     */
    public static String getPicExtendName(String picPath) {
        picPath = getUNIXfilePath(picPath);
        String picExtend = "";
        if (isExist(picPath + ".gif")) {
            picExtend = ".gif";
        }
        if (isExist(picPath + ".jpeg")) {
            picExtend = ".jpeg";
        }
        if (isExist(picPath + ".jpg")) {
            picExtend = ".jpg";
        }
        if (isExist(picPath + ".png")) {
            picExtend = ".png";
        }
        return picExtend; // 返回图片扩展名
    }

    /**
     * 拷贝文件
     *
     * @param in  输入文件
     * @param out 输出文件
     * @return
     * @throws Exception
     */
    public static boolean copyFile(File in, File out) {
        try (
                FileInputStream inputStream = new FileInputStream(in);
                FileOutputStream outputStream = new FileOutputStream(out)

        ) {
            byte[] buffer = new byte[1024];
            int i;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 拷贝文件
     *
     * @param infile  输入字符串
     * @param outfile 输出字符串
     * @return
     * @throws Exception
     */
    public static boolean copyFile(String infile, String outfile) {
        return copyFile(new File(infile), new File(outfile));

    }

    /**
     * 把内容content写的path文件中
     *
     * @param content 输入内容
     * @param path    文件路径
     * @return
     */
    public static boolean saveFileAs(String content, String path) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(path), false);
            if (content != null) {
                fileWriter.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 以字节为单位读取文件 常用于读二进制文件 如图片 声音 影像等文件
     */
    public static byte[] readFileByBytes(String fileName) {

        byte[] bytes = null;
        try (
                FileInputStream in = new FileInputStream(fileName);
                ByteArrayOutputStream out = new ByteArrayOutputStream(1024)
        ) {
            byte[] temp = new byte[1024];
            int size;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            bytes = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * 以字符为单位读取文件 常用于读文本 数字等类型的文件
     */
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        try (// 一次读一个字符
             Reader reader = new InputStreamReader(new FileInputStream(file))) {
            System.out.println("以字符为单位读取文件内容 一次读一个字节");

            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下 \r\n这两个字符在一起时 表示一个换行
                // 但如果这两个字符分开显示时 会换两次行
                // 因此 屏蔽掉\r 或者屏蔽\n 否则 将会多出很多空行
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("以字符为单位读取文件内容 一次读多个字节");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            Reader reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中 charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 以行为单位读取文件 常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String fileName) {
        List<String> result = new ArrayList<>();
        File file = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String tempString;
            int line = 1;
            // 一次读入一行 直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                result.add(tempString);
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 随机读取文件内容
     */
    public static void readFileByRandomAccess(String fileName) {
        try (
                // 打开一个随机访问文件流 按只读方式
                RandomAccessFile randomFile = new RandomAccessFile(fileName, "r")
        ) {
            System.out.println("随机读取一段文件内容");
            // 文件长度 字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节 如果文件内容不足10个字节 则读剩下的字节
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容
     *
     * @param fileName
     * @return
     */
    public static String readFileAll(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        long length = file.length();
        byte[] bytes = new byte[(int) length];
        try (FileInputStream in = new FileInputStream(file)) {
            in.read(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    public static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /// 以上为未验证方法 以下为已验证方法

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

    /**
     * 文件打包的方法
     *
     * @param directoryName 打包目录
     * @param zipName       打包文件名称
     * @author 李磊
     * @datetime 2019/12/17 14:12
     */
    public static void packFile(String zipName, String directoryName) {

        File[] files = new File(directoryName).listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        // 定义字节流
        byte[] buffer = new byte[1024];
        try (
                // 定义zip流 定义打包文件名和存放的路径
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipName))
        ) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                // 判断文件是否为空
                if (file.exists()) {
                    // 创建输入流
                    FileInputStream inputStream = new FileInputStream(file);
                    // 获取文件名
                    String name = file.getName();
                    // 创建zip对象
                    ZipEntry zipEntry = new ZipEntry(name);
                    out.putNextEntry(zipEntry);
                    int len;
                    // 读入需要下载的文件的内容 打包到zip文件
                    while ((len = inputStream.read(buffer)) > 0) {
                        out.write(buffer, 0, len);
                    }
                    out.closeEntry();
                    inputStream.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "D:\\data\\test\\";
        packFile(path + "test.zip", path);
    }
}