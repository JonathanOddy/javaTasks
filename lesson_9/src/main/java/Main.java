
import java.util.List;
import java.util.Map;


public class Main {


    public static void main(String[] args) {

        List<Person> personList = List.of(
                new Person("Pavel", 30),
                new Person("Andrew", 25),
                new Person("Alice", 20),
                new Person("Sancho", 18),
                new Person( "Daniel", 22),
                new Person("Kristen", 28)
                );

        Map<String, Person> personMap = Streams
                .of(personList)
                .filter(p -> p.getAge() > 20)
                .transform(p -> new Person(p.getName(), p.getAge() + 30))
                .toMap(p -> p.getName(), p -> p);

        System.out.println(personMap);

    }
}
