public class Program {
    public static void main(String[] args) {
        User user1 = new User(1, "Jack", 500);
        User user2 = new User(2, "Nick", 700);
        System.out.println(user1.getIdentifier() + " " + user1.getUserName() + " " + user1.getBalance());
        System.out.println(user2.getIdentifier() + " " + user2.getUserName() + " " + user2.getBalance());

        Transaction tr1 = new Transaction(user1, user2, "credit", -300);
        System.out.println(tr1.getIdentifier());
        System.out.println(tr1.getRecipient().getUserName());
        System.out.println(tr1.getSender().getUserName());
        System.out.println(tr1.getTransferCategory());
        System.out.println(tr1.getTransferAmount());
    }
}
