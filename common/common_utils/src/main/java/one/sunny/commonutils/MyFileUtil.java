package one.sunny.commonutils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


public class MyFileUtil {
    public static File MultipartFileToFile(MultipartFile multiFile) {
        if (multiFile == null){
            return null;
        }
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, suffix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String readFileByTrimLines( File file) {
        BufferedReader reader = null;
        String result = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            int line = 1;
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                result += tempString.trim();
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return result;
    }
    public static String md5FileByTrimLines( File file) {
        return SecureUtil.md5(readFileByTrimLines(file));
    }
}
