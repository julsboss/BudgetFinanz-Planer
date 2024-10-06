package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowImpl;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowManagerImpl;
import mosbach.dhbw.de.mymonthlybudget.model.CashflowRequest;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
import mosbach.dhbw.de.mymonthlybudget.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mosbach.dhbw.de.mymonthlybudget.data.api.CashflowManager;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {

    CashflowManager propertiesCashflowManager = CashflowManagerImpl.getCashflowManagerImpl();
    // private final PersonRepository personRepository;

    public MappingController(
    ) {}
    @Autowired
    private UserService userService;
   

    @PostMapping (
            path = "/cashflow",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public MessageAnswer createCashflow(@RequestHeader ("Authorization") String token, @RequestBody  CashflowRequest request) {

        User user = userService.getUser(token);
        if(user != null) {
            propertiesCashflowManager.addCashflow(new CashflowImpl(
                    request.getCashflow().getType(),
                    request.getCashflow().getCategory(),
                    request.getCashflow().getAmount(),
                    // Double.parseDouble(tokenTask.getTask().getGrade()),
                    request.getCashflow().getDate(),
                    request.getCashflow().getPaymentMethod(),
                    request.getCashflow().getRepetition(),
                    request.getCashflow().getComment()

            ));

            return
                    new MessageAnswer("Cashflow added to your Cashflowlist.");
        }
        else {
            return new MessageAnswer("Wrong Credentials");
        }

    }
    /*public ResponseEntity<?> createCashflow(@RequestHeader("Authorization") String token,@RequestBody CashflowRequest request) {
        // Logik zur Verarbeitung der Transaktion
        System.out.println(request);
        return ResponseEntity.ok("Transaktion erfolgreich erstellt");
    }*/
}
