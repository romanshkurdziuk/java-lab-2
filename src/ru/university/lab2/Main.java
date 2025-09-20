package ru.university.lab2;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the first line with the tokens");
        String mainString = scanner.nextLine();
        System.out.println("Enter the second line with the delimiters");
        String delimiters = scanner.nextLine();
        System.out.println("----You Enter----");
        System.out.println("MainString: " + mainString);
        System.out.println("Delimiters: " + delimiters);
        String regex = "[" + delimiters + "]+";
        String[] tokens = mainString.split(regex);
        System.out.println("----Result Of Split----");
        System.out.println(Arrays.toString(tokens));
        ArrayList<Double> doubleNumbers = new ArrayList<>();
        ArrayList<String> stringsOther = new ArrayList<>();
        for (String token : tokens)
        {
            try
            {
                double number = Double.parseDouble(token);
                doubleNumbers.add(number);
            } catch (NumberFormatException e)
            {
                stringsOther.add(token);
            }
        }
        System.out.println("----Find Double Numbers----");
        System.out.println(doubleNumbers);
        System.out.println("----Find String----");
        System.out.println(stringsOther);
        ArrayList<String> timeTokens = new ArrayList<>();
        ArrayList<String> otherTokens = new ArrayList<>();
        String timeRegex = "\\d{2}:\\d{2}";
        SimpleDateFormat sdf = new SimpleDateFormat("mm:HH");
        sdf.setLenient(false);
        for (String token : stringsOther)
        {
            if (token.matches(timeRegex))
            {
                try
                {
                  sdf.parse(token);
                  timeTokens.add(token);
                } catch (ParseException e)
                {
                    otherTokens.add(token);
                }
            }
            else
            {
                otherTokens.add(token);
            }
        }
        System.out.println("----Find Time Tokens (Format MM:HH)----");
        System.out.println(timeTokens);
        System.out.println("----Other Tokens (Not Time Format MM:HH)----");
        System.out.println(otherTokens);

    }
}
