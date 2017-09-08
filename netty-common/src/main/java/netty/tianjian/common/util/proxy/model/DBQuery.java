package netty.tianjian.common.util.proxy.model;
/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class DBQuery implements IDBQuery {


    public DBQuery(){
        try {
            Thread.sleep(1000); //可能包含数据库连接等耗时操作
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String request() {

        return "request string";
    }

}