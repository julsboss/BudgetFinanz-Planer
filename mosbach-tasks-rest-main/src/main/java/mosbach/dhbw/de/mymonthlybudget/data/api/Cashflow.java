package mosbach.dhbw.de.mymonthlybudget.data.api;

public interface Cashflow {

        Integer getID();
        String getType();
        String getCategory();
        Double getAmount();
        String getDate();
        String getPaymentMethod();
        String getRepetition();
        String getComment();
}
