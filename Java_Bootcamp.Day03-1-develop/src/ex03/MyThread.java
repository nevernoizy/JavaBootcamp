import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Stack;

public class MyThread extends Thread{
    Stack<String> stack;
    MyThread(Stack<String> stack){
        this.stack = stack;
    }
    public void run(){
        while(!stack.empty()){
            String str = stack.pop();
            try{
                URL url = new URL(str);
                Path path = Paths.get(str);
                String fileName = path.getFileName().toString();
                Path outputPath = Path.of(System.getProperty("user.dir") + "/files/" + fileName);
                System.out.println(Thread.currentThread().getName() + " start download file:" +
                        fileName);
                try (InputStream in = url.openStream()) {
                    Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
                }
                System.out.println(Thread.currentThread().getName() + " finish download file:" +
                        fileName);
            } catch(Exception ex){
                System.out.println(Thread.currentThread().getName() + " " + ex.getMessage());
            }
        }
    }
}
