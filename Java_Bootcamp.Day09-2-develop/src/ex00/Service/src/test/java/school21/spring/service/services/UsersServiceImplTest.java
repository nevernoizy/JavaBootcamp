package school21.spring.service.services;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;


public class UsersServiceImplTest {
    private static UsersService usersService;
    /*@BeforeAll
    static void before(){
        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(
                "school21.spring.service");
        usersService = context.getBean("usersServiceImpl", UsersService.class);
    }

    @Test
    void signTest(){
        assertNotNull(usersService.signUp("testMail", "testpassword"));
    }*/
}
