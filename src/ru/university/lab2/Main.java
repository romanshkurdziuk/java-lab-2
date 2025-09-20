package ru.university.lab2;

// Добавляем импорт для List - это более общий тип, чем ArrayList
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первую строку с лексемами:");
        String mainString = scanner.nextLine();
        System.out.println("Введите вторую строку с символами-разделителями:");
        String delimiters = scanner.nextLine();

        System.out.println("\n--- Вы ввели ---");
        System.out.println("Основная строка: " + mainString);
        System.out.println("Разделители: " + delimiters);
        String regex = "[" + delimiters + "]+";
        String[] tokens = mainString.split(regex);
        System.out.println("\n--- Результат разбиения на лексемы ---");
        System.out.println(Arrays.toString(tokens));
        List<Double> numbers = new ArrayList<>();
        List<String> otherTokens = new ArrayList<>();
        for (String token : tokens) {
            try {
                double number = Double.parseDouble(token);
                numbers.add(number);
            } catch (NumberFormatException e) {
                otherTokens.add(token);
            }
        }
        System.out.println("\n--- Найденные вещественные числа ---");
        System.out.println(numbers);
        System.out.println("\n--- Остальные лексемы (для дальнейшего анализа) ---");
        System.out.println(otherTokens);
        List<String> timeTokens = new ArrayList<>();
        List<String> remainingTokens = new ArrayList<>();
        String timeRegex = "\\d{1,2}:\\d{1,2}";
        SimpleDateFormat timeValidator = new SimpleDateFormat("mm:HH");
        timeValidator.setLenient(false);
        for (String token : otherTokens) {
            if (token.matches(timeRegex)) {
                try {
                    timeValidator.parse(token);
                    timeTokens.add(token);
                } catch (ParseException e) {
                    remainingTokens.add(token);
                }
            } else {
                remainingTokens.add(token);
            }
        }
        System.out.println("\n--- Найденные лексемы времени (ММ:ЧЧ) ---");
        System.out.println(timeTokens);
        System.out.println("\n--- Оставшиеся лексемы (не числа и не время) ---");
        System.out.println(remainingTokens);
        StringBuilder modifiedString = new StringBuilder(mainString);
        System.out.println("\n--- Начальная строка для модификации ---");
        System.out.println(modifiedString);

        Random random = new Random();
        String randomToken = "RND" + random.nextInt(100);

        int insertIndex;
        if (!timeTokens.isEmpty()) {
            String lastTimeToken = timeTokens.get(timeTokens.size() - 1);
            int lastTimeIndex = mainString.lastIndexOf(lastTimeToken);
            insertIndex = lastTimeIndex + lastTimeToken.length();
        } else {
            insertIndex = modifiedString.length();
        }
        modifiedString.insert(insertIndex, " " + randomToken);

        System.out.println("\n--- Строка после добавления случайной лексемы ---");
        System.out.println(modifiedString);
        String digitSubstringRegex = "\\d\\S*";
        Pattern pattern = Pattern.compile(digitSubstringRegex);
        Matcher matcher = pattern.matcher(modifiedString);
        String shortestSubstring = null;
        int shortestLength = Integer.MAX_VALUE;
        while (matcher.find()) {
            String found = matcher.group();
            if (found.length() < shortestLength) {
                shortestLength = found.length();
                shortestSubstring = found;
            }
        }
        if (shortestSubstring != null) {
            int startIndex = modifiedString.indexOf(shortestSubstring);
            if (startIndex != -1)
            {
                modifiedString.delete(startIndex, startIndex + shortestLength);
                System.out.println("\n--- Найдена и удалена самая короткая подстрока, начинающаяся с цифры ---");
                System.out.println("Удаленная подстрока: '" + shortestSubstring + "'");
            }
        } else {
            System.out.println("\n--- Подстрок, начинающихся с цифры, не найдено ---");
        }

        System.out.println("\n--- Итоговая строка ---");
        System.out.println(modifiedString);
    }
}