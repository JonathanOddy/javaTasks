package lesson_3.task_3_2;


import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Student> studentGroupA = CollectionUtils.newArrayList();
        Student student0 = new Student("Вася");
        CollectionUtils.add(studentGroupA,student0);
        Student student1 = new Student("Коля");
        CollectionUtils.add(studentGroupA,student1);
        Student student2 = new Student("Ира");
        CollectionUtils.add(studentGroupA,student2);
        Student student3 = new Student("Дима");
        CollectionUtils.add(studentGroupA,student3);

        System.out.println("Студенты группы A:");
        studentGroupA.forEach(System.out::println);
        
        List<Employee> studentGroupB = CollectionUtils.newArrayList();
        Employee student4 = new Student("Петр");
        CollectionUtils.add(studentGroupB,student4);
        Employee student5 = new Student("Карина");
        CollectionUtils.add(studentGroupB,student5);
        Employee student6 = new Student("Влад");
        CollectionUtils.add(studentGroupB,student6);

        System.out.println("Студенты группы B:");
        studentGroupB.forEach(System.out::println);

        CollectionUtils.addAll(studentGroupA,studentGroupB);
        System.out.println("Студенты группы A попали в группу B:");
        studentGroupB.forEach(System.out::println);

        System.out.println("Студент '" + student0 + "' в списке под номером " +
                CollectionUtils.indexOf(studentGroupB, student0));

        List<Employee> studentGroupC = CollectionUtils.limit(studentGroupB,2);
        System.out.println("Некоторые студенты перешли в новую группу C:");
        studentGroupC.forEach(System.out::println);

        CollectionUtils.removeAll(studentGroupB, studentGroupC);
        System.out.println("В группе B остались:");
        studentGroupB.forEach(System.out::println);

        System.out.println("Проверяем, все ли студенты группы А перешли в В:");
        System.out.println(CollectionUtils.containsAll(studentGroupB, studentGroupA));

        System.out.println("Проверяем, остался ли кто-то из группы A в B");
        System.out.println(CollectionUtils.containsAny(studentGroupB, studentGroupA));

        System.out.println( "Выведем студентов в группе B c ID от " + student4.getId() + " до " + student5.getId());
        List<Employee> superStudentGroup = CollectionUtils.range(studentGroupB,student4,student5);
        superStudentGroup.forEach(System.out::println);

        System.out.println( "Выведем студентов в группе B c ID от " + student4.getId() + " до " + student5.getId());
        List<Employee> puperStudentGroup = CollectionUtils.range(studentGroupB,student4,student5, Comparator.reverseOrder());
        puperStudentGroup.forEach(System.out::println);

    }
}
