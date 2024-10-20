package mosbach.dhbw.de.mymonthlybudget.controller;


import mosbach.dhbw.de.mymonthlybudget.data.api.VerificationService;
import mosbach.dhbw.de.mymonthlybudget.dto.*;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mosbach.dhbw.de.mymonthlybudget.data.api.AuthService;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@CrossOrigin(origins = "https://BudgetBackend-active-lemur-qg.apps.01.cf.eu01.stackit.cloud", allowedHeaders = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    //UserService userService = PostgresDBUserServiceImpl.getUserServiceImpl();


    @GetMapping("/create-user-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("AuthContoller")
                .log(Level.INFO, "AuthContoller create-user-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        userService.createUserTable();

        return "ok";
    }


    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signIn(@RequestBody AuthMessage authMessage) {
        User user = userService.getUserByEmail(authMessage.getEmail());
        if (user != null) {
            if (user.isVerified() == false) {
                return new ResponseEntity<MessageAnswer>(new MessageAnswer("User not verified"), HttpStatus.UNAUTHORIZED);
            }
            if (user.checkPassword(authMessage.getPassword())) return new ResponseEntity<MessageToken>(
                    new MessageToken(authService.generateToken(user)), HttpStatus.OK);
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong credentials"), HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("User not found"), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping(path = "/sign-up", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> signUp(@RequestBody UserDTO userRequest) {
        User user;
        if (userService.getUserByEmail(userRequest.getEmail()) == null) {
            if (userRequest.getPat() != null)
                user = new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword(), userRequest.getPat());
            else
                user = new User(userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPassword());
            userService.addUser(user);
            String verificationToken = authService.generateVerificationToken(user);
            verificationService.sendVerificationEmail(user.getEmail(), "file:///C:/Users/mkm/OneDrive%20-%20TecAlliance/Wirtschaftsinformatik/Webprogrammierung%20Semester%203/BudgetFinanz-Planer/mosbach-tasks-web-main/public/LoginPage.html?token=" + verificationToken);
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Account created"), HttpStatus.OK);
        } else {
            return new ResponseEntity<MessageReason>(new MessageReason("Mail already exists"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path = "/allUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllUsers() {
        Logger logger = Logger.getLogger("UserController");
        logger.log(Level.INFO, "Get-Call für alle Benutzer gestartet");

        // Abrufen aller Benutzer aus der Datenbank
        List<User> dbUsers = userService.getAllUsers();

        if (dbUsers.isEmpty()) {
            // Wenn keine Benutzer gefunden wurden
            logger.log(Level.WARNING, "Keine Benutzer gefunden");
            return new ResponseEntity<MessageReason>(new MessageReason("No users found"), HttpStatus.NOT_FOUND);
        } else {
            logger.log(Level.INFO, "Anzahl der gefundenen Benutzer: " + dbUsers.size());

            // Konvertierung der Benutzer in DTOs
            List<UserDTO> userDTOList = dbUsers.stream()
                    .map(user -> new UserDTO(
                            user.getUserID(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getEmail(),
                            user.getPassword()
                    ))
                    .collect(Collectors.toList());


            logger.log(Level.INFO, "Benutzer erfolgreich abgerufen und sortiert");
            return new ResponseEntity<List<UserDTO>>(userDTOList, HttpStatus.OK);
        }
    }

/*
    @GetMapping("/allUsers")
    public List<UserDTO> getAllUsers(
            @RequestParam (value = "sortOrder", defaultValue = "date") String sortOrder
    )
    {
            Logger
                    .getLogger("AuthController")
                    .log(Level.INFO, "Get-Call-Ausführung");

            List<UserDTO> myUsers = new ArrayList<>();
            var dbUsers = userService.getAllUsers();

        Logger
                .getLogger("AuthController")
                .log(Level.INFO, "Users count:" + dbUsers.size());

            for(mosbach.dhbw.de.mymonthlybudget.model.User c : dbUsers)
                myUsers.add(new mosbach.dhbw.de.mymonthlybudget.dto.UserDTO(
                        c.getUserID(),
                        c.getFirstName(),
                        c.getLastName(),
                        c.getEmail(),
                        c.getPassword()

                ));
        Logger.getLogger("AuthController")
                .log(Level.INFO,"ALL Users from file: {0} ");



        return myUsers;

    }

*/


    @DeleteMapping
    public ResponseEntity<?> signOut(@RequestHeader("Authorization") String token) {
        if(authService.isTokenExpired(token)) return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong credentials"), HttpStatus.UNAUTHORIZED);
        authService.invalidDateToken(token);
        return new ResponseEntity<MessageAnswer>(new MessageAnswer("Logout successful"), HttpStatus.OK);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token){
        System.out.println(token);
        if(!authService.isTokenExpired(token)){
            System.out.println(token);
            String newtoken = authService.generateToken(userService.getUser(token));
            return new ResponseEntity<MessageToken>(new MessageToken(newtoken),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong credentials"), HttpStatus.UNAUTHORIZED);
        }
    }
//Diese Methode wird aufgerufen wenn der Benutzer einen Bestätigungslink per E--Mail erhalten hat,
// der einen Verifizierungs-Token enthält.
    @PostMapping("/validate-email/{validateToken}")
    public ResponseEntity<?> validateEmail(@PathVariable String validateToken){
        try {
            if (!authService.isTokenExpired(validateToken)) {
                userService.getUserByEmail(authService.extractEmail(validateToken)).setVerified(true);;
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageAnswer("Wrong credentials"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageAnswer("Internal Server Error" + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




}