package mosbach.dhbw.de.mymonthlybudget.data.api;

import java.util.List;

public interface UserManager {

   void addUser(UserService newUser);

    List<UserService> getAllUser();

}
