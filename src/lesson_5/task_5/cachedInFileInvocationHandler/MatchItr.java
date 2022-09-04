package lesson_5.task_5.cachedInFileInvocationHandler;

import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.regex.Matcher;

final class MatchItr extends Spliterators.AbstractSpliterator<String> {

    private final Matcher matcher;
    MatchItr(Matcher matcher) {
        super(matcher.regionEnd()-matcher.regionStart(), ORDERED|NONNULL);
        this.matcher = matcher;
    }
    public boolean tryAdvance(Consumer<? super String> action) {
        if(!matcher.find()) return false;
        action.accept(matcher.group());
        return true;
    }
}
