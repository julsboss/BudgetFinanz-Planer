package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowImpl;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowManagerImpl;
import mosbach.dhbw.de.mymonthlybudget.data.impl.PostgresDBCashflowManagerImpl;
import mosbach.dhbw.de.mymonthlybudget.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mosbach.dhbw.de.mymonthlybudget.data.api.CashflowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {

  //  CashflowManager propertiesCashflowManager = CashflowManagerImpl.getCashflowManagerImpl();
    // private final PersonRepository personRepository;

    //TODO: when ready for using database manager, switch to:
  CashflowManager cashflowManager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();

    public MappingController(
    ) {}
    @Autowired
    private UserService userService;

    @GetMapping("/create-task-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO,"MappingController create-task-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        cashflowManager.createCashflowTable();

        return "ok";
    }

    @PostMapping (
            path = "/cashflow",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public MessageAnswer createCashflow(@RequestHeader ("Authorization") String token, @RequestBody  CashflowRequest request) {

        User user = userService.getUser(token);
        if(user != null) {
            cashflowManager.addCashflow(new CashflowImpl(
                 //   request.getCashflow().getId(), //muss drüber geschaut werden
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
   /* wenn datenbank erfolgreich implementiert
    @GetMapping("/create-task-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO,"MappingController create-task-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        cashflowManager.createTaskTable();

        return "ok";
    }*/
    @GetMapping("/cashflow")
    public CashflowResponse getAllCashflows(
            @RequestParam (value = "sortOrder", defaultValue = "date") String sortOrder,
            @RequestHeader ("Authorization") String token
    ) {
        User user = userService.getUser(token);
        if(user != null){
            Logger
                    .getLogger("MappingController")
                    .log(Level.INFO, "Get-Call-Ausführung");
            CashflowResponse answerCashflow = new CashflowResponse();
            List<mosbach.dhbw.de.mymonthlybudget.model.Cashflow> myCashflows = new ArrayList<>();
            for(mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow c : cashflowManager.getAllCashflows())
                myCashflows.add(new mosbach.dhbw.de.mymonthlybudget.model.Cashflow(
                        c.getCashflowID(),
                        c.getType(),
                        c.getCategory(),
                        c.getAmount(),
                        c.getDate(),
                        c.getPaymentMethod(),
                        c.getRepetition(),
                        c.getComment()

                ));
            answerCashflow.setSortOrder("NOT YET SORTED");
            Logger
                    .getLogger("MappingController")
                    .log(Level.INFO, "Cashflows from file");

            answerCashflow.setCashflow( myCashflows);
            return
                    answerCashflow;
        }
     else{ return null;

    }

    }
    @DeleteMapping("/cashflow/{cashflowId}")
    public ResponseEntity<?> deleteCashflow(
            @PathVariable int cashflowId,
            @RequestHeader("Authorization") String token) {
        User user = userService.getUser(token);
        if (user != null) {
            boolean removed = cashflowManager.removeCashflow(cashflowId);
            if (removed) {
                return ResponseEntity.ok("Cashflow successfully deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashflow not found.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
    }
    //TODO: ALEXA implementieren

}
