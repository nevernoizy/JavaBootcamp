public class Chicken extends Thread {

    Chicken(String name){
        super(name);
    }
    public void run(){
        for(int i = 0; i < 50; i++){
            try{
                ProducerConsumer.printThreadName(Thread.currentThread().getName());
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}