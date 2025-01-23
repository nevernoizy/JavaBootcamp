import java.util.UUID;

public class Transaction {
    private UUID identifier;
    private User recipient;
    private User sender;
    private String transferCategory;
    private int transferAmount;

    public Transaction(User recipient, User sender, String transferCategory, int transferAmount) {
        setIdentifier();
        setRecipient(recipient);
        setSender(sender);
        setTransferCategory(transferCategory);
        setTransferAmount(transferAmount);
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public void setIdentifier() {
        this.identifier = UUID.randomUUID();
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(String transferCategory) {
        if(transferCategory.equals("debit") || transferCategory.equals("credit")) {
            this.transferCategory = transferCategory;
        } else {
            System.err.println("wrong transfer category");
            System.exit(-1);
        }
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(int transferAmount) {
        if((transferAmount>0 && this.transferCategory.equals("credit")) ||
                transferAmount<0 && this.transferCategory.equals("debit")){
            System.err.println("wrong transfer amount");
            System.exit(-1);
        } else {
            this.transferAmount = transferAmount;
        }
    }
}
