import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class CaseRaceCondition extends JavaApp {
    private int count = 0;

    void increment() {
        count++;
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        IntStream.range(0, 100000)
            .forEach(i -> executor.submit(this::increment));

        stop(executor);

        println(count);
    }
}
