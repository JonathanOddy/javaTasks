package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class NumberUtil {

    private static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try  {
            lines = Files.lines(Path.of(fileName)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } return lines;
    }

    public static List<Integer> readNumbers(String filename) {
        return readLines(filename)
                .stream()
                .map(l -> l.replaceAll("[^\\d\\s-]",""))
                .filter(l -> !l.equals(""))
                .map(l -> l.split(" "))
                .flatMap(Arrays::stream)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public static long factorial(int number) {
        long result = LongStream
                .rangeClosed(1, Math.abs(number))
                .reduce(1, (long x, long y) -> x * y);
        return number < 0 && number % 2 != 0 ? -result : result;
    }
}
