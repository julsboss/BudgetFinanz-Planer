package mosbach.dhbw.de.mymonthlybudget.controller;

import mosbach.dhbw.de.mymonthlybudget.model.CashflowRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MappingController {

    // private final PersonRepository personRepository;

    public MappingController(
    ) {}

    @GetMapping ("/auth")
    public String getAuth() {
        return "I am alive.";
    }

    @PostMapping (
            path = "/cashflow",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createCashflow(@RequestBody CashflowRequest request) {
        // Logik zur Verarbeitung der Transaktion
        System.out.println(request);
        return ResponseEntity.ok("Transaktion erfolgreich erstellt");
    }
}
