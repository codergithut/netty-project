package netty.tianjian.common.util.file;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianian</a>
 * @version 1.0, 2017/8/11
 * @description
 */
@Service
public class FileUtil {

    public static byte[] getFileAsBytes(File file) throws IOException {
        ByteSource byteSource = null ;

        byteSource = Files.asByteSource(file);

        return byteSource.read();
    }


    public static boolean saveFileByByte(File file, byte[] content) {

        try {

            Files.write(content, file);

            return true;

        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public static String getFileAsString(File file) throws IOException {

        BufferedReader reader = null ;

        String fileString = null;

        try {

            reader = Files.newReader(file, Charsets.UTF_8);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } finally {

            reader.close();

            return fileString;

        }
    }

    public static boolean removeFile(File file) {

        return file.delete();

    }

    public static List<File> getFilesByPath(File file, FileFilter fileFilter) {

        List<File> allFile = new ArrayList<File>();

        if(!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();

        for(File fileDetail : files) {

            if(fileDetail.isFile()) {

                if(fileFilter != null && !fileFilter.accept(fileDetail)) {
                    continue;
                }

                allFile.add(fileDetail);
            }

            if(fileDetail.isDirectory()) {

                getFilesByPath(fileDetail, fileFilter);

            }
        }

        return allFile;
    }

    public static File saveStringToFile(String content, File file) throws IOException {
        FileUtils.writeStringToFile(file, content,"UTF-8");
        return file;
    }


}
