//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package mosbach.dhbw.de.mymonthlybudget.data.impl;

import java.util.ArrayList;
import java.util.List;
import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static List<User> users = new ArrayList();
    @Autowired
    private AuthService authService;

    public UserServiceImpl() {
    }

    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByEmail(String email) {
        User foundUser = (User)users.stream().filter((user) -> {
            return user.getEmail().equals(email);
        }).findFirst().orElse((User) null);
        if (foundUser == null) {
            System.out.println("Benutzer nicht gefunden");
        } else {
            System.out.println("Benutzer gefunden" + foundUser.getEmail());
        }

        return foundUser;
    }

    public User getUser(String token) {
        return this.getUserByEmail(this.authService.extractUsername(token));
    }

    public User getUserByID(int userID) {
        return (User)users.stream().filter((user) -> {
            return user.getUserID() == userID;
        }).findFirst().orElse((User) null);
    }

    static {
        users.add(new User(1, "Mariam", "Kmayha", "mar.kmayha.23@lehre.mosbach.dhbw.de", "1234", "xd"));
        ((User)users.get(0)).setVerified(true);
    }
}
