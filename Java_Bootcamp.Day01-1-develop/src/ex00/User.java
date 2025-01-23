import java.util.UUID;

public class User {

    private int identifier;
    private String userName;
    private int balance;
    public User(int identifier, String userName, int balance) {
        setBalance(balance);
        setIdentifier(identifier);
        setUserName(userName);
    }
    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        //this.identifier = identifier;
        this.identifier = identifier;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        if (balance > 0) {
            this.balance = balance;
        } else {
            System.err.println("negative balance");
            System.exit(-1);
        }
    }


}


