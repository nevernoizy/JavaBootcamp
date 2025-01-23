import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String[] namesArray = new String[10];
        int[] timeArray = new int[10];
        int[] dayArray = new int[10];

        readNames(namesArray);

        readClasses(timeArray, dayArray);


        int[][] dates = new int[13][155];
        fillDates(dates, dayArray, timeArray);
        bubbleSort(dates, 155);

        fillCalendar(dates, namesArray);


        printFirstString(dates);
        printAll(dates, namesArray);

    }

    static void readNames(String[] namesArray){
        String buff = "";
        int count = 0;
        Scanner in = new Scanner(System.in);
        while(!buff.equals(".") && count <10){
            buff = in.next();
            if(!buff.equals(".")) {
                namesArray[count] = buff;
            }
            count++;
        }
    }

    static void readClasses(int[] timeArray, int[] dateArray){
        String buff = "";
        int count = 0;
        char[] arr;
        Scanner in = new Scanner(System.in);
        while( count <10){
            if(in.hasNextInt()){
                timeArray[count] = in.nextInt();
                dateArray[count] = dateToInt(in.next());
                count++;
            } else{
                break;
            }
        }
    }

    static int dateToInt(String str){
        int res = 0;
        switch (str){
            case "MO":res = 1;
            break;
            case "TU":res = 2;
                break;
            case "WE":res = 3;
                break;
            case "TH":res = 4;
                break;
            case "FR":res = 5;
                break;
            case "SA":res = 6;
                break;
            case "SU":res = 7;
                break;
            default:
                break;
        }
        return res;
    }

    static  void fillDates(int[][] dates, int[] datesArray, int[] timeArray){
        int buff = 0;
        int count = 0;
        for(int i = 0; i<10; i++){
            if(datesArray[i]!=0){
                buff = datesArray[i]-1;
                while(buff<32){
                    if(buff >0 && buff < 32){
                        dates[0][count]=buff;
                        dates[1][count] = datesArray[i];
                        dates[2][count] = timeArray[i];
                        count++;
                    }
                    buff=buff+7;
                }
            } else{
                break;
            }
        }
    }

    static void bubbleSort(int[][] arr, int n){
        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){
                if(arr[0][j-1] > arr[0][j] && arr[0][j]!=0){
                    //swap elements
                    int temp = arr[0][j-1];
                    arr[0][j-1] = arr[0][j];
                    arr[0][j] = temp;
                    temp = arr[1][j-1];
                    arr[1][j-1] = arr[1][j];
                    arr[1][j] = temp;
                    temp = arr[2][j-1];
                    arr[2][j-1] = arr[2][j];
                    arr[2][j] = temp;
                }
                if(arr[0][j-1] == arr[0][j] && arr[0][j]!=0 && arr[2][j-1] > arr[2][j]){
                    int temp = arr[0][j-1];
                    arr[0][j-1] = arr[0][j];
                    arr[0][j] = temp;
                    temp = arr[1][j-1];
                    arr[1][j-1] = arr[1][j];
                    arr[1][j] = temp;
                    temp = arr[2][j-1];
                    arr[2][j-1] = arr[2][j];
                    arr[2][j] = temp;
                }

            }
        }
    }

    static void fillCalendar(int[][] dates, String[] names){
        String name;
        int nameID = 1;
        int time;
        int date;
        String mark;
        int markID;
        Scanner in = new Scanner(System.in);
        for(int i = 0; i<10; i++){
            name = in.next();

            if(name.equals(".")){
                break;
            }

            time = in.nextInt();
            date = in.nextInt();
            mark = in.next();

            if(mark.equals("HERE")){
                markID = 1;
            } else {
                markID = -1;
            }

            for(int j = 0; j<10; j++){
                if(name.equals(names[j])){
                    nameID = j+1;
                }
            }
            //System.out.println(nameID + " " + markID);
            for(int k = 0; k<31; k++){
                if(dates[0][k]==date && dates[2][k]==time){
                    dates[2+nameID][k] = markID;
                }
            }
        }
    }

    static void printFirstString(int[][] arr){
        System.out.print("          ");
        int i = 0;
        while(arr[0][i]!=0){
            System.out.print(arr[2][i] + ":00 ");
            System.out.print(dateToStr(arr[1][i]) + " ");
            System.out.printf("%2d|", arr[0][i]);
            i++;
        }
        System.out.println();
    }

    static String dateToStr(int date){
        String res = "";
        switch (date){
            case 1:res = "MO";
                break;
            case 2:res = "TU";
                break;
            case 3:res = "WE";
                break;
            case 4:res = "TH";
                break;
            case 5:res = "FR";
                break;
            case 6:res = "SA";
                break;
            case 7:res = "SU";
                break;
            default:
                break;
        }
        return res;
    }

    static void printAll(int[][] arr, String[] names){
        int i = 0;
        while(names[i]!=null){
            System.out.printf("%10s", names[i]);
            int j = 0;
            while(arr[0][j]!=0){
                if(arr[3+i][j]==0){
                    System.out.print("          |");
                } else if(arr[3+i][j]==1){
                    System.out.print("         1|");
                } else if(arr[3+i][j]==-1){
                    System.out.print("        -1|");
                }
                j++;
            }
            i++;
            System.out.println();
        }
    }
}
