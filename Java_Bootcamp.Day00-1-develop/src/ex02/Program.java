import java.util.Scanner;
public class Program {
    public static void main(String[] args) {
        System.out.println("hello");
        int num = 0;
        int count = 0;
        while(num!=42) {
            Scanner in = new Scanner(System.in);
            num = in.nextInt();
            int number = sumNumbers(num);
            if(isPrime(number)){
                count++;
            }
        }
        System.out.println("Count of coffee-request – " + count);

    }

    static int sumNumbers(int number) {
        int res = 0;
        while (number > 0) {
            res = res + (number % 10);
            number = number / 10;
        }
        return res;
    }

    static boolean isPrime(int number){
        boolean res = true;
        if(number<3){
            res = false;
        } else{
            for (int i = 2; i <= Math.sqrt(number) && res; i++) {
                if (number % i == 0) {
                    res = false;
                }
            }
        }
        return res;
    }
}