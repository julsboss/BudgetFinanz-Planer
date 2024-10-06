package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowImpl;
import mosbach.dhbw.de.mymonthlybudget.data.impl.CashflowManagerImpl;
import mosbach.dhbw.de.mymonthlybudget.model.CashflowRequest;
import mosbach.dhbw.de.mymonthlybudget.model.MessageAnswer;
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

    @PostMapping (
            path = "/cashflow",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public MessageAnswer createTasks(@RequestBody  CashflowRequest request) {

        String token = request.getToken();
        if (token != null && token.startsWith("Authorization")) {
            token = token.substring(7);
            // Verwenden Sie den Token hier
        }
        //TODO check the token
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


        // Double gradeDouble = Double.parseDouble(tokenTask.getTask().getGrade());
        // String answer = "You were a bit lazy.";
        // if (gradeDouble < 2.3) answer = "OK, you have learned";
        return
                new MessageAnswer("Task added to your tasklist.");
    }
    /*public ResponseEntity<?> createCashflow(@RequestHeader("Authorization") String token,@RequestBody CashflowRequest request) {
        // Logik zur Verarbeitung der Transaktion
        System.out.println(request);
        return ResponseEntity.ok("Transaktion erfolgreich erstellt");
    }*/
}
