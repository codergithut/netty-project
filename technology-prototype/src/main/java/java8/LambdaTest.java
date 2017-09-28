package java8;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/28
 * @description
 */
public class LambdaTest {
    public static void main(String[] args) {
        Runnable r1 = () -> {
            int i = 0;
            while(true) {
                System.out.println(i++);
            }
        };
        r1.run();
    }
}
