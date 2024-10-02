package mosbach.dhbw.de.mymonthlybudget.data.api;

public interface Cashflow {

        String getType();
        String getCategory();
        Double getAmount();
        String getDate();
        String getPaymentMethod();
        String getRepition();
        String getComment();
}
