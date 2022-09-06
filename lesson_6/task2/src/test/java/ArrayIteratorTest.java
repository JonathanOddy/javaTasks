import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

class ArrayIteratorTest {

    @ParameterizedTest
    @MethodSource("stringArrayProvider")
    void testIterator(String[] strings) {

        ArrayIterator<String> arrayIterator = new ArrayIterator<>(strings);

        if (strings.length != 0) {
            for (String expected : strings) {
                assertEquals(expected, arrayIterator.next());
            }
        }
        assertFalse(arrayIterator.hasNext());
        assertThrows(ArrayIndexOutOfBoundsException.class, arrayIterator::next);
    }

   private static Stream<Arguments> stringArrayProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{}),
                Arguments.of((Object) new String[]{"0"}),
                Arguments.of((Object) new String[]{"0", "1"}),
                Arguments.of((Object) new String[]{null})
        );
    }
}