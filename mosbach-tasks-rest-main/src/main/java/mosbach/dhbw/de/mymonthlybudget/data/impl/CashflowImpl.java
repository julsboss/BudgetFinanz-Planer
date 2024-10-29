package mosbach.dhbw.de.mymonthlybudget.data.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserService;
import mosbach.dhbw.de.mymonthlybudget.model.CashflowDTO;
import org.springframework.web.bind.annotation.RequestHeader;

@Entity
public class CashflowImpl implements Cashflow {
    @Id
    private int cashflowID;
    private String type;
    private String category;
    private Double amount;
    private String date;
    private String paymentMethod;
    private String repetition;
    private String comments;
    private int user_id;

    public CashflowImpl(String type, String category, Double amount, String date, String paymentMethod, String repetition, String comments) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comments = comments;
    }
    // Optional: Konstruktor mit ID für Datenbankabfragen
    public CashflowImpl(int id, String type, String category, Double amount, String date,
                        String paymentMethod, String repetition, String comments) {
        this.cashflowID = id; // Setze die ID beim Abrufen aus der Datenbank
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comments = comments;

    }
    public CashflowImpl(int id, String type, String category, Double amount, String date,
                        String paymentMethod, String repetition, String comments, int user_id) {
        this.cashflowID = id; // Setze die ID beim Abrufen aus der Datenbank
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comments = comments;
        this.user_id = user_id;

    }
    public CashflowImpl(CashflowDTO cashflowDTO) {
        this.cashflowID = cashflowDTO.getId(); // Setze die ID beim Abrufen aus der Datenbank
        this.type = cashflowDTO.getType();
        this.category = cashflowDTO.getCategory();
        this.amount = cashflowDTO.getAmount();
        this.date = cashflowDTO.getDate();
        this.paymentMethod = cashflowDTO.getPaymentMethod();
        this.repetition = cashflowDTO.getRepetition();
        this.comments = cashflowDTO.getComment();
        this.user_id = cashflowDTO.getUserId();

    }
    public CashflowImpl(int id, String type, String category, Double amount, String date,
                        String paymentMethod, String repetition, String comments, @RequestHeader String token) {
        this.cashflowID = id; // Setze die ID beim Abrufen aus der Datenbank
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comments = comments;
        UserManager userService = PostgresDBUserManagerImpl.getPostgresDBUserManagerImpl();
        User user = userService.getUser(token);
        this.user_id = user.getUserID();

    }



    @Override
    public Integer getCashflowID() {
        return cashflowID;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public Double getAmount() {
        return amount;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String getRepetition() {
        return repetition;
    }

    @Override
    public String getComment() {
        return comments;
    }

    @Override
    public Integer getUserID() {
        return user_id;
    }

    public void setUserID(String token){
        UserManager userService = PostgresDBUserManagerImpl.getPostgresDBUserManagerImpl();
        User user = userService.getUser(token);
        this.user_id = user.getUserID();
    }
}
