package mosbach.dhbw.de.mymonthlybudget.controller;


import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;
import mosbach.dhbw.de.mymonthlybudget.model.MessageReason;
import mosbach.dhbw.de.mymonthlybudget.model.UserDTO;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
import mosbach.dhbw.de.mymonthlybudget.data.impl.User;
import mosbach.dhbw.de.mymonthlybudget.model.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://BudgetFrontend-triumphant-panda-iv.apps.01.cf.eu01.stackit.cloud", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token){
        User user = userManager.getUser(token);
        if(user != null){
           // UserDTO userDTO = new UserDTO(user);
           // userDTO.setUserID(user.getUserID());

            return new ResponseEntity<UserResponse>(new UserResponse(user), HttpStatus.OK);
            //return new ResponseEntity<User>(new UserDTO(user), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageReason>(new MessageReason("Wrong Token"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable int id) {
        User user = userManager.getUserByID(id);
        if (user != null) {
            // Convert User to UserResponse or UserDTO if needed
            return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageReason("User not found"), HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        User user = userManager.getUser(token);
        if(user != null){
            userManager.deleteUser(user.getUserID());
            return new ResponseEntity<>(new MessageAnswer("Account is deleted"), HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new MessageAnswer ("Wrong token."), HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping (
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UserDTO updatedUser) {
        User user = userManager.getUser(token);
        if (user != null) {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            boolean isUpdated = userManager.updateUser(user);
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
