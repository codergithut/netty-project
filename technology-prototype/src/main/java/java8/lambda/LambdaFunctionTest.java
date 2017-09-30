package java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

import static java.util.Comparator.comparing;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/28
 * @description
 */
public class LambdaFunctionTest {
    public static void main(String[] args) throws Exception {
        List<String> a = new ArrayList<String>();
        a.add("tianjian");
        a.add(" ");
        a.add("dasheng");
        a.add("happy");
        Callable<Integer> c = () -> {
            int i = 1;
            while(i < 100000) {
                i = i*(++i);
                Thread.sleep(100);
            }
            System.out.println("end");
            return i;};
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future = pool.submit(c);
        List<String> data = LambadaFunction.filter(a, (String s) -> !s.isEmpty() && s.length() > 2);

        //List<String> data1 = LambadaFunction.filter(a, String::isEmpty);

        List<Integer> integerList = LambadaFunction.map(a, String::length);
        LambadaFunction.forEach(data, System.out::println);
        LambadaFunction.forEach(integerList, System.out::println);
        System.out.println("result " + future.get());

        final int portNumber = 1337;
        Runnable r = () -> System.out.println(portNumber);
        r.run();

        Function<Integer, Apple> apple = (weight) -> new Apple(weight);

        System.out.println(apple.apply(22).getName());

        data.sort(comparing(String::length));

        Function<String,Integer> StringToInteger = Integer::parseInt;

        LambadaFunction.forEach(data, System.out::println);

        List<Integer> weights = Arrays.asList(2,3,2,3,4,5);

        List<Apple> apples = LambadaFunction.map(weights, Apple::new);

        LambadaFunction.forEach(apples, (detail) -> System.out.println(detail.getWeight()));

        System.out.println("last" +LambadaFunction.integerate(LambadaFunction::f, 3, 7));



    }
}
