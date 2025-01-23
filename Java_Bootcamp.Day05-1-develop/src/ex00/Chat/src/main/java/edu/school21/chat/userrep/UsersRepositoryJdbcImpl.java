package edu.school21.chat.userrep;


import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final Connection connection;

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String INSERT_USER = "INSERT INTO User_table(login, password) VALUES (?,?);";
    private static final String query = "SELECT * FROM User_table";
    private static final String FIND_ALL =
            "SELECT User_table.id, User_table.login, User_table.password, Chatroom.id AS chatroom_id, name, owner_id, Message.id AS message_id, author_id, room_id,  text, date_time FROM User_table LEFT JOIN Chatroom ON User_table.id = owner_id LEFT JOIN Message ON User_table.id = author_id ORDER BY 9,4,1";
    private static final String test = "SELECT User_table.id, User_table.login, User_table.password, " +
            "Chatroom.id AS chatroom_id, name, owner_id, Message.id AS message_id, author_id, room_id,  " +
            "text, date_time FROM User_table LEFT JOIN Chatroom ON User_table.id = owner_id" +
            " LEFT JOIN Message ON User_table.id = author_id ORDER BY 9,1";
    public List<User> findAll(int page, int size){
        List<User> usersList = new ArrayList<>();
        List<Chatroom> helpListChatroom = new ArrayList<>();
        try(/*Connection connection = DBConnection.getConnection();*/
            PreparedStatement preparedStatement = connection.prepareStatement(test)){
            ResultSet rs = preparedStatement.executeQuery();
            User bufferUser = null;
            User bufferUser1 = null;
            Chatroom bufferedChatroom = null;
            //Message bufferedMessage = null;
            while(rs.next()){
                int id = rs.getInt("id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                int chatroom_id = rs.getInt("chatroom_id");
                String name = rs.getString("name");
                int owner_id = rs.getInt("owner_id");
                //int message_id = rs.getInt("message_id");
                //int author_id = rs.getInt("author_id");
                int room_id = rs.getInt("room_id");
                //String text = rs.getString("text");
                //Timestamp date_time = rs.getTimestamp("date_time");
                bufferUser1 = new User(id, login, password, new ArrayList<>(),new ArrayList<>());
                if(!usersList.contains(bufferUser1)){
                    bufferUser = bufferUser1;
                    usersList.add(bufferUser);
                }

                    bufferedChatroom = new Chatroom(chatroom_id, name, findUserByIdList(usersList, owner_id), null);
                if(name!=null) {
                    if (!Objects.requireNonNull(findUserByIdList(usersList, owner_id)).getChatroomsCreated().contains(bufferedChatroom)) {
                        Objects.requireNonNull(findUserByIdList(usersList, owner_id)).getChatroomsCreated().add(bufferedChatroom);
                        helpListChatroom.add(bufferedChatroom);
                    }
                }
                    if (!Objects.requireNonNull(findUserByIdList(usersList, id)).getChatroomsMembered().contains(findChatroomById(helpListChatroom, room_id)) && room_id!=0) {
                        Objects.requireNonNull(findUserByIdList(usersList, id)).getChatroomsMembered().add(findChatroomById(helpListChatroom, room_id));
                    }
            }
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        sortList(usersList);
        List<User> returnList = new ArrayList<>();
        for(int i = (page-1)*size; i < page*size && i < usersList.size(); i++){
            returnList.add(usersList.get(i));
        }
        //return usersList;
        return returnList;
    }
    private void sortList(List<User> userList){
        for(int i = 0; i < userList.size()-1; i++){
            for(int j = 0; j < userList.size()-i-1; j++){
                if(userList.get(j).getId() >userList.get(j+1).getId()){
                    User bufUser = userList.get(j);
                    userList.set(j, userList.get(j+1));
                    userList.set(j+1, bufUser);
                }
            }
        }
    }
    private User findUserByIdList(List<User> usersList,Integer id){
        for (User user:usersList) {
            if (user.getId()==id){
                return user;
            }
        }
        return null;
    }
    private Chatroom findChatroomById(List<Chatroom> chatroomList, Integer id){
        for (Chatroom chatroom: chatroomList) {
            if (chatroom.getId()==id){
                return chatroom;
            }
        }
        return null;
    }
    public void save(User user){
        List<User> usersList = new ArrayList<>();
        try(/*Connection connection = DBConnection.getConnection();*/
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)){
            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.executeUpdate();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
