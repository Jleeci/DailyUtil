

package com.Jleeci.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/** 
 * Created by Jleeci on 2018/5/31.
 */
public class OperationFile {
    private static List<String> files = new ArrayList<String>();

    /**
     * @param filePath 给定一个文档路径，遍历里面所有的文档
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
     * @param filePath 给定一个文档路径，遍历里面所有的文档
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
    public static String getFileContentByLine(String filePath) throws IOException {
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
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (freader != null) {
                freader.close();
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
    public static String getFileContentBy30Character(String filePath) throws IOException {
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
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (ins != null) {
                ins.close();
            }
        }
        return fileContent.toString();
    }

    /**
     * 将内容写入文件
     *
     * @param content  待写入的内容
     * @param filepath 文件路径
     * @param append   是否追加
     */
    public static void writeContentToFile(byte[] content, String filepath, boolean append) throws IOException {
        FileOutputStream out = null;
        File file = null, parentFile = null;

        try {
            file = new File(filepath);
            if (!file.exists()) {
                if (file.getParent() != null && !"".equals(file.getParent())) {
                    parentFile = new File(file.getParent());
                    if (parentFile != null && !parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                }
                file.createNewFile();
            }
            out = new FileOutputStream(file, append);
            out.write(content);
            out.flush();
        } finally {
            out.close();
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
        ByteArrayOutputStream bAOutputStream = null;
        byte data[] = null;
        try {
            bAOutputStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bAOutputStream.write(ch);
            }
            data = bAOutputStream.toByteArray();
        } finally {
            bAOutputStream.close();
        }
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
        FileInputStream inputFile = null;
        byte[] buffer = null;
        try {
            inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
        } finally {
            inputFile.close();
        }

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
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        } finally {
            out.close();
        }

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
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(targetPath);
            out.write(buffer);
        } finally {
            out.close();
        }

    }

    /**
     * 判断一个文件是否是二进制文件
     *
     * @param file 待判定的文件
     * @return 待判定的文件是/否是二进制文件。
     */
    public static boolean isBinary(File file) throws IOException {
        boolean isBinary = false;
        FileInputStream fin = null;
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
        } finally {
            fin.close();
        }
        return isBinary;
    }
}
