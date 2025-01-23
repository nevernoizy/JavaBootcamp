package school21.spring.service.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM User_table WHERE id = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, new UserMapper(), id);
            return Optional.of(users.get(0));
        } catch(Exception ex){
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("select * from User_table", new UserMapper());
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO User_table(email) VALUES (?)";
        jdbcTemplate.update(sql, entity.getEmail());
    }

    @Override
    public void update(User entity) {
        String SQL = "UPDATE User_table SET email = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getId());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM User_table WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM User_table WHERE email = ?";
        try {
            List<User> users = jdbcTemplate.query(sql, new UserMapper(), email);
            return Optional.of(users.get(0));
        } catch(Exception ex) {
            return Optional.empty();
        }
    }
}
