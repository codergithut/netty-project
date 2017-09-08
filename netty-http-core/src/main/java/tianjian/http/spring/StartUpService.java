package tianjian.http.spring;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description 启动服务
 */
public class StartUpService {
    public static void main(String[] args) {
        ScanSpringContext scanSpringContext = new ScanSpringContext();
        scanSpringContext.run(args);
    }
}
