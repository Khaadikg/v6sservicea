package main.java;

import java.io.*;
import java.util.*;
import static main.java.Scribe.*;

public class TextSorter {

    /**
     * Программа предназначена для построчной сортировки текста в трех вариантах:
     * 1. в алфавитном порядке
     * 2. по количеству символов в строке
     * 3. по определенному слову в строке, которое задается в качестве параметра
     *
     *  ВАЖНО!
     * - Порядковый номер обозначает какой из вариантов сортировки будет использован
     * - Можно задавать числа от 1 до 3 включительно
     * */
    private static String INPUT_FILE = "src/main/resources/src.txt";
    private static String OUT_FILE = "src/main/resources/output.txt";
    public static void main(String[] args) {
        appStarter();
    }

    public static void appStarter() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                1. По алфавиту.
                2. По количеству символов в строке.
                3. По слову в строке заданному аргументом программы в виде
                порядкового номера.""");
        int criteria = scanner.nextInt();
        scanner.nextLine();

        String optional = "";
        if (criteria == 3) {
            System.out.println("Введите порядковый номер слова:");
            optional = scanner.nextLine();
        }

        try {
            List<String> lines = readLinesFromFile(INPUT_FILE);
            List<String> sortedLines = sortLines(lines, criteria, optional);
            writeLinesToFile(sortedLines, OUT_FILE);
            System.out.println("Успех! Данные написаны в: " + OUT_FILE);
        } catch (IOException e) {
            System.out.println("Возникла ошибка: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static List<String> sortLines(List<String> lines, int sortingCriteria, String optional) {
        List<String> sortedLines = new ArrayList<>(lines);
        switch (sortingCriteria) {
            case 1 -> Collections.sort(sortedLines);
            case 2 -> sortedLines.sort(Comparator.comparingInt(String::length));
            case 3 -> sortedLines.sort(Comparator.comparingInt(line -> countOccurrences(line, optional)));
            default -> System.out.println("Неправильно введен номер!");
        }
        return sortedLines;
    }

    private static int countOccurrences(String line, String word) {
        String[] words = line.split("\\s+");
        int count = 0;
        for (String w : words) {
            if (w.equals(word)) {
                count++;
            }
        }
        return count;
    }
}
