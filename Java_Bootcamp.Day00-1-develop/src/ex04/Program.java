import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[] arr;
        int[] counts = new int[65535];
        String str = in.nextLine();
        arr = str.toCharArray();

        countsFill(arr, counts, str.length());

        int[] maxIndexes = new int[10];
        int[] maxCounts = new int[10];

        maxIndexesFill(counts, maxIndexes, maxCounts);

        printFunc(maxIndexes, maxCounts);
    }

    static void countsFill(char[] arr, int[] counts, int length){
        for(int i = 0; i<length; i++){
            counts[arr[i]]++;
        }
    }

    static void maxIndexesFill(int[] counts, int[] maxIndexes, int[] maxCounts){
        for(int i = 0; i < 65535; i++){
          if(counts[i]>maxCounts[0]){
              maxCounts[0]=counts[i];
              maxIndexes[0]=i;
              for(int j = 0; j<9; j++){
                  if(maxCounts[j]>maxCounts[j+1]){
                      //swap(maxCounts[j],maxCounts[j+1]);
                      int buff = maxCounts[j];
                      maxCounts[j] = maxCounts[j+1];
                      maxCounts[j+1] = buff;
                      //swap(maxIndexes[j],maxIndexes[j+1]);
                      buff = maxIndexes[j];
                      maxIndexes[j] = maxIndexes[j+1];
                      maxIndexes[j+1] = buff;
                  }
              }
          }
        }
    }

    static void printFunc(int[] maxIndexes, int[] maxCounts){
        int max = maxCounts[9];
        int[] procents = new int[10];
        for(int i = 0; i<10; i++){
            procents[i] = (int)(((double)maxCounts[i]/max)*10);
        }

        int[][] printArray = new int[10][11];
        for(int i = 0; i<10; i++){
            for(int j = 0; j<11; j++){
                if(j<procents[i]){
                    printArray[i][j]=-1;
                } else{
                    printArray[i][j]=maxCounts[i];
                    break;
                }
            }
        }
        for(int i = 10; i>-1; i--){
            for(int j = 9; j>-1; j--){
                if(printArray[j][i]==-1){
                    System.out.print(" # ");
                } else if(printArray[j][i]!=0) {
                    System.out.printf("%2d ", printArray[j][i]);
                }
            }
            System.out.print("\n");
        }
        for(int i = 9; i>-1; i--){
            System.out.printf(" %c ", maxIndexes[i]);
        }


    }



}
