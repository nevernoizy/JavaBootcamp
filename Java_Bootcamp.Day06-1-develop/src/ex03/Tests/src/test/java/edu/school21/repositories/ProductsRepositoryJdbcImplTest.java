package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProductsRepositoryJdbcImplTest {
    EmbeddedDatabase db;
    Connection connection;
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0,"product1", 100 ),
            new Product(1,"product2", 200 ),
            new Product(2,"product3", 300 ),
            new Product(3,"product4", 400 ),
            new Product(4,"product5", 500 )
    );
    final List<Product> EXPECTED_AFTER_DELETE_PRODUCTS = Arrays.asList(
            new Product(0,"product1", 100 ),
            new Product(1,"product2", 200 ),
            new Product(3,"product4", 400 ),
            new Product(4,"product5", 500 )
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2,"product3",300);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(3, "updatedProduct", 1000);

    @BeforeEach
    void init() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        connection = db.getConnection();
    }

    @AfterEach
    void shutdown(){
        db.shutdown();
    }
    @Test
    void findAllTest(){
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
            List<Product> productList = productsRepositoryJdbc.findAll();
            for(int i = 0; i < EXPECTED_FIND_ALL_PRODUCTS.size(); i++){
                assertEquals(EXPECTED_FIND_ALL_PRODUCTS.get(i), productList.get(i));
            }
    }
    @Test
    void findByIdTest(){
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
        Optional<Product> product = productsRepositoryJdbc.findById(2);
        assertEquals(product.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }
    @Test
    void updateTest(){
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        assertEquals(productsRepositoryJdbc.findById(3).get(), EXPECTED_UPDATED_PRODUCT);
    }
    @Test
    void saveTest(){
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
        productsRepositoryJdbc.save(new Product(1,"product6", 600));
        assertEquals(productsRepositoryJdbc.findById(5).get(),
                new Product(5,"product6", 600));
    }
    @Test
    void deleteTest(){
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(connection);
        productsRepositoryJdbc.delete(2);
        List<Product> productList = productsRepositoryJdbc.findAll();
        for(int i = 0; i < productList.size(); i++){
            assertEquals(EXPECTED_AFTER_DELETE_PRODUCTS.get(i), productList.get(i));
        }
    }

}
