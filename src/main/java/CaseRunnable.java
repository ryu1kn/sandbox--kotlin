public class CaseRunnable extends JavaApp {
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
}
