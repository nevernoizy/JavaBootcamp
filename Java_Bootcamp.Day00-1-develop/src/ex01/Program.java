import java.util.Scanner;
public class Program{
    public static void main(String[] args) {
        System.out.print("Input a number: ");
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        boolean res = true;
        if(num<3){
            System.err.println("Illegal argument");
            System.exit(-1);
        }
        int count = 1;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                res = false;
                break;
            }
            count++;
        }
        System.out.print(res + " " + count);
    }
}