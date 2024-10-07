package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowImpl;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowManagerImpl;
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
            List<Cashflow> myCashflows = new ArrayList<>();
            for(mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow c : propertiesCashflowManager.getAllCashflows())
                myCashflows.add(new mosbach.dhbw.de.mymonthlybudget.model.Cashflow(
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

}
