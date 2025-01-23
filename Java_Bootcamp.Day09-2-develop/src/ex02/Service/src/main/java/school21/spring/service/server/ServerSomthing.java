package school21.spring.service.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.application.Main;
import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ServerSomthing extends Thread {

    private Socket socket; // сокет, через который сервер общается с клиентом,
    // кроме него - клиент и сервер никак не связаны
    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток завписи в сокет

    private AnnotationConfigApplicationContext context;
    private UsersService usersService;
    UsersRepository usersRepository;
    private String nickname = "";

    /**
     * для общения с клиентом необходим сокет (адресные данные)
     *
     * @param socket
     * @throws IOException
     */

    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        context = new AnnotationConfigApplicationContext(
                "school21.spring.service");
        usersService = context.getBean("usersServiceImpl", UsersService.class);
        usersRepository =
                context.getBean("usersRepositoryImpl", UsersRepository.class);
        start(); // вызываем run()
    }

    @Override
    public void run() {
        String word;
        try {
            menu();

            try {
                List<Message> history = usersService.historyChatroom(Main.serverMap.get(this));
                for (Message msg : history) {
                    out.write(msg.getAuthor().getEmail() + " " + msg.getText() + "\n");
                    out.flush();
                }
                while (true) {
                    word = in.readLine();
                    if (word.equals("stop")) {
                        this.downService(); // харакири
                        break; // если пришла пустая строка - выходим из цикла прослушки
                    }
                    System.out.println("Echoing: " + word);
                    Optional<User> user = usersRepository.findByEmail(nickname);
                    Chatroom chatroom = usersService.findChatroomByName(Main.serverMap.get(this)).get();
                    Message message = new Message(1, user.get(), chatroom, word,
                            Timestamp.valueOf(LocalDateTime.now()));
                    System.out.println(message);
                    usersService.save(message);
                    word = nickname + " " + Timestamp.valueOf(LocalDateTime.now()) + ": " + word;
                    for (Map.Entry<ServerSomthing, String> entry : Main.serverMap.entrySet()) {
                        ServerSomthing vr = entry.getKey();
                        String room = entry.getValue();
                        if (room.equals(Main.serverMap.get(this))) {
                            vr.send(word);
                        }
                    }
                }
            } catch (NullPointerException ignored) {
            }


        } catch (IOException e) {
            this.downService();
        }
    }

    public void menu() throws IOException {
        String buffer = "";
        while (true) {
            printOptions();
            buffer = in.readLine();
            if (buffer == null) {
                this.downService();
                break;
            }
            System.out.println(buffer);
            if (buffer.equals("signUp")) {
                signUp();
            } else if (buffer.equals("signIn")) {
                if (signIn()) {
                    break;
                }
            }
        }

        while (true) {
            printRoomOptions();
            buffer = in.readLine();
            if (buffer == null) {
                this.downService();
                break;
            }
            System.out.println(buffer);
            if (buffer.equals("enterRoom")) {
                roomsPrint();
                if (selectRoom()) {
                    break;
                }
            } else if (buffer.equals("createRoom")) {
                createRoom();
            }
        }
    }

    public void signUp() throws IOException {
        out.write("enter login\n");
        out.flush();
        String login = in.readLine();
        out.write("enter password\n");
        out.flush();
        String password = in.readLine();
        usersService.signUp(login, password);
    }

    public void printOptions() throws IOException {
        out.write("signIn\n");
        out.flush();
        out.write("signUp\n");
        out.flush();
        out.write("to exit: stop\n");
        out.flush();
    }

    public void printRoomOptions() throws IOException {
        out.write("enterRoom\n");
        out.flush();
        out.write("createRoom\n");
        out.flush();
        out.write("to exit: stop\n");
        out.flush();
    }

    public void roomsPrint() throws IOException {
        List<Chatroom> rooms = usersService.findAllChatrooms();
        for (Chatroom room : rooms) {
            out.write(room.getName() + "\n");
            out.flush();
        }
    }

    public boolean selectRoom() throws IOException {
        out.write("select room:\n");
        out.flush();
        String roomName = in.readLine();
        List<Chatroom> rooms = usersService.findAllChatrooms();
        for (Chatroom room : rooms) {
            if (room.getName().equals(roomName)) {
                Main.serverMap.put(this, roomName);
                return true;
            }
        }
        return false;
    }

    private void createRoom() throws IOException {
        out.write("enter room name\n");
        out.flush();
        String roomname = in.readLine();
        Optional<User> user = usersRepository.findByEmail(nickname);
        usersService.createChatroom(roomname, user.get());
        out.write("room created\n");
        out.flush();
    }


    public boolean signIn() throws IOException {
        boolean res = false;
        out.write("Enter username:\n");
        out.flush();
        String username = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();
        Optional<User> user = usersService.signIn(username, password);
        if (user.isPresent()) {
            out.write("Successful!\n");
            out.flush();
            res = true;
            nickname = username;
        } else {
            out.write("try again!\n");
            out.flush();
        }
        return res;
    }

    /**
     * отсылка одного сообщения клиенту по указанному потоку
     *
     * @param msg
     */
    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {
        }


    }

    /**
     * закрытие сервера
     * прерывание себя как нити и удаление из списка нитей
     */
    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing vr : Main.serverList) {
                    if (vr.equals(this)) vr.interrupt();
                    Main.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {
        }
    }
}
