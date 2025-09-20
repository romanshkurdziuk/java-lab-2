package ru.university.lab2;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

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
    }
}
