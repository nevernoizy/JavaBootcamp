import java.util.ArrayList;

public class UsersArrayList implements UsersList{
    static private final ArrayList<User> usersArray = new ArrayList<>(10);
    static private int index = 0;
    @Override
    public void addUser(User user) {
        usersArray.add(index,user);
        index++;
        if(index == usersArray.size()){
            usersArray.ensureCapacity((int)Math.ceil(usersArray.size()*1.5));
        }
    }

    @Override
    public User getUserById(int id) throws Exception {
        for (User user : usersArray) {
            if (user.getIdentifier() == id) {
                return user;
            }
        }
        throw new Exception("UserNotFoundException");
        //return null;
    }

    @Override
    public User getUserByIndex(int index) {
        return usersArray.get(index);
    }

    @Override
    public int getNumbersOfUsers() {
        int res = 0;
        for(User user: usersArray){
            if(user != null){
                res++;
            }
        }
        return res;
    }
}
