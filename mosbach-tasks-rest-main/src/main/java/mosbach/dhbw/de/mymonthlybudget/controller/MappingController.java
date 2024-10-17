package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.MonthlyReportManager;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.data.impl.*;
import mosbach.dhbw.de.mymonthlybudget.dto.MessageReason;
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
  MonthlyReportManager monthlyReportManager = PostgresDBMonthlyReportManagerImpl.getPostgresDBMonthlyReportManagerImpl();
    public MappingController(
    ) {}
    @Autowired
    private UserService userService;

    @GetMapping("/create-cashflow-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO,"MappingController create-cashflow-table " + token);

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
            List<CashflowDTO> myCashflowDTOS = new ArrayList<>();
            for(mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow c : cashflowManager.getAllCashflows())
                myCashflowDTOS.add(new CashflowDTO(
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

            answerCashflow.setCashflow(myCashflowDTOS);
            return
                    answerCashflow;
        }
     else{ return null;

    }

    }
    @GetMapping("/cashflow/{userId}")
    public ResponseEntity<?> getCashflowsByUserId(@PathVariable int userId) {
        List<mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow> cashflows = cashflowManager.getCashflowsByUserID(userId);
        if (cashflows.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cashflows, HttpStatus.OK);
    }


    @DeleteMapping("/cashflow/{cashflowId}")
    public ResponseEntity<?> deleteCashflow(
            @PathVariable int cashflowId,
            @RequestHeader("Authorization") String token) {
        Logger logger = Logger.getLogger("CashflowController");
        User user = userService.getUser(token);
        if (user != null) {
            logger.info("User authenticated. Attempting to delete cashflow with ID: " + cashflowId);
            boolean removed = cashflowManager.removeCashflow(cashflowId);
            if (removed) {
                logger.info("Cashflow successfully deleted.");
                return ResponseEntity.ok("Cashflow successfully deleted.");
            } else {
                logger.warning("Cashflow not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashflow not found.");
            }
        } else {
            logger.warning("Unauthorized access attempt.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
        }
    }

    @PostMapping (
            path = "/monthly-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    ) public ResponseEntity<?> createMonthlyReport(@RequestHeader ("Authorization") String token, @RequestBody MonthlyReport report) {
        User user = userService.getUser(token);
        if (user != null) {
            MonthlyReportImpl monthlyReport = new MonthlyReportImpl(token, report.getMonth(), report.getYear());
            monthlyReportManager.addMonthlyReport(monthlyReport);

            return new ResponseEntity<MessageAnswer>(new MessageAnswer("MonthlyReport created"), HttpStatus.OK);
        }
        else{
        return new ResponseEntity<MessageReason>(new MessageReason("Wrong Credentials"), HttpStatus.UNAUTHORIZED);
    }
    }
    @GetMapping(
            path = "/monthly-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    ) public ResponseEntity <?> getMonthlyReport (@RequestHeader ("Authorization") String token, @RequestParam String month, @RequestParam Integer year){
        User user = userService.getUser(token);
        if (user != null) {
            MonthlyReport report = monthlyReportManager.getMonthlyReport(token, month, year);
            if (report == null)
                return new ResponseEntity<MessageReason>(new MessageReason("Monthly Report not found."), HttpStatus.NOT_FOUND);
            return new ResponseEntity<MonthlyReport>(report, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageReason>(new MessageReason("Wrong Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }


    //TODO: ALEXA implementieren

}
