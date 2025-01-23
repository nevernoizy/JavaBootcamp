package school21.spring.service.services;

import school21.spring.service.models.Message;
import school21.spring.service.models.User;

import java.util.Optional;

public interface UsersService {
    String signUp(String email, String password);
    Optional<User> signIn(String email, String password);
    void save(Message entity);
}
