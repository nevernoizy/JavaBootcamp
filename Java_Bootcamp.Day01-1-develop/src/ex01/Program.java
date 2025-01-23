public class Program {
    public static void main(String[] args) {
        User user1 = new User( "Jack", 500);
        User user2 = new User( "Nick", 700);
        System.out.println(user1.getIdentifier() + " " + user1.getUserName() + " " + user1.getBalance());
        System.out.println(user2.getIdentifier() + " " + user2.getUserName() + " " + user2.getBalance());
    }
}
