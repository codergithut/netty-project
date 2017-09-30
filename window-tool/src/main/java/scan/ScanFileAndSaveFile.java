package scan;

import com.alibaba.fastjson.JSON;
import netty.tianjian.common.util.elastic.server.ElasticServer;
import netty.tianjian.common.util.file.FileUtil;
import scan.model.FileMetadata;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/30
 * @description
 */
public class ScanFileAndSaveFile {



    public static void main(String[] args) throws IOException, ParseException {
        List<File> allFiles = new ArrayList<File>();
        File[] fs = File.listRoots();
        List<FileMetadata> fileMetadatas = new ArrayList<FileMetadata>();

        for(File file : fs) {
            allFiles.addAll(ScanFileUtil.getFilesByPath(file, null, new ArrayList<File>()));
            break;
        }


        for(File f : allFiles) {
            FileMetadata fileMetadata = new FileMetadata();
            fileMetadata.setDate(ScanFileUtil.getChangeTime(f));
            String fileName = f.getName();
            fileMetadata.setFileName(fileName);
            String[] sufix = fileName.split("\\.");
            if(sufix.length > 0){
                fileMetadata.setType(sufix[sufix.length-1]);
            }
            if(fileName.endsWith(".java") || fileName.endsWith(".txt") || fileName.endsWith(".xml")) {
                fileMetadata.setData(new String(FileUtil.getFileAsBytes(f), "UTF-8"));
            }
            fileMetadata.setDirector(f.isDirectory());
            fileMetadata.setSize(f.length());
            fileMetadata.setPath(f.getAbsolutePath());
            fileMetadata.setParentDir(f.getParent());
            fileMetadatas.add(fileMetadata);

            ElasticServer.saveDataToEs(fileMetadata.getEsInsertData(), "window_search", "system");
        }

        FileUtil.saveStringToFile(JSON.toJSONString(fileMetadatas), new File("D:\\filemeata.index"));

    }

}
