package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.UUID;

@Component("usersServiceImpl")
public class UsersServiceImpl implements UsersService{
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public UsersServiceImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public String signUp(String email) {
        String sql = "INSERT INTO User_table(email) VALUES (?)";
        jdbcTemplate.update(sql, email);
        return String.valueOf(UUID.randomUUID());
    }
}
