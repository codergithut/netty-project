package java8.stream.demo;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/30
 * @description
 */
public class Transcation {
    private final Trader trader;
    private final int year;
    private final int value;

    public Transcation(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public int getYear() {
        return year;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "{" + this.trader + ", year: " + this.year + ", value: " + this.value + "}";
    }
}
