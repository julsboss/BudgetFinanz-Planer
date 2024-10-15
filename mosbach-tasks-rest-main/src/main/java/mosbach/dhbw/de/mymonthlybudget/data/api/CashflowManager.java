package mosbach.dhbw.de.mymonthlybudget.data.api;

import java.util.List;

public interface CashflowManager {

    void addCashflow(Cashflow newCashflow);

    List<Cashflow> getAllCashflows();

    boolean removeCashflow( int cashflowID );

    void createCashflowTable();

    List<Cashflow> getCashflowsByUser (String token);
}
