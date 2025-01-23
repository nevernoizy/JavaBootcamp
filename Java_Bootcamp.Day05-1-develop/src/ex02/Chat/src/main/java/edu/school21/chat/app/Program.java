package edu.school21.chat.app;

import edu.school21.chat.DBConnection;
import edu.school21.chat.messagerep.MessageAccess;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.userrep.UsersRepositoryJdbcImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Program {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Connection connection = DBConnection.getConnection();
        MessageAccess messageAccess = new MessageAccess(connection);
        UsersRepositoryJdbcImpl usersRepositoryJdbc = new UsersRepositoryJdbcImpl(connection);
        //ex 01
        //System.out.println(messageAccess.findById(9));
        //ex 02

        User creator = new User(1, "user", "user", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom chatroom = new Chatroom(1, "room", creator, new ArrayList<>());
        Message message = new Message(123, author, chatroom, "Hello test", Timestamp.valueOf(LocalDateTime.now()));
        messageAccess.saveMessage(message);
        System.out.println(message.getId());

        //ex03
        /*
        Optional<Message> messageOptional = messageAccess.findById(5);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            System.out.println(message);
            message.setText("Bye");
            message.setDate(null);
            messageAccess.update(message);
        }
        Optional<Message> messageOptional1 = messageAccess.findById(5);
        if (messageOptional.isPresent()) {
            Message message1 = messageOptional1.get();
            System.out.println(message1);
        }*/
        //ex04
        /*
        List<User> usersList = usersRepositoryJdbc.findAll(1,4);
        System.out.println(usersList);
         */

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
}
