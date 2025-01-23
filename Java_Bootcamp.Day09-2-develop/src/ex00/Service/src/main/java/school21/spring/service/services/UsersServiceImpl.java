package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import school21.spring.service.services.UsersService;

import javax.sql.DataSource;
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
}
