import java.util.Random;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Program {
    public static void main(String[] args) {
        System.out.println(args[0] + " " + args[1]);
        int sizeArray = parseInt(args[0]);
        int[] array = new int[sizeArray];
        Random r = new Random();
        int sum = 0;
        for(int i = 0; i < sizeArray; i++){
            array[i] = r.nextInt(1000);
            sum+=array[i];
        }
        System.out.println("Sum: " + sum);

        int start =0;
        int step = (int) Math.ceil(parseDouble(args[0])/parseDouble(args[1]));
        CommonResource commonResource = new CommonResource();
        MyThread[] threads = new MyThread[parseInt(args[1])];
        for(int i = 0; i < parseInt(args[1]); i++){
            threads[i] = new MyThread(step, array, start, commonResource);
            start += step;
        }
        for(MyThread thr : threads){
            thr.start();
            try {
                thr.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Sum by threads= " + commonResource.x);
    }
}
