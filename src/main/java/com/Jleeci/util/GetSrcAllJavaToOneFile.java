package com.Jleeci.util;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSrcAllJavaToOneFile {
    public static final String JleeciLine = LoadProperty.pro.get("JleeciLine")+"";
    public static final String JleeciLineRegex = LoadProperty.pro.get("JleeciLineRegex")+"";

    public static String fileDesc = "fileDesc";
    public static String prefix=LoadProperty.pro.get("prefix")+"";
    public static String stuffix=LoadProperty.pro.get("stuffix")+"";

    public static String fileDescrible =  prefix+ fileDesc + stuffix;
    public static final String allSourceFileContent = "JleeciFile.txt";

    public static void writeAllSrcFileContentToOneFile(String pathName) throws IOException {
        List<String> allFileList = OperationFile.getAllFileOn(pathName);
        addOrRemove(allFileList);
        for (String fileAbsolutePath : allFileList) {
            fileDescrible = prefix + fileAbsolutePath + stuffix;
            String fileContent = OperationFile.getFileContentByLine(fileAbsolutePath);
            if (fileContent != null && !"".equals(fileContent)) {
                fileContent = fileDescrible + new String(fileContent.getBytes(), "GBK") + JleeciLine;
                OperationFile.writeContentToFile(fileContent.getBytes(), allSourceFileContent, true);
            }
        }
    }

    private static void addOrRemove(List<String> allFileList) throws IOException {
        String currentProjectPath=new File("").getCanonicalPath();
        allFileList.remove(currentProjectPath+"\\src\\main\\java\\com\\Jleeci\\util\\GetSrcAllJavaToOneFile.java");
        allFileList.remove(currentProjectPath+"\\src\\main\\resources\\util.properties");
        allFileList.add(currentProjectPath+"\\pom.xml");
    }

    public static void spiltContentToMoreFile() throws IOException {
        String srcFileContent = OperationFile.getFileContentByLine(allSourceFileContent);
        String[] filesContent = srcFileContent.split(JleeciLineRegex);
        for (String fileContent : filesContent) {
            processContent(fileContent);
        }

    }

    private static void processContent(String fileContent) throws IOException {
        String filePathRegex = "([=]{9}(&&))([\\S]+)((&&)[=]{9})";
        Pattern pattern = Pattern.compile(filePathRegex);
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find() && matcher.groupCount() > 3) {
            String pathName = matcher.group(3);
            fileContent = fileContent.replaceAll(filePathRegex, "");
            OperationFile.writeContentToFile(fileContent.getBytes(), pathName, false);
        }
    }

    public static void main(String[] args) throws IOException {
        String pathName = "C:\\Users\\6092002550\\Desktop\\download\\DailyUtil-master\\src";
        //writeAllSrcFileContentToOneFile(pathName);
        spiltContentToMoreFile();
    }
}
