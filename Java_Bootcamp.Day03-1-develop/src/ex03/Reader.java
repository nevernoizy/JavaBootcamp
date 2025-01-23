import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Reader {
    public static void readFile(Stack<String> stack) throws FileNotFoundException {
        Scanner in = new Scanner(new File("files_urls.txt"));
        while(in.hasNext()){
            in.next();
            stack.push(in.next());
        }
    }
}
