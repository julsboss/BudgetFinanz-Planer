package mosbach.dhbw.de.mymonthlybudget.controller;


import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;
import mosbach.dhbw.de.mymonthlybudget.model.MessageReason;
import mosbach.dhbw.de.mymonthlybudget.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.model.AuthMessage;
import mosbach.dhbw.de.mymonthlybudget.model.MessageToken;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
import mosbach.dhbw.de.mymonthlybudget.data.impl.User;

import java.util.logging.Level;
import java.util.logging.Logger;


@CrossOrigin(origins = "https://BudgetFrontend-triumphant-panda-iv.apps.01.cf.eu01.stackit.cloud", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @Autowired
    private UserManager userManager;



    @GetMapping("/create-user-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO,"MappingController create-user-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        userManager.createUserTable();

        return "ok";
    }


    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signIn(@RequestBody AuthMessage authMessage) {
        final Logger logger = Logger.getLogger("AuthLogger");

        User user = userManager.getUserByEmail(authMessage.getEmail());
        if (user != null) {
            // Log the passwords for debugging
            logger.log(Level.INFO, "Input password: " + authMessage.getPassword());
            logger.log(Level.INFO, "Stored (hashed) password: " + user.getPassword());

            if (user.checkPassword(authMessage.getPassword())) {
                return new ResponseEntity<>(new MessageToken(authService.generateToken(user)), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageAnswer("Wrong password"), HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(new MessageAnswer("User not found"), HttpStatus.UNAUTHORIZED);
        }
    }


    @DeleteMapping
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String token) {
        if(authService.isTokenExpired(token)) return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong token"), HttpStatus.UNAUTHORIZED);
        authService.invalidDateToken(token);
        return new ResponseEntity<MessageAnswer>(new MessageAnswer("Logout successful"), HttpStatus.OK);
    }


    @PostMapping(
            path = "/sign-up",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> signUp(@RequestBody UserDTO userRequest) {
        User user;
        if(userManager.getUserByEmail(userRequest.getEmail()) == null){
           user = new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword());
            userManager.addUser(user);
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Account created"), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageReason>(new MessageReason("Account already exists"), HttpStatus.BAD_REQUEST);
        }
    }






}