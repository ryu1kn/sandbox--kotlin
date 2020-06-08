import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

abstract public class JavaApp {
    private final List<String> printed = new ArrayList<>();

    protected synchronized void println(String msg) { printed.add(msg); }
    protected synchronized void println(int n) { println(String.valueOf(n)); }

    abstract public void run() throws InterruptedException;

    public String getResult() {
        return String.join("\n", printed);
    }

    protected void stop(ExecutorService executor) {
        try {
            executor.shutdown();
            executor.awaitTermination(60, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("termination interrupted");
        }
        finally {
            if (!executor.isTerminated()) {
                System.err.println("killing non-finished tasks");
            }
            executor.shutdownNow();
        }
    }

    protected void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
