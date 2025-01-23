package school21.spring.service.application;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
        UsersRepository usersRepository =
                context.getBean("userRepositoryJdbcTemplate", UsersRepository.class);
        //usersRepository.save(new User(0, "123@gmail.com"));
        usersRepository.delete(1);
        System.out.println(usersRepository.findAll());
        System.out.println(usersRepository.findById(3));
        System.out.println(usersRepository.findByEmail("123@gmail.com"));

        UsersRepository usersRepository1 =
                context.getBean("userRepositoryJdbc", UsersRepository.class);
        usersRepository1.delete(2);
        usersRepository1.update(new User(4, "emailemail"));
        usersRepository1.save(new User(0,"zxcmail"));
        System.out.println(usersRepository1.findById(3));
        System.out.println(usersRepository1.findByEmail("123@gmail.com"));
        System.out.println(usersRepository1.findAll());
    }
}
