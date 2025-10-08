package advanced_java.Chap12.Page381.test;

import java.util.*;
import java.util.stream.*;

public class CoffeeOrder {
    public static void main(String[] args) {
        List<String> coffees = List.of(
                "Cappuccino", "Americano", "Espresso",
                "Cortado", "Mocha", "Cappuccino",
                "Flat White", "Latte"
        );

        List<String> coffeesEndingInO = coffees.stream()
                .filter(s -> s.endsWith("o"))   // only names ending with "o"
                .distinct()                     // remove duplicates
                .sorted()                       // sort alphabetically
                .collect(Collectors.toList());  // collect to List

        System.out.println(coffeesEndingInO);
    }
}
