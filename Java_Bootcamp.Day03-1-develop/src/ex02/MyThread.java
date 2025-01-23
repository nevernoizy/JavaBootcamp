public class MyThread extends Thread{
    int step;
    int[] array;
    int start;
    CommonResource res;
    MyThread(int step, int[] array, int start, CommonResource res){
        this.step = step;
        this.array = array;
        this.start = start;
        this.res = res;
    }
    @Override
    public void run() {
            int sum = 0;
            int startPlusStep = 0;
            for (int i = start; (i < start + step) && i < array.length; i++) {
                sum += array[i];
                startPlusStep = i;
            }
            System.out.println(Thread.currentThread().getName() + ": from " + start + " to " +
                    startPlusStep + " sum is " + sum);
            res.x += sum;
        }
}
