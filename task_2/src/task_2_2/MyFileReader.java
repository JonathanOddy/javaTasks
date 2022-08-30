package task_2_2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

public class MyFileReader {

    public static List<String> getLines(String fileName) {

        List<String> lines = new ArrayList<>();

        try (Stream<String> streamOfLines = Files.lines(Path.of(fileName)) ) {
            lines = streamOfLines.collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String> getWords(String fileName) {

        return getLines(fileName)
                .stream()
                .map(s -> s.replaceAll("[\\p{Punct}]", ""))
                .filter(s -> !s.equals(""))
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    public static List<String> getDistinctWords(String fileName) {

        return getLines(fileName)
                .stream()
                .map(s -> s.replaceAll("[\\p{Punct}]", ""))
                .filter(s -> !s.equals(""))
                .map(s -> s.split(" "))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public static int countDistinctWords(String fileName) {

        return getDistinctWords(fileName).size();

    }

    public static List<String> getSortedDistinctWords(String filename) {

        List<String> distinctWords = getDistinctWords(filename);

        distinctWords
                .sort(Comparator
                        .comparing(String::length)
                        .thenComparing(s -> s.toLowerCase())
                );

        return distinctWords;
    }

    public static Map<String, Integer> getCountOfEachWord(String filename) {

        return getWords(filename)
                .stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.summingInt(s -> 1)
                ));
    }

    public static List<String> getLinesInReverseOrder(String fileName) {

        List<String> lines = getLines(fileName);
        lines.sort(Collections.reverseOrder());

        return lines;
    }

    public static void printLineByNumber(String filename) {

        Scanner scanner = new Scanner(System.in);
        List<String> lines = getLines(filename);

        while (true) {
            int index = scanner.nextInt();
            if (index > 0 && index < lines.size()) {
                System.out.println(getLines(filename).get(index));
            }
            else {
                break;
            }
        }
    }

}
