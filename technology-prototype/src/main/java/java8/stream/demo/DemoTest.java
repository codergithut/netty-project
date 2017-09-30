package java8.stream.demo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/30
 * @description
 */
public class DemoTest {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transcation> transcations = Arrays.asList(
                new Transcation(brian, 2011, 300),
                new Transcation(raoul, 2012, 1000),
                new Transcation(raoul, 2011, 400),
                new Transcation(mario, 2012, 710),
                new Transcation(mario, 2012, 700),
                new Transcation(alan, 2012, 950)
        );
        List<Transcation> tr2011 = transcations.stream()
                .filter(transcation -> transcation.getYear() == 2011)
                .sorted(Comparator.comparing(Transcation::getValue))
                .collect(toList());

        Set<String> trads = transcations.stream()
                .map(transcation -> transcation.getTrader().getCity())
                .collect(toSet());

        Set<Trader> names = transcations.stream()
                .map(Transcation::getTrader)
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(Trader::getName))
                .collect(toSet());

        String traderString = transcations.stream()
                .map(transcation -> transcation.getTrader().getName())
                .distinct()
                .sorted(Comparator.comparing(String::toString))
                .reduce("", (name1, name2) -> name1 + name2 );

        String traderString1 = transcations.stream()
                .map(transcation -> transcation.getTrader().getName())
                .distinct()
                .sorted(Comparator.comparing(String::toString))
                .collect(joining());

        System.out.println(traderString);
        System.out.println(traderString1);

        boolean milanBased = transcations.stream()
                .anyMatch(transcation -> transcation.getTrader().getCity().equals("Milan"));
        System.out.println(milanBased);

        Integer allMoney = transcations.stream()
                .filter(transcation -> transcation.getTrader().getCity().equals("Cambridge"))
                .map(Transcation::getValue)
                .reduce(0, Integer::sum);
        System.out.println(allMoney);

        transcations.stream()
                .map(transcation -> transcation.getValue())
                .reduce(Integer::max).ifPresent(System.out::println);

        transcations.stream()
                .min(Comparator.comparing(Transcation::getValue))
                .ifPresent(transcation -> System.out.println(transcation.getTrader().getName()));


    }
}
