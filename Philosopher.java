public class Philosopher implements Runnable {
    private final int id;
    private final Fork leftFork;
    private final Fork rightFork;
    private int mealsEaten;
    private static final int REQUIRED_MEALS = 3;
    private static final int EATING_TIME = 1000;
    private static final int THINKING_TIME = 1000;

    public Philosopher(int id, Fork leftFork, Fork rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.mealsEaten = 0;
    }

    private void eat() throws InterruptedException {
        while (true) {
            // Пытаемся взять левую вилку
            if (leftFork.pickUp()) {
                System.out.println("Философ " + id + " взял левую вилку " + leftFork.getId());
                
                // Пытаемся взять правую вилку
                if (rightFork.pickUp()) {
                    System.out.println("Философ " + id + " взял правую вилку " + rightFork.getId());
                    
                    // Едим
                    System.out.println("Философ " + id + " начал есть (прием пищи #" + (mealsEaten + 1) + ")");
                    Thread.sleep(EATING_TIME);
                    mealsEaten++;
                    System.out.println("Философ " + id + " закончил есть");
                    
                    // Кладем вилки
                    rightFork.putDown();
                    leftFork.putDown();
                    System.out.println("Философ " + id + " положил обе вилки");
                    return;
                } else {
                    // Если не удалось взять правую вилку, кладем левую
                    leftFork.putDown();
                    Thread.sleep(100);
                }
            }
            Thread.sleep(100);
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Философ " + id + " размышляет");
        Thread.sleep(THINKING_TIME);
    }

    @Override
    public void run() {
        try {
            while (mealsEaten < REQUIRED_MEALS) {
                think();
                eat();
            }
            System.out.println("Философ " + id + " закончил трапезу (поел " + REQUIRED_MEALS + " раза)");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}