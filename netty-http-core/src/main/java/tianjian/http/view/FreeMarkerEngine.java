package tianjian.http.view;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import tianjian.http.util.FileUtil;

import java.io.*;
import java.util.List;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description freemarker 引擎
 */
public class FreeMarkerEngine extends TemplateEngine {

    public FreeMarkerEngine(String suffix, String templatePath) {
        super(suffix, templatePath);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param templatePath 模板资源路径
     * @param o 模板数据
     * @return 经过模板化的文件
     * @description 模板文件处理并返回结果
     */
    public File changeTemplateByData(String templatePath, Object o) throws IOException, TemplateException {
        File file = new File("test");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        templateMap.get(templatePath).process(o, writer);
        writer.flush();
        String haha = new String(FileUtil.getFileAsBytes(file));
        System.out.println(haha);
        return file;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param templatePath 判断该路径下的资源文件路径
     * @return
     * @description 判断该静态文件是否存在
     */
    public boolean templateIsExist(String templatePath) {
        Template t = templateMap.get(templatePath);
        return (t == null ? false : true);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return void
     * @description 根据提供的路径查找并注册资源模板
     */
    public void InitTemplate() {
        try {
            Configuration freeMarkerCfg = new Configuration();
            File dirFile = new File(templatePath);
            freeMarkerCfg.setDirectoryForTemplateLoading(new File(templatePath));

            List<File> fileList = FileUtil.getFilesByPath(dirFile, new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(pathname.getName().endsWith("." + suffix)) {
                        return true;
                    }
                    return false;
                }
            });

            for(File templateFile : fileList) {
                template = freeMarkerCfg.getTemplate(templateFile.getName());
                templateMap.put(templateFile.getName(), template);
            }
            addTemplate("freemarker", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
