import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private final int port;
    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        socket = new Socket("127.0.0.1", port);
        reader = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
        reader.close();
    }

    public void work() throws IOException {
        String buffer = "";
        while(!buffer.equals("exit")){
            buffer = reader.readLine();
            out.write(buffer + "\n");
            out.flush();
            String serverWord = in.readLine();
            System.out.println(serverWord);
        }
    }
}
