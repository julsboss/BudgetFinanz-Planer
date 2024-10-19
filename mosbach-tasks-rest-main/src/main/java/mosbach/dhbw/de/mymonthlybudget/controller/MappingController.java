package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.*;
import mosbach.dhbw.de.mymonthlybudget.data.impl.*;
import mosbach.dhbw.de.mymonthlybudget.dto.MessageReason;
import mosbach.dhbw.de.mymonthlybudget.model.*;
import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {

  //  CashflowManager propertiesCashflowManager = CashflowManagerImpl.getCashflowManagerImpl();
//  AuthService authService = AuthServiceImpl.getAuthServiceImpl();

    @Autowired
            AuthService authService;

  CashflowManager cashflowManager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();
  MonthlyReportManager monthlyReportManager = PostgresDBMonthlyReportManagerImpl.getPostgresDBMonthlyReportManagerImpl();
    public MappingController(
    ) {}
    @Autowired
    private UserManager userManager;

    @GetMapping("/create-cashflow-table")
    public String createDBTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO, "MappingController create-cashflow-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        cashflowManager.createCashflowTable();

        return "ok";
    }
    @GetMapping("/create-monthlyReport-table")
    public String createMonthlyReportTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO, "MappingController create-monthlyReport-table " + token);

        // TODO:  Check token, this should be a very long, super secret token
        // Usually this is done via a different, internal component, not the same component for all public REST access

        monthlyReportManager.createMonthlyReportTable();

        return "ok";
    }

    @PostMapping (
            path = "/cashflow",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createCashflow(@RequestHeader ("Authorization") String token, @RequestBody  CashflowDTO request) {
        final Logger logger = Logger.getLogger("CashflowLogger");
        logger.log(Level.INFO, "Received request to create cashflow with token: " + token);
        User user = userManager.getUser(token);
        if(user != null) {
            logger.log(Level.INFO, "User found for token: " + token);
            CashflowImpl cashflow = new CashflowImpl( request.getType(),
                    request.getCategory(),
                    request.getAmount(),
                    request.getDate(),
                    request.getPaymentMethod(),
                    request.getRepetition(),
                    request.getComment());
                    //cashflow.setUserID(token);
            cashflowManager.addCashflow(cashflow, user.getUserID());
            logger.log(Level.INFO, "Cashflow added successfully for user ID: " + user.getUserID());
            return
                    new ResponseEntity<MessageAnswer>(new MessageAnswer("Cashflow added to your Cashflowlist."), HttpStatus.CREATED);
        }
        else {
            logger.log(Level.WARNING, "Invalid token: " + token);
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong token"), HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/cashflow")
    public CashflowResponse getAllCashflows(
            @RequestParam (value = "sortOrder", defaultValue = "date") String sortOrder,
            @RequestHeader ("Authorization") String token
    ) {
        User user = userManager.getUser(token);
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
            answerCashflow.setSortOrder("ALL CASHFLOWS BY ALL USERS");
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

    @GetMapping("/cashflow/user")
    public ResponseEntity<?> getAllCashflowsByUser(@RequestHeader ("Authorization") String token){
        final Logger logger = Logger.getLogger("CashflowLogger");
        logger.log(Level.INFO, "Received request to create cashflow with token: " + token);
        User user = userManager.getUser(token);
        if(user != null) {
            int userId = user.getUserID();
            List<mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow> cashflows = cashflowManager.getCashflowsByUserID(userId);
            if (cashflows.isEmpty()) {
                return new ResponseEntity<MessageAnswer>(new MessageAnswer("There are no cashflows for this user"), HttpStatus.NOT_FOUND);
            }   List<CashflowDTO> cashflowDTO = new ArrayList<>();
                for(mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow c : cashflows){
                    cashflowDTO.add(new CashflowDTO(c));
                }
            return new ResponseEntity<CashflowResponse>(new CashflowResponse("Cashflows of User: "+ user.getFirstName(), cashflowDTO), HttpStatus.OK);
        }   return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong token"), HttpStatus.UNAUTHORIZED);
    }



    @DeleteMapping("/cashflow/{cashflowId}")
    public ResponseEntity<?> deleteCashflow(
            @PathVariable int cashflowId,
            @RequestHeader("Authorization") String token) {
        Logger logger = Logger.getLogger("CashflowController");
        User user = userManager.getUser(token);
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

    @PutMapping(
            path = "/cashflow/{cashflowID}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> updateCashflow(
            @PathVariable int cashflowID,
            @RequestBody CashflowDTO updatedCashflow,
            @RequestHeader("Authorization") String token
    ) {
        final Logger logger = Logger.getLogger("CashflowLogger");
        logger.log(Level.INFO, "Received request to update cashflow with ID: " + cashflowID);

        User user = userManager.getUser(token);
        if (user == null) {
            return new ResponseEntity<>(new MessageAnswer("Invalid token"), HttpStatus.UNAUTHORIZED);
        }

        mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow existingCashflow = cashflowManager.getCashflowById(cashflowID);
        logger.log(Level.INFO, "Authenticated user ID: " + user.getUserID());
        logger.log(Level.INFO, "Cashflow user ID: " + existingCashflow.getUserID());
        updatedCashflow.setId(cashflowID);
        updatedCashflow.setUserId(user.getUserID());
        if ((existingCashflow.getCashflowID()!= updatedCashflow.getId().intValue()) || (existingCashflow.getUserID() != user.getUserID())) {
            return new ResponseEntity<>(new MessageAnswer("Cashflow not found or unauthorized"), HttpStatus.NOT_FOUND);
        }

        cashflowManager.updateCashflow(new CashflowImpl(updatedCashflow), user.getUserID());

        return new ResponseEntity<>(new MessageAnswer("Cashflow updated successfully"), HttpStatus.OK);
    }

    @GetMapping("/cashflow/{cashflowId}")
    public ResponseEntity<?> getCashflowByCashflowId(
            @PathVariable int cashflowId,
            @RequestHeader("Authorization") String token){
        User user = userManager.getUser(token);
        if (user != null) {
            CashflowDTO cashflow = new CashflowDTO(cashflowManager.getCashflowById(cashflowId));
            if (cashflow != null) {
                return new ResponseEntity<>(cashflow, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new MessageAnswer("Cashflow not found"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new MessageAnswer("Unauthorized access."), HttpStatus.UNAUTHORIZED);
        }

    }


    @PostMapping (
            path = "/monthly-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    ) public ResponseEntity<?> createMonthlyReport(@RequestHeader ("Authorization") String token, @RequestBody MonthlyReportRequest request) {
        User user = userManager.getUser(token);
        if (user != null) {
            MonthlyReportImpl monthlyReport = new MonthlyReportImpl(user.getUserID(), request.getMonth(), request.getYear());
            monthlyReportManager.addMonthlyReport(monthlyReport);

            return new ResponseEntity<MessageAnswer>(new MessageAnswer("MonthlyReport created"), HttpStatus.OK);
        }
        else{
        return new ResponseEntity<MessageReason>(new MessageReason("Wrong token"), HttpStatus.UNAUTHORIZED);
    }
    }
    @GetMapping(
            path = "/monthly-report",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    ) public ResponseEntity <?> getMonthlyReport (@RequestHeader ("Authorization") String token, @RequestBody MonthlyReportRequest request){
        User user = userManager.getUser(token);
        if (user != null) {
            MonthlyReport report = monthlyReportManager.getMonthlyReport(user.getUserID(), request.getMonth(), request.getYear());
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
