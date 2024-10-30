package mosbach.dhbw.de.mymonthlybudget.data.api;


import mosbach.dhbw.de.mymonthlybudget.data.impl.User;

import java.util.List;
//nicht mehr in Benutzung
public interface UserService {

    public void addUser(User user);

    public User getUserByEmail(String email);

    public boolean deleteUser(String email);

    public User getUser(String token);

    public User getUserByID(int userID);

    public String getUserPATbyID(int userID);

    public void createUserTable();

    public List<User> getAllUsers();

}


