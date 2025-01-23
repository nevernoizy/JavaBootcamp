package edu.school21.chat.userrep;

import edu.school21.chat.models.User;

import java.sql.Connection;
import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size);
    void save(User user);
}
