package scan.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/30
 * @description
 */
public class FileMetadata {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 文件名称
     */
    private String fileName;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 文件类型
     */
    private String type;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 文件内容
     */
    private String data;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 文件大小
     */
    private double size;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 创建日期
     */
    private Date date;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 文件路径
     */
    private String path;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 父目录
     */
    private String parentDir;

    public String getParentDir() {
        return parentDir;
    }

    public void setParentDir(String parentDir) {
        this.parentDir = parentDir;
    }

    private boolean isDirector;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDirector() {
        return isDirector;
    }

    public void setDirector(boolean director) {
        isDirector = director;
    }

    public Map<String,Object> getEsInsertData() {
        Map<String,Object> detail = new HashMap<String,Object>();
        detail.put("size", size);
        detail.put("date", date);
        detail.put("type", type);
        detail.put("filename", fileName);
        detail.put("data", data);
        detail.put("path", path);
        detail.put("parent", parentDir);
        return detail;
    }
}
