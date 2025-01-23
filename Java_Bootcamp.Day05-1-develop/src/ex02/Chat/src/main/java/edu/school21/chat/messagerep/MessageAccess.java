package edu.school21.chat.messagerep;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MessageAccess implements MessagePepository{
    private final Connection connection;
    public MessageAccess(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Message> findAllMessage(){
        List<Message> messageList = new ArrayList<>();
        try(/*Connection connection = DBConnection.getConnection();*/
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM message")){
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int author_id = rs.getInt("author_id");
                int room_id = rs.getInt("room_id");
                String text = rs.getString("text");
                Timestamp date = rs.getTimestamp("date_time");
                messageList.add(new Message(id, findUserById(author_id), findChatroomById(room_id), text, date));
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return messageList;
    }
    @Override
    public void saveMessage(Message message){
        //List<User> usersList = new ArrayList<>();
        try(/*Connection connection = DBConnection.getConnection();*/
                PreparedStatement preparedStatement =
                        connection.prepareStatement("INSERT INTO Message (author_id, room_id, text, date_time) VALUES (?,?,?,NOW())",
                                Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setInt(1,message.getAuthor().getId());
            preparedStatement.setInt(2,message.getChatroom().getId());
            preparedStatement.setString(3,message.getText());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to save message");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve message ID");
                }
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public Optional<Message> findById(Integer finderId){
        List<Message> messageList = new ArrayList<>();
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM message WHERE id = (?)")){
            preparedStatement.setInt(1,finderId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                int author_id = rs.getInt("author_id");
                int room_id = rs.getInt("room_id");
                String text = rs.getString("text");
                Timestamp date = rs.getTimestamp("date_time");
                //messageList.add(new Message(id, findUserById(author_id), room_id, text, date));
                Message message = new Message(id, findUserById(author_id), findChatroomById(room_id), text, date);
                return Optional.of(message);
            }

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }

    /*@Override
    public void deleteMessage(Message message){
        try(PreparedStatement preparedStatement =
                    connection.prepareStatement("")){

        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }*/
    private User findUserById(Integer id){
        String query = "SELECT * FROM User_table WHERE id = ?";
        try(/*Connection connection = DBConnection.getConnection();*/
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new User(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        null,
                        null
                );
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private Chatroom findChatroomById(Integer id){
        String query = "SELECT * FROM Chatroom WHERE id = ?";
        try(/*Connection connection = DBConnection.getConnection();*/
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,id);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new Chatroom(
                        rs.getInt("id"),
                        rs.getString("name"),
                        findUserById(rs.getInt("owner_id")),
                        null
                );
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
    public void update(Message message){
        String query = "UPDATE message SET author_id = ?, room_id = ?, text = ?, date_time = ? WHERE id = ?";
        try(/*Connection connection = DBConnection.getConnection();*/
                PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1,message.getAuthor().getId());
            preparedStatement.setInt(2,message.getChatroom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4,message.getDate());
            preparedStatement.setInt(5,message.getId());
            preparedStatement.executeUpdate();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
