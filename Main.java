public class Main {
    public static void main(String[] args) {
        PhilosopherTable table = new PhilosopherTable(5);
        table.startDinner();
        table.waitForDinnerToFinish();
    }
}