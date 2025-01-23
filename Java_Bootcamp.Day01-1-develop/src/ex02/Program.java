public class Program {
    public static void main(String[] args) throws Exception {
        User user1 = new User( "Jack", 500);
        User user2 = new User( "Nick", 700);
        //System.out.println(user1.getIdentifier() + " " + user1.getUserName() + " " + user1.getBalance());
        //System.out.println(user2.getIdentifier() + " " + user2.getUserName() + " " + user2.getBalance());
        UsersArrayList usersArray = new UsersArrayList();
        usersArray.addUser(user1);
        usersArray.addUser(user2);
        System.out.println(usersArray.getNumbersOfUsers());
        System.out.println(usersArray.getUserById(2).getUserName());
        System.out.println(usersArray.getUserByIndex(0).getUserName());
    }
}
