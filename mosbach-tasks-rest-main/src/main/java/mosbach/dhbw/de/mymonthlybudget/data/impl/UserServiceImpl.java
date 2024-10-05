package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static List<User> users = new ArrayList<>();

    @Autowired
    private AuthService authService;

    static{
        users.add(new User("Mariam", "Kmayha", "mar.kmayha.23@lehre.mosbach.dhbw.de","1234"));
    }


    @Override
    public void addUser(User user) {
        users.add(user);

    }

    @Override
    public User getUserByEmail(String email) {
        return (User) users
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUser(String token) {
        return getUserByEmail(authService.extractUsername(token));
    }

    @Override
    public User getUserByID(int userID) {
        return users
                .stream()
                .filter(user -> user.getUserID() == userID)
                .findFirst()
                .orElse(null);
    }
}
