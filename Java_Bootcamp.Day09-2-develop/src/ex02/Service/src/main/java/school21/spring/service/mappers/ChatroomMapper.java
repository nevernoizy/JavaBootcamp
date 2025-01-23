package school21.spring.service.mappers;

import org.springframework.jdbc.core.RowMapper;
import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatroomMapper implements RowMapper<Chatroom> {
    public Chatroom mapRow(ResultSet rs, int i) throws SQLException {
        Chatroom chatroom = new Chatroom();
        chatroom.setId(rs.getInt("id"));
        chatroom.setName(rs.getString("name"));
        chatroom.setOwner(new User());
        chatroom.setMessageList(new ArrayList<Message>());
        return chatroom;
    }
}
