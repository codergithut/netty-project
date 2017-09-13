package tianjian.http.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description spring 扫描器
 */
public class ScanSpringContext {
    private List<String> scanPaths = new ArrayList<String>();
    private List<Class> scanConfigs = new ArrayList<Class>();
    public void run(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        if(scanConfigs.size() > 0) {
            for(Class config : scanConfigs) {
                ctx.register(config);
            }
        }

        if(scanPaths.size() > 0) {
            for(String config : scanPaths) {
                ctx.scan(config);
            }
        }

        ctx.register(NettyServerConfig.class);
        ctx.refresh();
    }

    public void addScanPath(String path) {
        scanPaths.add(path);
    }

    public void addScanConfig(Class config) {
        scanConfigs.add(config);
    }

    public List<String> getScanPaths() {
        return scanPaths;
    }

    public void setScanPaths(List<String> scanPaths) {
        this.scanPaths = scanPaths;
    }

    public List<Class> getScanConfigs() {
        return scanConfigs;
    }

    public void setScanConfigs(List<Class> scanConfigs) {
        this.scanConfigs = scanConfigs;
    }
}
