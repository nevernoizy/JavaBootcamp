package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.Message;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UserMapper;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


}
