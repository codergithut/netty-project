package java8;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/28
 * @description
 */
public class LamabdaFile {
    public static String processFile(BufferedReaderProcessor p) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\HTML&&JS&&VUE&&ECHAET\\VUE\\test.html"));
            return p.process(br);
        } catch (Exception e) {
            e.toString();
        }
        return null;
    }


    public static void main(String[] args) {
        String result = processFile((BufferedReader br) -> br.readLine().toString() );
        System.out.println(result);

    }


}
