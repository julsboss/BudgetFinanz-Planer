package mosbach.dhbw.de.mymonthlybudget.data.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
@Entity
public class CashflowImpl implements Cashflow {
    private static int cashflowIDCounter =1;
    @Id
    private int cashflowID;
    private String type;
    private String category;
    private Double amount;
    private String date;
    private String paymentMethod;
    private String repetition;
    private String comments;

    public CashflowImpl(String type, String category, Double amount, String date, String paymentMethod, String repetition, String comments) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comments = comments;
        this.cashflowID = cashflowIDCounter;
        cashflowIDCounter++;
    }

    @Override
    public Integer getID() {
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
}
