package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.mappers.ChatroomMapper;
import school21.spring.service.mappers.MessageMapper;
import school21.spring.service.mappers.UserMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UsersServiceImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String signUp(String email, String password) {
        String sql = "INSERT INTO User_table(email, password) VALUES (?, ?)";
        //String password = String.valueOf(UUID.randomUUID());
        jdbcTemplate.update(sql, email, password);
        return password;
    }

    public Optional<User> signIn(String email, String password) {
        String sql = "SELECT * FROM User_table WHERE email = ? AND password = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, new UserMapper(), email, password);
            return Optional.of(users.get(0));
        } catch(Exception ex){
            return Optional.empty();
        }
    }

    @Override
    public void save(Message entity) {
        String sql = "INSERT INTO Message(author_id, room_id, text, date_time) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getAuthor().getId(), entity.getChatroom().getId(),
                entity.getText(), entity.getDate());
    }


    public List<Chatroom> findAllChatrooms(){
        List<Chatroom> chatrooms= new ArrayList<Chatroom>();
        String sql = "SELECT * FROM Chatroom";
        chatrooms = jdbcTemplate.query(sql, new ChatroomMapper());
        return chatrooms;
    }

    public void createChatroom(String name, User owner){
        String sql = "INSERT INTO Chatroom (name, owner_id) VALUES (?, ?);";
        jdbcTemplate.update(sql, name, owner.getId());
    }

    public Optional<Chatroom> findChatroomByName(String name){
        String sql = "SELECT * FROM Chatroom WHERE name = ?;";
        try {
            List<Chatroom> chatrooms = jdbcTemplate.query(sql, new ChatroomMapper(), name);
            return Optional.of(chatrooms.get(0));
        } catch(Exception ex){
            return Optional.empty();
        }

    }

    public List<Message> historyChatroom(String chatroomName){
        String sql = "SELECT * FROM Message WHERE room_id = ? ORDER BY date_time asc LIMIT 10;";
        Optional<Chatroom> chatroom = findChatroomByName(chatroomName);


        return jdbcTemplate.query(sql, new MessageMapper(), chatroom.get().getId());
    }

    public List<Message> findAll() {
        return jdbcTemplate.query("select * from Message", new MessageMapper());
    }

}
