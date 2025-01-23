package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository{
    private Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Product> findAll(){
        List<Product> productList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product")){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                productList.add(new Product(id, name, price));

            }
            return productList;
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Optional<Product> findById(int findId){
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * " +
                "FROM product WHERE id = ?")){
            preparedStatement.setInt(1,findId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                Product product = new Product(id, name, price);
                return Optional.of(product);
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    public void update(Product product){
        try(PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET " +
                "name = ?, price = ? WHERE id = ?")){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getPrice());
            preparedStatement.setInt(3, product.getId());
            preparedStatement.executeUpdate();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void save(Product product){
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO product(name, price) " +
                "VALUES (?,?)")){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setInt(2,product.getPrice());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save message");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve message ID");
                }
            }

        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void delete(int id){
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM product " +
                "WHERE id = ?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
