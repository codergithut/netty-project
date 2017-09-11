package netty.tianjian.common.util.qrcode;

import com.google.zxing.WriterException;

import java.io.IOException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class Qrest {


    public static void qrest(String code, String url) {

        String content="http://localhost:9999/getResource?";
        String logUri="/Users/tianjian/IdeaProjects/springboot-wechart/src/main/resources/static/img/user/user3.jpg";
        String outFileUri="/Users/tianjian/IdeaProjects/nettyproject/netty-http-core/src/main/resources/static/img/" + code + ".jpg";
        int[]  size=new int[]{430,430};
        String format = "jpg";

        try {
            new QRCodeFactory().CreatQrImage(content, format, outFileUri, logUri,size);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
