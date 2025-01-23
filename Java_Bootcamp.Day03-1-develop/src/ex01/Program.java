import java.util.concurrent.BlockingQueue;

public class Program {
    public static void main(String[] args) {
        try{
            Chicken chicken = new Chicken("Hen");
            Egg egg = new Egg("Egg");
            chicken.start();
            egg.start();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
