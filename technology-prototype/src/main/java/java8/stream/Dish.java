package java8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/28
 * @description
 */
public class Dish {
    private final String name;

    private final boolean vegetarian;

    private final int calories;

    private final Type type;

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type{
        META, FISH, OTHER
    }

    public static List<Dish> init() {
         List<Dish> menu = Arrays.asList(
                 new Dish("pork", false, 800, Type.META),
                 new Dish("beef", false, 700, Type.META),
                 new Dish("chicken", false, 400, Type.META),
                 new Dish("french fries", true, 530, Type.OTHER),
                 new Dish("rice", true, 350, Type.OTHER),
                 new Dish("session fruit", true, 120, Type.OTHER),
                 new Dish("pizza", true, 550, Type.OTHER),
                 new Dish("prawns", false, 300, Type.FISH),
                 new Dish("salmon", false, 450, Type.FISH)
        );
        return menu;
    }
}
