package task_2_2;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        String fileName = System.getProperty("user.dir") + "/src/resources/collections.txt";

        System.out.println("Задание 1: Подсчитать количество различных слов в файле");
        System.out.println(MyFileReader.countDistinctWords(fileName));

        System.out.println("\nЗадание 2: Вывести на экран список различных слов файла, отсортированный" +
                " по возрастанию их длины, затем по алфавиту" );
        System.out.println(MyFileReader.getSortedDistinctWords(fileName));

        System.out.println("\nЗадание 3: Подсчитать и вывести на экран, сколько раз каждое слово встречается в файле ");
        System.out.println(MyFileReader.getCountOfEachWord(fileName));

        System.out.println("\nЗадание 4: Вывести на экран все строки файла в обратном порядке");
        MyFileReader.getLinesInReverseOrder(fileName).forEach(System.out::println);

        System.out.println("\nЗадание 5: Реализовать свой Iterator для обхода списка в обратном порядке");
        List<String> lines = MyFileReader.getLines(fileName);
        for (String line : new ReverseIterator<String>(lines)) {
            System.out.println(line);
        }

        System.out.println("\nЗадание 6: Вывести на экран строки, номера которых задаются пользователем " +
                "в произвольном порядке.");
        MyFileReader.printLineByNumber(fileName);

    }
}
