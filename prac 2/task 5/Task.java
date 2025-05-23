public interface Task {
    void start();
    void stop();
}

public class CountdownTimer implements Task {
    private int seconds;
    private boolean isRunning;
    private Thread timerThread;

    public CountdownTimer(int seconds) {
        this.seconds = seconds;
        this.isRunning = false;
    }

    @Override
    public void start() {
        if (isRunning) {
            System.out.println("Таймер уже запущен");
            return;
        }

        isRunning = true;
        timerThread = new Thread(() -> {
            try {
                while (seconds > 0 && isRunning) {
                    System.out.println("Осталось: " + seconds + " сек.");
                    Thread.sleep(1000);
                    seconds--;
                }
                if (seconds == 0) {
                    System.out.println("Таймер завершен!");
                }
            } catch (InterruptedException e) {
                System.out.println("Таймер прерван");
            }
        });
        timerThread.start();
    }

    @Override
    public void stop() {
        isRunning = false;
        if (timerThread != null) {
            timerThread.interrupt();
        }
        System.out.println("Таймер остановлен");
    }

    public static void main(String[] args) {
        CountdownTimer timer = new CountdownTimer(5);
        timer.start();

        // Для демонстрации остановки по таймауту
        try {
            Thread.sleep(3500);
            timer.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}