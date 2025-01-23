package edu.school21.chat.messagerep;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface MessagePepository {
    List<Message> findAllMessage();
    void saveMessage(Message message);
    //void deleteMessage(Message message);
    Optional<Message> findById(Integer id);
    void update(Message message);
}
