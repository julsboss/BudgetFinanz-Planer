package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.data.impl.User;

import java.util.List;

public interface UserManager {

    public boolean deleteUser (String email);

    //void addUser(UserService newUser);

    List<User> getAllUser();

    public void createUserTable();


    public void addUser(User user);

    public User getUserByEmail(String email);

    public User getUser(String token);

    public User getUserByID(int userID);

    public boolean updateUser(User user);

}
