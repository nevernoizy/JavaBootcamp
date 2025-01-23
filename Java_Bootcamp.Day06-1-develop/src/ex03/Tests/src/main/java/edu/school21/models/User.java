package edu.school21.models;

import java.util.Objects;

public class User {
    private int id;
    private String login;
    private String password;
    private boolean ass;

    public User(int id, String login, String password, boolean ass) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.ass = ass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", ass=" + ass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && ass == user.ass && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, ass);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAss() {
        return ass;
    }

    public void setAss(boolean ass) {
        this.ass = ass;
    }
}
