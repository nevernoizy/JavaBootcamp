import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int weekCount = 1;
        int weekNumber = 1;
        long numLong = 0;
        while(weekNumber<18) {
            weekNumber = readWeeks((weekCount));
            if(weekNumber<18) {
                int min = readGrades();
                numLong = numLong + min*((long)Math.pow(10,(weekNumber-1)));
                //System.out.println(weekNumber + " " + min);
                weekCount++;
            }
        }
        //System.out.println(numLong);
        printResult(numLong);
    }

    static int readGrades(){
        int min = 9;
        int num = 0;
        Scanner in = new Scanner(System.in);
        for(int i = 0; i<5; i++) {
            num = in.nextInt();
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

    static int readWeeks(int weekCount){
        Scanner in = new Scanner(System.in);
        String str = in.next();
        int num = 0;
        if(str.equals("42")){
            num = 99;
        } else {
            num = in.nextInt();
            if (weekCount != num) {
                System.err.println("Illegal argument");
                System.exit(-1);
            }
        }
        return num;
    }

    static void printResult(long num){
        int count = 1;
        long lastNum = 0;
        while(num>0){
            lastNum = num%10;
            System.out.print("Week " + count + " ");
            for(int i = 0; i<lastNum; i++){
                System.out.print("=");
            }
            System.out.print(">\n");
            count++;
            num=num/10;
        }
    }
}
