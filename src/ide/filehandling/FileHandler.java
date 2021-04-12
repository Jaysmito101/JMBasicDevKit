package ide.filehandling;

import utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    public static boolean saveFile(File file, String projectFileContent) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bytes = StringUtils.textToHex(projectFileContent).getBytes(StandardCharsets.UTF_8);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static String loadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            String projectFileContent = StringUtils.hexToText(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
            inputStream.close();
            return  projectFileContent;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
