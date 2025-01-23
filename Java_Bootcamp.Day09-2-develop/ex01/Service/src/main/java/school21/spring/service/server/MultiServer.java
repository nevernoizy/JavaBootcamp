package school21.spring.service.server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.services.UsersService;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Optional;

class ServerSomthing extends Thread {

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
     * @param socket
     * @throws IOException
     */

    public ServerSomthing(Socket socket) throws IOException {
        this.socket = socket;
        // если потоку ввода/вывода приведут к генерированию искдючения, оно проброситься дальше
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //MultiServer.story.printStory(out); // поток вывода передаётся для передачи истории последних 10
        // сооюбщений новому поключению
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
            String buffer = "";
            while(!buffer.equals("exit")){
                buffer = in.readLine();
                System.out.println(buffer);
                if(buffer.equals("signIn")){
                    if(signIn()){
                        break;
                    }

                } else if (!buffer.equals("exit")){
                    out.write("error\n");
                    out.flush();
                }
            }

            // первое сообщение отправленное сюда - это никнейм
           /* word = in.readLine();
            try {
                out.write(word + "\n");
                out.flush(); // flush() нужен для выталкивания оставшихся данных
                // если такие есть, и очистки потока для дьнейших нужд
            } catch (IOException ignored) {}*/
            try {
                MultiServer.story.printStory(out);
                while (true) {
                    word = in.readLine();
                    if(word.equals("stop")) {
                        this.downService(); // харакири
                        break; // если пришла пустая строка - выходим из цикла прослушки
                    }
                    System.out.println("Echoing: " + word);
                    Optional<User> user = usersRepository.findByEmail(nickname);
                    Chatroom chatroom = new Chatroom(1, "room1",
                            user.get(), null);
                    Message message = new Message(1,user.get(), chatroom, word,
                            Timestamp.valueOf(LocalDateTime.now()));
                    System.out.println(message);
                    usersService.save(message);
                    word = nickname + " " + Timestamp.valueOf(LocalDateTime.now()) + ": " + word;
                    MultiServer.story.addStoryEl(word);
                    for (ServerSomthing vr : MultiServer.serverList) {
                        vr.send(word); // отослать принятое сообщение с привязанного клиента всем остальным влючая его
                    }
                }
            } catch (NullPointerException ignored) {}


        } catch (IOException e) {
            this.downService();
        }
    }

    public boolean signIn() throws IOException {
        boolean res = false;
        out.write("Enter username:\n");
        out.flush();
        String username = in.readLine();
        out.write("Enter password:\n");
        out.flush();
        String password = in.readLine();
        Optional<User> user =usersService.signIn(username, password);
        if(user.isPresent()) {
            out.write("Successful!\n");
            out.flush();
            res = true;
            nickname = username;
        } else{
            out.write("try again!\n");
            out.flush();
        }
        return res;
    }

    /**
     * отсылка одного сообщения клиенту по указанному потоку
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
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomthing vr : MultiServer.serverList) {
                    if(vr.equals(this)) vr.interrupt();
                    MultiServer.serverList.remove(this);
                }
            }
        } catch (IOException ignored) {}
    }
}

/**
 * класс хранящий в ссылочном приватном
 * списке информацию о последних 10 (или меньше) сообщениях
 */

class Story {

    private LinkedList<String> story = new LinkedList<>();

    /**
     * добавить новый элемент в список
     * @param el
     */

    public void addStoryEl(String el) {
        // если сообщений больше 10, удаляем первое и добавляем новое
        // иначе просто добавить
        if (story.size() >= 10) {
            story.removeFirst();
            story.add(el);
        } else {
            story.add(el);
        }
    }

    /**
     * отсылаем последовательно каждое сообщение из списка
     * в поток вывода данному клиенту (новому подключению)
     * @param writer
     */

    public void printStory(BufferedWriter writer) {
        if(story.size() > 0) {
            try {
                writer.write("History messages" + "\n");
                for (String vr : story) {
                    writer.write(vr + "\n");
                }
                writer.write("/...." + "\n");
                writer.flush();
            } catch (IOException ignored) {}

        }

    }
}
public class MultiServer {
    public static final int PORT = 8080;
    public static LinkedList<ServerSomthing> serverList = new LinkedList<>(); // список всех нитей - экземпляров
    // сервера, слушающих каждый своего клиента
    public static Story story; // история переписки

    /**
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        story = new Story();
        System.out.println("Server Started");
        try {
            while (true) {
                // Блокируется до возникновения нового соединения:
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomthing(socket)); // добавить новое соединенние в список
                } catch (IOException e) {
                    // Если завершится неудачей, закрывается сокет,
                    // в противном случае, нить закроет его:
                    socket.close();
                }
            }
        } finally {
            server.close();
        }
    }
}
