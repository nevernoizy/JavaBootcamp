package school21.spring.service.application;

import com.beust.jcommander.JCommander;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.server.Server;
import school21.spring.service.services.UsersService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Args commandLineArgs = new Args();
        JCommander jCommander = JCommander.newBuilder().addObject(commandLineArgs).build();
        jCommander.parse(args);

        try {
            Server server = new Server(commandLineArgs.getPort());
            server.start();
            server.work();
            server.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }



        AnnotationConfigApplicationContext context = new
                AnnotationConfigApplicationContext(
                "school21.spring.service");
        UsersRepository usersRepository =
                context.getBean("usersRepositoryImpl", UsersRepository.class);
        System.out.println(usersRepository.findAll());

    }
}
