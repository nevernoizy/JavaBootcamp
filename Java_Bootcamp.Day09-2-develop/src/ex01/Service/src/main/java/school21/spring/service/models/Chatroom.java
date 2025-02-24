package school21.spring.service.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private int id;
    private String name;
    //private int owner_id;
    private User owner;
    private List<Message> messageList;

    public Chatroom(int id, String name, User owner, List<Message> messageList) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messageList = messageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner_id=" + owner.getId() +
                '}' + "\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
