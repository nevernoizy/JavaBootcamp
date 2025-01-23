package edu.school21.chat.models;

import edu.school21.chat.models.Chatroom;

import java.util.List;
import java.util.Objects;

public class User {

    private int id;
    private String login;
    private String password;

    @Override
    public String toString() {
        return "\nUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", chatroomsCreated=" + chatroomsCreated +
                ", chatroomsMembered=" + chatroomsMembered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, chatroomsCreated, chatroomsMembered);
    }

    public User(int id, String login, String password, List<Chatroom> chatroomsCreated, List<Chatroom> chatroomsMembered) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.chatroomsCreated = chatroomsCreated;
        this.chatroomsMembered = chatroomsMembered;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    private List<Chatroom> chatroomsCreated;
    private List<Chatroom> chatroomsMembered;


    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getChatroomsCreated() {
        return chatroomsCreated;
    }

    public List<Chatroom> getChatroomsMembered() {
        return chatroomsMembered;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setChatroomsCreated(List<Chatroom> chatroomsCreated) {
        this.chatroomsCreated = chatroomsCreated;
    }

    public void setChatroomsMembered(List<Chatroom> chatroomsMembered) {
        this.chatroomsMembered = chatroomsMembered;
    }
}
