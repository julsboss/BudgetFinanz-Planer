package mosbach.dhbw.de.mymonthlybudget.data.api;


import mosbach.dhbw.de.mymonthlybudget.model.User;

public interface UserService {

    public void addUser(User user);

    public User getUserByEmail(String email);

    public User getUser(String token);

    public User getUserByID(int userID);


}
