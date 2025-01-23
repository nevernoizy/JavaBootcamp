package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {
    EmbeddedDatabase db;
    @BeforeEach
    void init(){
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        try {
            assertNotNull(db.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void shutdown(){
        db.shutdown();
    }


    @Test
    void authenticateTest() throws Exception {
        UsersServiceImpl mockUsersServiceImpl = Mockito.spy(new UsersServiceImpl(db.getConnection()));
        User testUser = new User(1,"login", "password", false);
        Mockito.doReturn(testUser).when(mockUsersServiceImpl).findByLogin("login");
        Mockito.doNothing().when(mockUsersServiceImpl).update(testUser);

        mockUsersServiceImpl.authenticate("login", "password");

        Mockito.verify(mockUsersServiceImpl).update(testUser);
    }

    @Test
    void wrongLoginTest() throws Exception{
        UsersServiceImpl mockUsersServiceImpl = Mockito.spy(new UsersServiceImpl(db.getConnection()));
        Mockito.doReturn(null).when(mockUsersServiceImpl).findByLogin("login");
        assertFalse(mockUsersServiceImpl.authenticate("login", "password"));
    }

    @Test
    void wrongPassword() throws Exception{
        UsersServiceImpl mockUsersServiceImpl = Mockito.spy(new UsersServiceImpl(db.getConnection()));
        User testUser = new User(1,"login", "password", false);
        Mockito.doReturn(testUser).when(mockUsersServiceImpl).findByLogin("login");
        assertFalse(mockUsersServiceImpl.authenticate("login", "password1"));
    }
}
