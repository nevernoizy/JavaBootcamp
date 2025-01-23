import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Stack;

public class Program {
    public static void main(String[] args) {
        int threadsCount = Integer.parseInt(args[0]);
        Stack<String> stack = new Stack<>();
        try{
            Reader.readFile(stack);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        MyThread[] threadsArray = new MyThread[threadsCount];
        for(int i = 0; i < threadsCount; i++){
            threadsArray[i] = new MyThread(stack);
            threadsArray[i].start();
        }
    }

}
