package ru.otus.java.pro.homework.hm2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainApp2 {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

        List<Employee> employees = Arrays.asList(
                new Employee("Андрей", 32, "Инженер"),
                new Employee("Настя", 29, "Начальник отдела"),
                new Employee("Федор", 32, "Инженер"),
                new Employee("Алиса", 36, "Инженер")
        );
        List<String> words = Arrays.asList("Компьютер", "Клавиатура", "Мышка", "Монитор");

        String wordsOneLine = "Компьютер Компьютер Компьютер Клавиатура Мышка Мышка Монитор";

        String[] phrases = {"Компьютер Мышка Монитор", "Колонка адаптер", "Клавиатура Принтер Программист"};

        int thirdMax = numbers.stream()
                .sorted((a, b) -> b - a)
                .skip(2)
                .findFirst()
                .orElseThrow();
        System.out.println(thirdMax);

        int thirdUniqueMax = numbers.stream()
                .distinct()
                .sorted((a, b) -> b - a)
                .skip(2)
                .findFirst()
                .orElseThrow();
        System.out.println(thirdUniqueMax);

        List<String> oldestEngineers = employees.stream()
                .filter(e -> "Инженер".equals(e.position))
                .sorted((a, b) -> b.age - a.age)
                .limit(3)
                .map(e -> e.name)
                .collect(Collectors.toList());
        System.out.println(oldestEngineers);

        double averageAge = employees.stream()
                .filter(e -> "Инженер".equals(e.position))
                .mapToInt(e -> e.age)
                .average()
                .orElse(0);
        System.out.println(averageAge);


        String longestWord = words.stream()
                .reduce((a, b) -> a.length() >= b.length() ? a : b)
                .orElse("");
        System.out.println(longestWord);

        Map<String, Long> wordsCount = Arrays.stream(wordsOneLine.split(" "))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        System.out.println(wordsCount);

        words.stream()
                .sorted((a, b) -> a.length() == b.length() ? a.compareTo(b) : Integer.compare(a.length(), b.length()))
                .forEach(System.out::println);

        String longestWordInPhrase = Arrays.stream(phrases)
                .flatMap(phrase -> Arrays.stream(phrase.split(" ")))
                .reduce((a, b) -> a.length() >= b.length() ? a : b)
                .orElse("");
        System.out.println(longestWordInPhrase);

    }
}
