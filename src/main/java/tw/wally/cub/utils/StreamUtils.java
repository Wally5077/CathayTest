package tw.wally.cub.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author - wally55077@gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    public static <T, R> List<R> mapToList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        return collection.stream().map(mapper).collect(toList());
    }

    public static <T, R> List<R> mapToList(T[] ts, Function<? super T, ? extends R> mapper) {
        return Stream.of(ts).map(mapper).collect(toList());
    }

    public static <T, K, U> Map<K, U> toMap(Collection<T> collection,
                                            Function<? super T, ? extends K> keyMapper,
                                            Function<T, U> valueMapper) {
        return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper, (existing, replacement) -> existing, LinkedHashMap::new));
    }

    public static <T> Optional<T> findFirst(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst();
    }
}
