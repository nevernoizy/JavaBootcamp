package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component("userRepositoryJdbc")
public class UsersRepositoryJdbcImpl implements UsersRepository{
    private final DataSource dataSource;
    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("hikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM User_table WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int rsid = rs.getInt("id");
            String email = rs.getString("email");
            User user = new User(rsid, email);
            return Optional.of(user);
        } catch (Exception ex){
            //System.out.println(ex.getMessage());
            return Optional.empty();
        }
        //return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM User_table";
        List<User> users = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                int rsid = rs.getInt("id");
                String email = rs.getString("email");
                User user = new User(rsid, email);
                users.add(user);
            }
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public void save(User entity) {
        String sql = "INSERT INTO User_table(email) VALUES (?)";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,entity.getEmail());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE User_table SET email = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,entity.getEmail());
            preparedStatement.setInt(2,entity.getId());
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM User_table WHERE id = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM User_table WHERE email = ?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,email);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            int id = rs.getInt("id");
            String rsemail = rs.getString("email");
            User user = new User(id, rsemail);
            return Optional.of(user);
        } catch (Exception ex){
            //System.out.println(ex.getMessage());
            return Optional.empty();
        }
        //return Optional.empty();
    }
}
