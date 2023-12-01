import java.util.List;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public interface FizzBuzzBranchless {

    static void main(String... args) {
        IntStream.rangeClosed(1, 100)
                .mapToObj(processorFactory())
                .forEach(System.out::println);
    }

    static Processor processorFactory() {
        return new Processor(
                new Mapper(divisibleBy(15), n -> "FizzBuzz"),
                new Mapper(divisibleBy(3), n -> "Fizz"),
                new Mapper(divisibleBy(5), n -> "Buzz"),
                new Mapper(n -> true, Integer::toString)
        );
    }

    class Processor implements IntFunction<String> {
        private final List<Mapper> mappers;

        public Processor(Mapper... mappers) {
            this.mappers = List.of(mappers);
        }

        @Override
        public String apply(int num) {
            return mappers.stream()
                    .filter(bm -> bm.filter().test(num))
                    .map(bm -> bm.formatter().apply(num))
                    .findFirst()
                    .orElseThrow();
        }
    }

    record Mapper(
            IntPredicate filter,
            IntFunction<String> formatter) {}

    static IntPredicate divisibleBy(int divisor) {
        return n -> n % divisor == 0;
    }

}
