package mosbach.dhbw.de.mymonthlybudget.data.api;

import java.util.List;

public interface CashflowManager {

    void addCashflow(Cashflow newCashflow, int userid);

    List<Cashflow> getAllCashflows();

    boolean removeCashflow( int cashflowID );

    void createCashflowTable();

    List<Cashflow> getCashflowsByUser (String token);

    List<Cashflow> getCashflowsByUserID(int user_ID);

    void updateCashflow(Cashflow cashflow, int user_ID);

    Cashflow getCashflowById(int cashflowID);

}
