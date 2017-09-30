package scan;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/30
 * @description
 */
public class ScanFileUtil {

    public static List<String> getSystemView(String[] args){

        List<String> systemView = new ArrayList<String>();

        // 当前文件系统类
        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fsv.getSystemDisplayName(fs[i]));
            systemView.add(fsv.getSystemDisplayName(fs[i]));
        }

        return systemView;
    }

    public static Date getChangeTime(File f) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(f.lastModified());
        String info = sdf.format(cal.getTime());
        return sdf.parse(info);
    }

    public static List<File> getFilesByPath(File file, FileFilter fileFilter, List<File> allFile) {

        if(!file.exists()) {
            return null;
        }

        File[] files = file.listFiles();

        if(files != null &&files.length > 0) {
            for(File fileDetail : files) {

                if(fileDetail.isFile()) {

                    if(fileFilter != null && !fileFilter.accept(fileDetail)) {
                        continue;
                    }
                    allFile.add(fileDetail);
                }

                if(fileDetail.isDirectory()) {
                    allFile.add(fileDetail);
                    getFilesByPath(fileDetail, fileFilter, allFile);

                }
            }
        }
        return allFile;
    }

}
