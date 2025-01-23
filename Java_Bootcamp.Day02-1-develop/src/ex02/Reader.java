import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Reader {
    private String path;

    public Reader() {
        this.path = System.getProperty("user.dir");
    }

    public void lsFunc() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("ls", "-sh");
        processBuilder.directory(new File(path));
        Process process = processBuilder.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
    public void cdFunc(String str1) throws IOException {
        File directory = new File(str1);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("Directory not found: " + str1);
        }
        File newDirectory = new File(path, str1);
        path = newDirectory.getAbsolutePath();
    }
    public void mvFunc(String str1, String str2) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        File directory = new File(str1);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new IllegalArgumentException("File not found: " + str1);
        }
        processBuilder.command("mv", str1, str2);
        processBuilder.directory(new File(path));
        Process process = processBuilder.start();
    }

    public void mainFunc(){
        Scanner scanner = new Scanner(System.in);
        String str1 = "";
        String str2 = "";
        String str3 = "";
        while(!str1.equals("exit")){
            str1 = scanner.next();
            if(str1.equals("ls")){
                try{
                    lsFunc();
                } catch(Exception ex){
                    System.out.println("error");
                }
            } else if(str1.equals("cd")){
                str2 = scanner.next();
                try{
                    cdFunc(str2);
                } catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            } else if(str1.equals("mv")){
                str2 = scanner.next();
                str3 = scanner.next();
                try{
                    mvFunc(str2, str3);
                } catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
