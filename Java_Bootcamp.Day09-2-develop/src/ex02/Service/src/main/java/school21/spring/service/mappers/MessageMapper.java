package school21.spring.service.mappers;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import school21.spring.service.models.Chatroom;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageMapper implements RowMapper<Message> {
    public Message mapRow(ResultSet rs, int i) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setText(rs.getString("text"));
        message.setDate(rs.getTimestamp("date_time"));
        int author_id = rs.getInt("author_id");
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(
                "school21.spring.service");
        UsersRepository usersRepository =
                context.getBean("usersRepositoryImpl", UsersRepository.class);
        message.setAuthor(usersRepository.findById(author_id).get());
        message.setChatroom(new Chatroom(0,"name",new User(0,"name", "password"),null));
        return message;
    }
}
