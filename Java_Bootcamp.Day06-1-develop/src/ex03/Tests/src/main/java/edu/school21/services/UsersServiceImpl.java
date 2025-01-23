package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.Product;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

public class UsersServiceImpl implements UsersRepository {
    private final Connection connection;

    public UsersServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public User findByLogin(String login){
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * " +
                "FROM user WHERE login = ?")){
            preparedStatement.setString(1,login);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String userLogin = rs.getString("login");
                String password = rs.getString("password");
                boolean ass = rs.getBoolean("ass");
                return new User(id, userLogin, password, ass);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public void update(User user){
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET " +
                "login = ?, password = ?, ass = ? WHERE id = ?")){
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3,user.isAss());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public boolean authenticate(String login, String password) throws Exception {
        User user = this.findByLogin(login);
        if(user!= null && password.equals(user.getPassword())){
            if(user.isAss()){
                throw new AlreadyAuthenticatedException("already in");
            } else{
                user.setAss(true);
                this.update(user);
                return true;
            }
        }
        return false;
    }

}
