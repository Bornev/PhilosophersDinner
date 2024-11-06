public class PhilosopherTable {
    private final int numberOfPhilosophers;
    private final Thread[] philosophers;
    private final Fork[] forks;

    public PhilosopherTable(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.philosophers = new Thread[numberOfPhilosophers];
        this.forks = new Fork[numberOfPhilosophers];
        initializeForks();
        initializePhilosophers();
    }

    private void initializeForks() {
        for (int i = 0; i < numberOfPhilosophers; i++) {
            forks[i] = new Fork(i);
        }
    }

    private void initializePhilosophers() {
        for (int i = 0; i < numberOfPhilosophers; i++) {
            Fork leftFork = forks[i];
            Fork rightFork = forks[(i + 1) % numberOfPhilosophers];
            
            // Последний философ берет вилки в другом порядке
            if (i == numberOfPhilosophers - 1) {
                philosophers[i] = new Thread(new Philosopher(i, rightFork, leftFork));
            } else {
                philosophers[i] = new Thread(new Philosopher(i, leftFork, rightFork));
            }
        }
    }

    public void startDinner() {
        System.out.println("Начало обеда...");
        for (Thread philosopher : philosophers) {
            philosopher.start();
        }
    }

    public void waitForDinnerToFinish() {
        try {
            for (Thread philosopher : philosophers) {
                philosopher.join();
            }
            System.out.println("Все философы закончили трапезу!");
        } catch (InterruptedException e) {
            System.err.println("Обед был прерван!");
            Thread.currentThread().interrupt();
        }
    }
}