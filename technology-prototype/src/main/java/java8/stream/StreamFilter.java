package java8.stream;

import java8.lambda.LambadaFunction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
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

        boolean isHealthy1 = menu.stream().noneMatch(d -> d.getCalories() >= 1000);

        System.out.println(isHealthy1);

        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(d -> System.out.println(d.getName()));

        List<Integer> someNumbers = Arrays.asList(1,2,3,4,5);
        Optional<Integer> firstSquareDivisibleByThree =
                someNumbers.stream()
                .map(x -> x*x)
                .filter(x -> x%3 == 0)
                .findFirst();
        firstSquareDivisibleByThree.ifPresent(System.out::println);

        int total = menu.stream().map(Dish::getCalories).reduce(0, Integer::sum);

        System.out.println(total);

        menu.stream().map(Dish::getCalories).reduce((x, y) -> x < y ? x : y).ifPresent(System.out::println);

        menu.stream().map(Dish::getCalories).reduce((x, y) -> x > y ? x : y).ifPresent(System.out::println);

        Optional<Integer> min = menu.stream().map(Dish::getCalories).reduce(Integer::min);

        Optional<Integer> max = menu.stream().map(Dish::getCalories).reduce(Integer::max);

        min.ifPresent(System.out::println);

        max.ifPresent(System.out::println);

        System.out.println(menu.stream().map(d -> 1).reduce(0, Integer::sum));

        System.out.println(menu.stream().count());

        int colories = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> stram = intStream.boxed();



    }

}
