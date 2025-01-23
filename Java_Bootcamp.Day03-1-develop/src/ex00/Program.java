public class Program {
    public static void main(String[] args) {

        try{
            Thread chicken = new Thread(new Chicken());
            Thread egg = new Thread(new Egg());
            chicken.start();
            egg.start();
            chicken.join();
            egg.join();
            for(int i = 0; i < 50; i++){
                System.out.println("Human");
            }
        }
        catch(Exception e){

            System.out.println("error");
        }

    }
}
