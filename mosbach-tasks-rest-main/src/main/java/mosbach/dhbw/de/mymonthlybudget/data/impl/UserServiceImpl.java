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
public class UserServiceImpl implements UserService{ //hier habe ich das Interface Userservice entfernt
    private static List<User> users = new ArrayList();
    @Autowired
    private AuthService authService;

    static {
        users.add(new User("Mariam", "Kmayha", "mar.kmayha.23@lehre.mosbach.dhbw.de", "1234"));
        ((User)users.get(0)).setVerified(true);
    }


    public void addUser(User user) {
        users.add(user);
    }

    public User getUserByEmail(String email) {

        return users
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteUser(String email) {
        User existingUser = getUserByEmail(email);
        if (existingUser != null) {
            users.remove(existingUser);
            return true;
        }
        return false;
    }

    public User getUser(String token) {
        return this.getUserByEmail(this.authService.extractUsername(token));
    }

    @Override
    public User getUserByID(int userID) {
        return null;
    }

    @Override
    public void createUserTable() {

    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }


    public String getUserPATbyID(int userID){
        return users
                .stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .map(User::getPat)
                .orElse(null);
    }

    public User getUserById(int userID){
        return users
                .stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }
    }


