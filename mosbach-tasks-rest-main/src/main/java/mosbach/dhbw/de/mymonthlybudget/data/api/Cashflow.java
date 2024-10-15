package mosbach.dhbw.de.mymonthlybudget.data.api;

public interface Cashflow {

        Integer getCashflowID();
        String getType();
        String getCategory();
        Double getAmount();
        String getDate();
        String getPaymentMethod();
        String getRepetition();
        String getComment();
}
