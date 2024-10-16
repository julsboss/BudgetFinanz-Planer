
package mosbach.dhbw.de.mymonthlybudget.model;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import mosbach.dhbw.de.mymonthlybudget.data.impl.MonthlyReportImpl;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class MonthlyReport {

    @JsonProperty("monthly-report_ID")
    private Integer monthlyReportID;
    @JsonProperty("month")
    private String month;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("user_ID")
    private Integer userID;
    @JsonProperty("income_total")
    private Double incomeTotal;
    @JsonProperty("fixed_total")
    private Double fixedTotal;
    @JsonProperty("variable_total")
    private Double variableTotal;
    @JsonProperty("expenses_total")
    private Double expensesTotal;
    @JsonProperty("difference_summary")
    private Double differenceSummary;
    @JsonProperty("cashflows_income")
    private List<Cashflow> cashflowsIncome;
    @JsonProperty("cashflows_fixed-costs")
    private List<Cashflow> cashflowsFixedCosts;
    @JsonProperty("cashflows_variable-costs")
    private List<Cashflow> cashflowsVariableCosts;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public MonthlyReport() {
    }
    public MonthlyReport (MonthlyReportImpl monthlyReport){
        this.monthlyReportID = monthlyReport.getMonthlyReportID();
        this.month = monthlyReport.getMonth();
        this.year = monthlyReport.getYear();
        this.userID = monthlyReport.getUserID();
        this.incomeTotal = monthlyReport.getIncomeTotal();
        this.fixedTotal = monthlyReport.getFixedTotal();
        this.variableTotal = monthlyReport.getVariableTotal();
        this.expensesTotal = monthlyReport.getExpensesTotal();
        this.differenceSummary = monthlyReport.getDifferenceSummary();
        this.cashflowsIncome = monthlyReport.getCashflowsIncome();
        this.cashflowsFixedCosts = monthlyReport.getCashflowsFixedCosts();
        this.cashflowsVariableCosts = monthlyReport.getCashflowsVariableCosts();
    }
    public MonthlyReport(Integer monthlyReportID, String month, Integer year, Integer userID, Double incomeTotal, Double fixedTotal, Double variableTotal, Double expensesTotal, Double differenceSummary, List<Cashflow> cashflowsIncome, List<Cashflow> cashflowsFixedCosts, List<Cashflow> cashflowsVariableCosts) {
        super();
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

    public MonthlyReport( String month, Integer year) {
        super();
       // this.monthlyReportID = monthlyReportID;
        this.month = month;
        this.year = year;
      /*  this.userID = userID;
        this.incomeTotal = incomeTotal;
        this.fixedTotal = fixedTotal;
        this.variableTotal = variableTotal;
        this.expensesTotal = expensesTotal;
        this.differenceSummary = differenceSummary;
        this.cashflowsIncome = cashflowsIncome;
        this.cashflowsFixedCosts = cashflowsFixedCosts;
        this.cashflowsVariableCosts = cashflowsVariableCosts;*/
    }

    @JsonProperty("monthly-report_ID")
    public Integer getMonthlyReportID() {
        return monthlyReportID;
    }

    @JsonProperty("monthly-report_ID")
    public void setMonthlyReportID(Integer monthlyReportID) {
        this.monthlyReportID = monthlyReportID;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(String month) {
        this.month = month;
    }

    @JsonProperty("year")
    public Integer getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Integer year) {
        this.year = year;
    }

    @JsonProperty("user_ID")
    public Integer getUserID() {
        return userID;
    }

    @JsonProperty("user_ID")
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @JsonProperty("income_total")
    public Double getIncomeTotal() {
        return incomeTotal;
    }

    @JsonProperty("income_total")
    public void setIncomeTotal(Double incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    @JsonProperty("fixed_total")
    public Double getFixedTotal() {
        return fixedTotal;
    }

    @JsonProperty("fixed_total")
    public void setFixedTotal(Double fixedTotal) {
        this.fixedTotal = fixedTotal;
    }

    @JsonProperty("variable_total")
    public Double getVariableTotal() {
        return variableTotal;
    }

    @JsonProperty("variable_total")
    public void setVariableTotal(Double variableTotal) {
        this.variableTotal = variableTotal;
    }

    @JsonProperty("expenses_total")
    public Double getExpensesTotal() {
        return expensesTotal;
    }

    @JsonProperty("expenses_total")
    public void setExpensesTotal(Double expensesTotal) {
        this.expensesTotal = expensesTotal;
    }

    @JsonProperty("difference_summary")
    public Double getDifferenceSummary() {
        return differenceSummary;
    }

    @JsonProperty("difference_summary")
    public void setDifferenceSummary(Double differenceSummary) {
        this.differenceSummary = differenceSummary;
    }

    @JsonProperty("cashflows_income")
    public List<Cashflow> getCashflowsIncome() {
        return cashflowsIncome;
    }

    @JsonProperty("cashflows_income")
    public void setCashflowsIncome(List<Cashflow> cashflowsIncome) {
        this.cashflowsIncome = cashflowsIncome;
    }

    @JsonProperty("cashflows_fixed-costs")
    public List<Cashflow> getCashflowsFixedCosts() {
        return cashflowsFixedCosts;
    }

    @JsonProperty("cashflows_fixed-costs")
    public void setCashflowsFixedCosts(List<Cashflow> cashflowsFixedCosts) {
        this.cashflowsFixedCosts = cashflowsFixedCosts;
    }

    @JsonProperty("cashflows_variable-costs")
    public List<Cashflow> getCashflowsVariableCosts() {
        return cashflowsVariableCosts;
    }

    @JsonProperty("cashflows_variable-costs")
    public void setCashflowsVariableCosts(List<Cashflow> cashflowsVariableCosts) {
        this.cashflowsVariableCosts = cashflowsVariableCosts;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
