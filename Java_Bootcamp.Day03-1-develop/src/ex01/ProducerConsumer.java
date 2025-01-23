import java.util.LinkedList;
import java.util.List;

public class ProducerConsumer<T> {
    private static String threadName = "Hen";
    private static final int BUFFER_MAX_SIZE = 1;
    private List<T> buffer = new LinkedList<>();

    synchronized void produce(T value) throws Exception {
        while (buffer.size() == BUFFER_MAX_SIZE){
            wait();
        }
        buffer.add(value);
        notify();
    }

    synchronized T consume() throws Exception{
        while(buffer.isEmpty()){
            wait();
        }
        T result = buffer.remove(0);
        notify();
        return result;
    }
    public static void printThreadName(String name) throws InterruptedException {
        synchronized (ProducerConsumer.class) {
            if (threadName.equals(name)) {
                ProducerConsumer.class.wait();
            } else {
                System.out.println(name);
                threadName = name;
                ProducerConsumer.class.notify();
            }
        }
    }
}
