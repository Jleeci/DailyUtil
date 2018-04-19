package Util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 对文件进行
 *
 */
public class OperationFile {
    private static List<String> files = new ArrayList<String>();

    /**
     *
     * @param filePath
     *            给定一个文档路径，遍历里面所有的文档
     * @return 返回一个List集合，value 文件路径
     */
    public static List<String> getAllFileOn(String filePath) {
        List<String> tempFileList = new ArrayList<String>();
        try {
            readFilePathToMap(filePath);
            for (String file : files) {
                tempFileList.add(file);
            }
            return tempFileList;
        } finally {
            filesMapRemoveAll();
        }
    }

    /**
     *
     * @param filePath
     *            给定一个文档路径，遍历里面所有的文档
     * @return 返回一个List集合，value 文件路径
     */
    private static List<String> readFilePathToMap(String filePath) {
        File root = new File(filePath);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                readFilePathToMap(file.getAbsolutePath());
            } else {
                files.add(file.getAbsolutePath());// 防止同名文件
            }
        }
        return files;
    }

    /**
     * 清除全局变量 files
     */
    private static void filesMapRemoveAll() {
        files.clear();
    }

    /**
     * 读取某个文件(filePath)内容 以行为单位读取
     *
     * @param filePath
     * @return
     */
    public static String getFileContentByLine(String filePath) {
        StringBuffer fileContent = new StringBuffer();
        File file = new File(filePath);
        FileReader freader = null;
        BufferedReader reader = null;
        String tempString = null;
        try {
            freader = new FileReader(file);
            reader = new BufferedReader(freader);
            while ((tempString = reader.readLine()) != null) {
                fileContent.append(tempString + "\r\n");

            }
        } catch (IOException e) {
            System.out.println("read " + filePath + "fail " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    System.out.println("reader colsed fail." + e1);
                }
            }
            if (freader != null) {
                try {
                    freader.close();
                } catch (IOException e1) {
                    System.out.println("freader colsed fail." + e1);
                }
            }
        }
        return fileContent.toString();
    }

    /**
     * 读取某个文件(filePath)内容 以30字符为单位读取
     *
     * @param filePath
     * @return
     */
    public static String getFileContentBy30Character(String filePath) {
        StringBuffer fileContent = new StringBuffer();
        FileInputStream ins = null;
        InputStreamReader reader = null;
        try {
            char[] tempchars = new char[30];

            ins = new FileInputStream(filePath);
            reader = new InputStreamReader(ins);
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((reader.read(tempchars)) != -1) {
                fileContent.append(tempchars);
            }
        } catch (IOException e) {
            System.out.println("read " + filePath + "fail " + e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    System.out.println("inputstreamreader colsed fail." + e1);
                }
            }
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e1) {
                    System.out.println("inputstream colsed fail." + e1);
                }
            }
        }
        return fileContent.toString();
    }

    /**
     * 将内容写入文件
     *
     * @param content
     *            待写入的内容
     * @param filepath
     *            文件路径
     * @param append
     *            是否追加
     */
    public static void writeContentToFile(byte[] content, String filepath, boolean append) {
        FileOutputStream out = null;
        File file = null;

        try {
            file = new File(filepath);
            if (!file.exists()) {
                file.createNewFile();
            }
            out = new FileOutputStream(file, append);
            out.write(content);
            out.flush();
        } catch (FileNotFoundException e) {
            System.out.println("file not found Exception");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOExceptionIOException,write file fail.");
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将文件保存到字节数组中
     *
     * @param is
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bAOutputStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bAOutputStream.write(ch);
        }
        byte data[] = bAOutputStream.toByteArray();
        bAOutputStream.close();
        return data;
    }

    /**
     * 将文件转成base64 字符串
     *
     * @param path 文件路径
     * @return *
     * @throws Exception
     */

    public static String fileToEncodeBase64(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void decoderBase64ToFile(String base64Code, String targetPath) throws Exception {
        File file = new File(targetPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 将base64字符保存文本文件
     *
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */

    public static void strToTxtFile(String base64Code, String targetPath) throws Exception {
        File file = new File(targetPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }

    /**
     * 判断一个文件是否是二进制文件
     * @param file  待判定的文件
     * @return 待判定的文件是/否是二进制文件。
     */
    public static boolean isBinary(File file) throws IOException {
        boolean isBinary = false;
        FileInputStream fin =null;
        try {
             fin = new FileInputStream(file);
            long len = file.length();
            for (int j = 0; j < (int) len; j++) {
                int t = fin.read();
                if (t < 32 && t != 9 && t != 10 && t != 13) {//结合
                    isBinary = true;
                    break;
                }
            }
        } catch (Exception e){
            throw new IOException(e);
        }finally {
            try{
                fin.close();
            } catch (IOException e) {
                throw new IOException("close FileInputStream fail.",e);
            }
        }
        return isBinary;
    }
}
