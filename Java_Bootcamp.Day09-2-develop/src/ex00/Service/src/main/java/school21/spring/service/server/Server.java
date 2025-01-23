package school21.spring.service.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.services.UsersService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader in;
    private  BufferedWriter out;
    private AnnotationConfigApplicationContext context;
    private UsersService usersService;

    public Server(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        context = new AnnotationConfigApplicationContext(
                "school21.spring.service");
        usersService = context.getBean("usersServiceImpl", UsersService.class);
    }

    public void close() throws Exception {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public void work() throws Exception {
        //out.write("Hello from Server!");
        //out.flush();
        String buffer = "";
        while(!buffer.equals("exit")){
            buffer = in.readLine();
            System.out.println(buffer);
            if(buffer.equals("signUp")){
                signUp();
            } else if (!buffer.equals("exit")){
                out.write("error\n");
                out.flush();
            }
        }
    }

    public void signUp() throws Exception {
        out.write("Enter username:\n");
        out.flush();
        String username = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();
        usersService.signUp(username, password);
        out.write("Successful!\n");
        out.flush();
    }


}
