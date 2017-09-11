package thread;

/**
 * Created by tianjian on 2017/9/10.
 */
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThreader extends Thread {
        public void run() {
            while (!ready) {
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThreader().start();
        new ReaderThreader().start();
        new ReaderThreader().start();
        new ReaderThreader().start();
        new ReaderThreader().start();
        number = 42;
        ready = true;
    }
}
