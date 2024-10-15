package mosbach.dhbw.de.mymonthlybudget.data.api;

import java.util.List;

public interface MonthlyReport {

    Integer getMonthlyReportID();



    //void setMonthlyReportID(Integer monthlyReportID) ;

   String getMonth() ;

   //void setMonth(String month);

   Integer getYear() ;

   //void setYear(Integer year);

   Integer getUserID();

   //void setUserID(Integer userID);

   Double getIncomeTotal();

   //void setIncomeTotal(Double incomeTotal);

   Double getFixedTotal();

  // void setFixedTotal(Double fixedTotal);

   Double getVariableTotal();

  // void setVariableTotal(Double variableTotal);

   Double getExpensesTotal();

  // void setExpensesTotal(Double expensesTotal);

   Double getDifferenceSummary() ;
   //void setDifferenceSummary(Double differenceSummary);

   List<mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow> getCashflowsIncome();

   //void setCashflowsIncome(List<Cashflow> cashflowsIncome);

    List<mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow> getCashflowsFixedCosts() ;

   //  void setCashflowsFixedCosts(List<Cashflow> cashflowsFixedCosts);

    List<Cashflow> getCashflowsVariableCosts();


   // void setCashflowsVariableCosts(List<Cashflow> cashflowsVariableCosts);
}
