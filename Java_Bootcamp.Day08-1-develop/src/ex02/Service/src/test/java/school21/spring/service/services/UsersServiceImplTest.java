package school21.spring.service.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UsersServiceImplTest {
    private static UsersService usersService;
    @BeforeAll
    static void before(){
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(
                "school21.spring.service");
        usersService = context.getBean("usersServiceImpl", UsersService.class);
    }

    @Test
    void signTest(){
        assertNotNull(usersService.signUp("testMail"));
    }
}
