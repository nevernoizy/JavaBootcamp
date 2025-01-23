public class Program {
    public static void main(String[] args) {
        int i = 479598;
        int res = 0;
        while(i>0){
            res = res + (i%10);
            i = i/10;
        }
        System.out.print(res);
        }
    }