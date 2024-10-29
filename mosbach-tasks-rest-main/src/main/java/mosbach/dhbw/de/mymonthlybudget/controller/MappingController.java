package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.api.*;
import mosbach.dhbw.de.mymonthlybudget.data.impl.*;
import mosbach.dhbw.de.mymonthlybudget.model.*;
import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;
import mosbach.dhbw.de.mymonthlybudget.model.alexa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@CrossOrigin(origins = {"https://BudgetFrontend-triumphant-panda-iv.apps.01.cf.eu01.stackit.cloud", "*"}, allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {



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


        cashflowManager.createCashflowTable();

        return "ok";
    }
    @GetMapping("/create-monthlyReport-table")
    public String createMonthlyReportTable(@RequestParam(value = "token", defaultValue = "no-token") String token) {
        Logger.getLogger("MappingController")
                .log(Level.INFO, "MappingController create-monthlyReport-table " + token);

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
               // return ResponseEntity.ok("Cashflow successfully deleted.");
               return new ResponseEntity<MessageAnswer>(new MessageAnswer("Cashflow successfully deleted"), HttpStatus.OK);
            } else {
                logger.warning("Cashflow not found.");
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cashflow not found.");
                return new ResponseEntity<MessageAnswer>(new MessageAnswer("Cashflow not found"), HttpStatus.NOT_FOUND);
            }
        } else {
            logger.warning("Unauthorized access attempt.");
           // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access.");
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong token"), HttpStatus.UNAUTHORIZED);
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
        return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong token"), HttpStatus.UNAUTHORIZED);
    }
    }
    @GetMapping(
            path = "/monthly-report",
            produces = MediaType.APPLICATION_JSON_VALUE
    ) public ResponseEntity <?> getMonthlyReport (@RequestHeader ("Authorization") String token, @RequestParam String month, @RequestParam int year){
        final Logger logger = Logger.getLogger("MonthlyReportLogger");
        logger.log(Level.INFO, "Received request for monthly report: month={}, year={}" , month);
        User user = userManager.getUser(token);
        if (user != null) {
            logger.log(Level.INFO, "User found: {}", user.getUserID());
            MonthlyReport report = monthlyReportManager.getMonthlyReport(user.getUserID(), month, year);
            if (report == null)
                return new ResponseEntity<MessageAnswer>(new MessageAnswer("Monthly Report not found."), HttpStatus.NOT_FOUND);
            return new ResponseEntity<MonthlyReport>(report, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<MessageAnswer>(new MessageAnswer("Wrong Credentials"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "/statistik", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getStatistikReport(@RequestHeader("Authorization") String token, @RequestParam int year) {
        User user = userManager.getUser(token);
        if (user != null) {
            List<StatistikDTO> statistikList = monthlyReportManager.getStatistikByYear(user.getUserID(), year);
            if(statistikList.isEmpty()){
                return new ResponseEntity<>(new MessageAnswer("No statistic available for this year"),HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new StatistikResponse(statistikList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new MessageAnswer("Wrong Token"), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping(
            path = "/alexa",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public AlexaRO financialPlannerAlexaController(@RequestBody AlexaRO alexaRO) {

        final Logger logger = Logger.getLogger("AlexaLogger");
        StringBuilder text = new StringBuilder();

        if (alexaRO.getRequest().getType().equalsIgnoreCase("LaunchRequest")) {
            text.append("Willkommen im My Monthly Budget Finanzplaner. Um dich anzumelden, sprich zuerst das Stichwort 'Login',und nenne anschließend deine User ID und dein Passwort jeweils mit den Stichwörtern davor.");
        } else if (alexaRO.getRequest().getType().equals("IntentRequest")) {

            // Check if user is logged in
            if (alexaRO.getSession().getAttributes().isEmpty() && !alexaRO.getRequest().getIntent().getName().equals("Login")) {
                text.append("Bitte melde dich zuerst mit dem Stichwort 'Login', gefolgt von deiner Benutzer-ID und deinem Passwort an.");
            } else if (alexaRO.getRequest().getIntent().getName().equals("Login")) {

                // Extract email and password from slots
                int id = Integer.parseInt(alexaRO.getRequest().getIntent().getSlotsRO().getId().getValue());
                String rawPassword = alexaRO.getRequest().getIntent().getSlotsRO().getPassword().getValue();
                String processedPassword = processPassword(rawPassword);

                // Fetch user by email
                User user = userManager.getUserByID(id);
                if (user != null) {
                    logger.log(Level.INFO, "User ID" + user.getUserID());
                    logger.log(Level.INFO, "User Email" + user.getEmail());
                    logger.log(Level.INFO, "Input password: " + processedPassword);
                    logger.log(Level.INFO, "Stored (hashed) password: " + user.getPassword());

                    // Validate password
                    if (user.checkPassword(processedPassword)) {
                        alexaRO.getSession().setAttributes(null);
                        HashMap<String, String> credentials = new HashMap<>();
                        credentials.put(
                                "id",
                                alexaRO.getRequest().getIntent().getSlotsRO().getId().getValue()
                        );
                        credentials.put(
                                "password",
                                alexaRO.getRequest().getIntent().getSlotsRO().getPassword().getValue()
                        );
                        SessionRO session = new SessionRO();
                        session.setAttributes(credentials);
                        alexaRO.setSessionAttributes(credentials);
                        text.append("Die Anmeldung war erfolgreich. Du bist nun angemeldet.");
                    } else {
                        text.append("Anmeldung fehlgeschlagen. Bitte überprüfe dein Passwort. Bitte melde dich zuerst mit dem Stichwort 'Login', gefolgt von deiner Benutzer-ID und deinem Passwort an.");
                    }
                }else{
                        text.append("Benutzer nicht gefunden. Bitte überprüfe deine UserID in deiner Profilverwaltung.");
                    }
            }
            else if (!alexaRO.getSession().getAttributes().isEmpty()) {

                if (alexaRO.getRequest().getIntent().getName().equals("monatsberichtabfrage")) {
                    SlotsRO slots = alexaRO.getRequest().getIntent().getSlotsRO();
                    if (slots != null && slots.getMonth() != null && slots.getYear() != null) {
                        String month = slots.getMonth().getValue();
                        int year = Integer.parseInt(slots.getYear().getValue());

                        logger.log(Level.INFO, "Retrieving report for Month: " + month + ", Year: " + year);

                        text.append(getMonthlyReportAlexa(alexaRO, month, year));
                    } else {
                        text.append("Fehler: Monat oder Jahr wurde nicht korrekt angegeben.");
                    }
                }if (alexaRO.getRequest().getIntent().getName().equals("Logout")) {
                    alexaRO.getSession().setAttributes(null);
                    text.append("Du wurdest erfolgreich abgemeldet. ");
                    return prepareResponse(alexaRO, text.toString(), true);
                }
                }

        } else {
            text.append("Diese Funktion des Monthly Budget Finanzplaners existiert nicht.");
        }

        return prepareResponse(alexaRO, text.toString(), false);
    }


    private String getMonthlyReportAlexa(AlexaRO alexaRO, String month, int year) {
        User user = userManager.getUserByID(Integer.parseInt(alexaRO.getSession().getAttributes().get("id")));
        if(user != null){
            MonthlyReport monthlyReport = monthlyReportManager.getMonthlyReport(user.getUserID(), month,  year);
            double differenceSummary = monthlyReport.getDifferenceSummary();
            BigDecimal roundedDifference = new BigDecimal(differenceSummary).setScale(2, RoundingMode.HALF_UP);
            String answer = "Dein Monatsbericht für den Monat " + month + " und das Jahr "+ year +
                    " lautet: Dein Gesamteinkommen für den Monat sind: "+ monthlyReport.getIncomeTotal() +
                    " Euro , Deine gesamten Fixen Kosten für den Monat sind: " + monthlyReport.getFixedTotal() +
                    " Euro , Deine gesamten variablen Kosten für den Monat sind: " + monthlyReport.getVariableTotal() +
                    " Euro , Deine gesamten Ausgaben für den Monat sind: " + monthlyReport.getExpensesTotal() +
                    " Euro, deine Differenz für den Monat beträgt: " + roundedDifference + " Euro. " ;
                return answer;
        }



        return String.format("Leider konnte ich keinen Monatsbericht für den Monat " + month +" und das Jahr "+ year +
                "finden. Gehe in die Webseite und lege den Monatsbericht an oder wähle einen anderen Monat und ein anderes Jahr.");
    }

    private AlexaRO prepareResponse(AlexaRO alexaRO, String outText, boolean shouldEndSession) {
        alexaRO.setRequest(null);
        alexaRO.setSession(null);
        alexaRO.setContext(null);

        OutputSpeechRO outputSpeechRO = new OutputSpeechRO();
        outputSpeechRO.setType("PlainText");
        outputSpeechRO.setText(outText);

        ResponseRO response = new ResponseRO(outputSpeechRO, shouldEndSession);
        alexaRO.setResponse(response);

        return alexaRO;
    }
    public String processPassword(String password) {
        if (password == null) {
            return "";
        }
        // Remove dots from the password
        return password.replace(".", "");
    }



}
