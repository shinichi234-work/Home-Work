import java.util.concurrent.Semaphore;

class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final Semaphore waiter = new Semaphore(NUM_PHILOSOPHERS - 1); // Официант ограничивает до 4 вилок
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    static class Philosopher implements Runnable {
        private final int id;
        private final Semaphore leftFork;
        private final Semaphore rightFork;

        public Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking.");
            Thread.sleep((int) (Math.random() * 1000)); // Размышление
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating.");
            Thread.sleep((int) (Math.random() * 1000)); // Еда
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();
                    // Запрашиваем разрешение у официанта
                    waiter.acquire();
                    System.out.println("Philosopher " + id + " got waiter's permission.");
                    // Пытаемся взять левую вилку
                    leftFork.acquire();
                    System.out.println("Philosopher " + id + " picked up left fork.");
                    // Пытаемся взять правую вилку
                    rightFork.acquire();
                    System.out.println("Philosopher " + id + " picked up right fork.");
                    // Едим
                    eat();
                    // Возвращаем вилки
                    leftFork.release();
                    System.out.println("Philosopher " + id + " put down left fork.");
                    rightFork.release();
                    System.out.println("Philosopher " + id + " put down right fork.");
                    // Освобождаем официанта
                    waiter.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        // Инициализация вилок
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1); // Каждая вилка — семафор с 1 разрешением
        }

        // Создание и запуск философов
        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Thread(new Philosopher(i, forks[i], forks[(i + 1) % NUM_PHILOSOPHERS]));
            philosophers[i].start();
        }
    }
}