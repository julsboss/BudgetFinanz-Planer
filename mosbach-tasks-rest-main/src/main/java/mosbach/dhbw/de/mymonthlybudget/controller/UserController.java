package mosbach.dhbw.de.mymonthlybudget.controller;


import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.dto.MessageReason;
import mosbach.dhbw.de.mymonthlybudget.dto.UserDTO;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://api.system.01.cf.eu01.stackit.cloud/", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token){
        User user = userService.getUser(token);
        if(user != null){
            return new ResponseEntity<User>((MultiValueMap<String, String>) new UserDTO(user), HttpStatus.OK);
            //return new ResponseEntity<User>(new UserDTO(user), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageReason>(new MessageReason("Wrong Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
}
