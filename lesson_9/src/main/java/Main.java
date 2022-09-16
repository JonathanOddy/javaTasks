
import java.util.List;
import java.util.Map;


public class Main {


    public static void main(String[] args) {

        List<String> list = List.of(new String[]{"ab", "a", "abc", "ab"});

        Map<String, Integer> stringIntegerMap = Streams.of(list).filter(s -> s.length() > -11)
                .transform(s -> s + "NEW")
                .toMap(s -> s, String::length);

        System.out.println(stringIntegerMap);

    }
}
