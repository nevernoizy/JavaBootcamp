package school21.spring.service.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import school21.spring.service.repositories.UsersServiceImpl;

import javax.sql.DataSource;
@Configuration
//@Import(school21.spring.service.config.ApplicationConfig.class)
@ComponentScan("school21.spring.service.repositories")
@PropertySource("classpath:db1.properties")
public class TestApplicationConfig {
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public HikariDataSource hikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public UsersServiceImpl usersServiceImpl(){
        return new UsersServiceImpl(hikariDataSource());
    }
}
