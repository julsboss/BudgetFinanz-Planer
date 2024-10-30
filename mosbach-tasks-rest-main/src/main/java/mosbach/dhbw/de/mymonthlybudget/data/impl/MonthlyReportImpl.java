package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import mosbach.dhbw.de.mymonthlybudget.data.api.MonthlyReport;
import mosbach.dhbw.de.mymonthlybudget.data.api.UserManager;


import java.util.List;

public class MonthlyReportImpl implements MonthlyReport {

    private Integer monthlyReportID;
    private String month;
    private Integer year;
    private Integer userID;
    private Double incomeTotal;
    private Double fixedTotal;
    private Double variableTotal;
    private Double expensesTotal;
    private Double differenceSummary;
    private List <Cashflow> cashflowsIncome;
    private List <Cashflow> cashflowsFixedCosts;
    private List <Cashflow> cashflowsVariableCosts;



    public MonthlyReportImpl(Integer monthlyReportID, String month, Integer year, Integer userID, Double incomeTotal, Double fixedTotal, Double variableTotal, Double expensesTotal, Double differenceSummary, List<Cashflow> cashflowsIncome, List<Cashflow> cashflowsFixedCosts, List<Cashflow> cashflowsVariableCosts) {
        this.monthlyReportID = monthlyReportID;
        this.month = month;
        this.year = year;
        this.userID = userID;
        this.incomeTotal = incomeTotal;
        this.fixedTotal = fixedTotal;
        this.variableTotal = variableTotal;
        this.expensesTotal = expensesTotal;
        this.differenceSummary = differenceSummary;
        this.cashflowsIncome = cashflowsIncome;
        this.cashflowsFixedCosts = cashflowsFixedCosts;
        this.cashflowsVariableCosts = cashflowsVariableCosts;
    }

    public MonthlyReportImpl(int userID, String month, Integer year) {
        /*UserService userService = new UserServiceImpl();
        User user = userService.getUser(token);*/
        this.userID = userID;
        this.month = month;
        this.year = year;

        PostgresDBCashflowManagerImpl manager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();

        // Retrieve cashflows
        this.cashflowsIncome = manager.getCashflowByMonthAndType(userID, month, year, "Einkommen");
        this.cashflowsFixedCosts = manager.getCashflowByMonthAndType(userID, month, year, "Fixe Kosten");
        this.cashflowsVariableCosts = manager.getCashflowByMonthAndType(userID, month, year, "Variable Kosten");

        // Calculate totals
        setIncomeTotal(cashflowsIncome);
        setFixedTotal(cashflowsFixedCosts);
        setVariableTotal(cashflowsVariableCosts);
        setExpensesTotal();
        setDifferenceSummary();
    }

    public MonthlyReportImpl(String month, Integer year) {
        this.month = month;
        this.year = year;
    }


    @Override
    public Integer getMonthlyReportID() {
        return monthlyReportID;
    }

    @Override
    public String getMonth() {
        return month;
    }

    @Override
    public Integer getYear() {
        return year;
    }

    @Override
    public Integer getUserID() {
        return userID;
    }
   public void setUserID(String token){
       UserManager userService = PostgresDBUserManagerImpl.getPostgresDBUserManagerImpl();
       User user = userService.getUser(token);
       this.userID = user.getUserID();
   }

    @Override
    public Double getIncomeTotal() {
        return incomeTotal;
    }
   public void setIncomeTotal(List<Cashflow> incomeCashflows){
        double total = 0;
        for(Cashflow c : incomeCashflows){
            total = total + c.getAmount();
        }
        this.incomeTotal = total;
   }
    @Override
    public Double getFixedTotal() {
        return fixedTotal;
    }
    public void setFixedTotal(List<Cashflow> fixedCashflows){
        double total = 0;
        for(Cashflow c : fixedCashflows){
            total = total + c.getAmount();
        }
        this.fixedTotal = total;
    }
    @Override
    public Double getVariableTotal() {
        return variableTotal;
    }
    public void setVariableTotal(List<Cashflow> variableCashflows){
        double total = 0;
        for(Cashflow c : variableCashflows){
            total = total + c.getAmount();
        }
        this.variableTotal = total;
    }

    @Override
    public Double getExpensesTotal() {
        return expensesTotal;
    }
    public void setExpensesTotal(){
        this.expensesTotal = variableTotal + fixedTotal;
    }
    @Override
    public Double getDifferenceSummary() {
        return differenceSummary;
    }

    public void setDifferenceSummary(){
        this.differenceSummary = incomeTotal - expensesTotal;
    }

    @Override
    public List<Cashflow> getCashflowsIncome() {
        return cashflowsIncome;
    }
    public void setCashflowsIncome(int userID, String month, int year){
        PostgresDBCashflowManagerImpl manager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();
        this.cashflowsIncome = manager.getCashflowByMonthAndType(userID, month, year, "Einkommen");
    }

    @Override
    public List<Cashflow> getCashflowsFixedCosts() {
        return cashflowsFixedCosts;
    }
    public void setCashflowsFixedCosts(int userID, String month, int year){
        PostgresDBCashflowManagerImpl manager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();
        this.cashflowsFixedCosts = manager.getCashflowByMonthAndType(userID, month, year, "Fixe Kosten");
    }


    @Override
    public List<Cashflow> getCashflowsVariableCosts() {
        return cashflowsVariableCosts;
    }


    public void setCashflowsVariableCosts(int userID, String month, int year){
        PostgresDBCashflowManagerImpl manager = PostgresDBCashflowManagerImpl.getCashflowManagerImpl();
        this.cashflowsVariableCosts = manager.getCashflowByMonthAndType(userID, month, year, "Variable Kosten");
    }
}

