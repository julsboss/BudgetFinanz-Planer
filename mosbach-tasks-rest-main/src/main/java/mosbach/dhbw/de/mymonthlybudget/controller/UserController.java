package mosbach.dhbw.de.mymonthlybudget.controller;


import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.dto.MessageReason;
import mosbach.dhbw.de.mymonthlybudget.dto.UserDTO;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token){
        User user = userManager.getUser(token);
        if(user != null){
            return new ResponseEntity<UserDTO>(new UserDTO(user), HttpStatus.OK);
            //return new ResponseEntity<User>(new UserDTO(user), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageReason>(new MessageReason("Wrong Token"), HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        User user = userManager.getUser(token);
        if(user != null){
            userManager.deleteUser(user.getEmail());
            return new ResponseEntity<>(new MessageAnswer("Account is deleted"), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new MessageAnswer ("Wrong token."), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping (
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody User updatedUser) {
        User user = userManager.getUser(token);
        if (user != null) {
            boolean isUpdated = userManager.updateUser(updatedUser);
            if (isUpdated) {
                return new ResponseEntity<>(new MessageAnswer("User updated successfully"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageAnswer("User update failed"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new MessageAnswer("Unauthorized access"), HttpStatus.UNAUTHORIZED);
        }
    }

}
