import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    private final int id;
    private final ReentrantLock lock = new ReentrantLock();

    public Fork(int id) {
        this.id = id;
    }

    public boolean pickUp() {
        return lock.tryLock();
    }

    public void putDown() {
        lock.unlock();
    }

    public int getId() {
        return id;
    }
}