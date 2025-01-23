package school21.spring.service.services;

import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;

import java.util.List;
import java.util.Optional;

public interface UsersService {
    String signUp(String email, String password);
    Optional<User> signIn(String email, String password);
    void save(Message entity);
    public List<Chatroom> findAllChatrooms();
    public void createChatroom(String name, User owner);
    public List<Message> historyChatroom(String chatroomName);
    public Optional<Chatroom> findChatroomByName(String name);
    public List<Message> findAll();
}
