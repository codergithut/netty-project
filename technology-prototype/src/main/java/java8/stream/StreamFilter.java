package java8.stream;

import java8.lambda.LambadaFunction;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/28
 * @description
 */
public class StreamFilter {
    public static void main(String[] args) {
        List<Dish> menu = Dish.init();
        List<String> lowCaloricDishesName = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        List<String> lowcaloricDishesNameParalle = menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());

        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());
        System.out.println(threeHighCaloricDishNames);
        LambadaFunction.forEach(lowCaloricDishesName, System.out::println);

        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> s= title.stream();
        s.forEach(System.out::println);
        menu.forEach(System.out::println);

        List<Integer> numbers = Arrays.asList(1,2,1,3,3,2,4);
        numbers.stream()
                .filter(i -> i%2 == 0)
                .distinct()
                .forEach(System.out::println);

        List<Dish> dishes = menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
        dishes.stream().forEach(System.out::println);

        List<String> helloword = Arrays.asList("hello", "world");
        List xx = helloword.stream()
                .map(word -> word.split(""))
                .distinct()
                .collect(toList());
        xx.stream().forEach(System.out::println);

        String[] s1 = "hello".split("");
        System.out.println();

        List yy = helloword.stream()
                .map(word -> word.split(""))
                .map(Arrays::stream).distinct()
                .collect(toList());
        yy.stream().forEach(System.out::println);

        List zz = helloword.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(toList());

        zz.stream().forEach(System.out::println);

        List<Integer> number1 = Arrays.asList(1,2,3);
        List<Integer> number2 = Arrays.asList(3,4);
        List<Integer[]> value = number1.stream()
                .flatMap( i -> number2.stream().map(j -> new Integer[]{i, j}))
                .collect(toList());
        List<Integer[]> values = number1.stream()
                .flatMap( i -> number2.stream()
                        .filter(j -> (i+j) %3 ==0)
                        .map(j -> new Integer[]{i, j})
                )
                .collect(toList());

        if(menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!");
        }

        boolean isHealthy = menu.stream().allMatch(d -> d.getCalories() < 1000);
        System.out.println(isHealthy);
    }

}
