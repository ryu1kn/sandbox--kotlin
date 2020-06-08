import java.util.ArrayList;
import java.util.List;

public class CaseRunnable {
    private final List<String> printed = new ArrayList<>();

    public void run() throws InterruptedException {
        Runnable task = () -> {
            String threadName = Thread.currentThread().getName();
            println("Hello " + threadName);
        };

        task.run();

        Thread thread = new Thread(task);
        thread.start();

        thread.join();
        println("Done!");
    }

    private synchronized void println(String msg) {
        printed.add(msg);
    }

    public String getResult() {
        return String.join("\n", printed);
    }
}
