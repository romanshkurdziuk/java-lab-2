package ru.university.lab2;

// Импортируем все необходимые классы
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        // --- Шаг 1: Чтение ввода от пользователя ---
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первую строку с лексемами:");
        String mainString = scanner.nextLine();
        System.out.println("Введите вторую строку с символами-разделителями:");
        String delimiters = scanner.nextLine();

        System.out.println("\n--- Вы ввели ---");
        System.out.println("Основная строка: " + mainString);
        System.out.println("Разделители: " + delimiters);

        // --- Шаг 2: Разбиение строки на лексемы ---
        String regex = "[" + delimiters + "]+";
        String[] tokens = mainString.split(regex);
        System.out.println("\n--- Результат разбиения на лексемы ---");
        System.out.println(Arrays.toString(tokens));

        // --- Шаг 3: Поиск вещественных чисел ---
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

        // --- Шаг 4: Поиск времени среди остальных лексем (формат ММ:ЧЧ) ---
        List<String> timeTokens = new ArrayList<>();
        List<String> remainingTokens = new ArrayList<>();
        // Улучшенное регулярное выражение для времени (1 или 2 цифры)
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

        // --- Шаг 5: Модификация строки (ИСПРАВЛЕННАЯ ЛОГИКА) ---

        // --- 5А: Анализируем ОРИГИНАЛЬНУЮ строку, чтобы решить, что делать ---

        // 1. Находим самую короткую подстроку для удаления из ОРИГИНАЛЬНОЙ mainString
        String digitSubstringRegex = "\\d\\S*";
        Pattern pattern = Pattern.compile(digitSubstringRegex);
        Matcher matcher = pattern.matcher(mainString);

        String shortestSubstringToDelete = null;

        while (matcher.find()) {
            String found = matcher.group();
            if (shortestSubstringToDelete == null || found.length() < shortestSubstringToDelete.length()) {
                shortestSubstringToDelete = found;
            }
        }

        // 2. Находим индекс для вставки случайной лексемы в ОРИГИНАЛЬНОЙ mainString
        int insertIndex;
        if (!timeTokens.isEmpty()) {
            String lastTimeToken = timeTokens.get(timeTokens.size() - 1);
            int lastTimeIndex = mainString.lastIndexOf(lastTimeToken);
            insertIndex = lastTimeIndex + lastTimeToken.length();
        } else {
            insertIndex = mainString.length();
        }

        // --- 5Б: Теперь создаем StringBuilder и ПРИМЕНЯЕМ все изменения ---

        StringBuilder finalString = new StringBuilder(mainString);
        System.out.println("\n--- Начальная строка для модификации ---");
        System.out.println(finalString);

        // Действие 1: Вставляем случайную лексему
        Random random = new Random();
        String randomToken = "RND" + random.nextInt(100);
        finalString.insert(insertIndex, " " + randomToken);

        System.out.println("\n--- Строка после добавления случайной лексемы ---");
        System.out.println(finalString);

        // Действие 2: Удаляем подстроку, которую мы нашли РАНЕЕ в mainString
        if (shortestSubstringToDelete != null) {
            // Ищем ее индекс уже в ИЗМЕНЕННОЙ строке
            int startIndex = finalString.indexOf(shortestSubstringToDelete);
            if (startIndex != -1) {
                finalString.delete(startIndex, startIndex + shortestSubstringToDelete.length());
                System.out.println("\n--- Удалена самая короткая подстрока, найденная в оригинале: '" + shortestSubstringToDelete + "' ---");
            }
        } else {
            System.out.println("\n--- Подстрок, начинающихся с цифры, не найдено ---");
        }

        System.out.println("\n--- Итоговая строка ---");
        System.out.println(finalString);


        // --- Шаг 6: Сортировка лексем времени ---
        if (!timeTokens.isEmpty()) {
            String[] timeArray = timeTokens.toArray(new String[0]);

            Comparator<String> timeComparator = (time1, time2) -> {
                String[] parts1 = time1.split(":");
                String[] parts2 = time2.split(":");
                int hours1 = Integer.parseInt(parts1[1]);
                int hours2 = Integer.parseInt(parts2[1]);
                int hourComparison = Integer.compare(hours1, hours2);
                if (hourComparison != 0) {
                    return hourComparison;
                } else {
                    int minutes1 = Integer.parseInt(parts1[0]);
                    int minutes2 = Integer.parseInt(parts2[0]);
                    return Integer.compare(minutes1, minutes2);
                }
            };

            Arrays.sort(timeArray, timeComparator);

            System.out.println("\n--- Отсортированный список времени (сначала по часам, потом по минутам) ---");
            System.out.println(Arrays.toString(timeArray));

        } else {
            System.out.println("\n--- Лексемы времени для сортировки отсутствуют ---");
        }

        scanner.close(); // Закрываем Scanner в самом конце
    }
}