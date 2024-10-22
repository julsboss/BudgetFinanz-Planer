
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class StatistikDTO {

    @JsonProperty("month")
    private String month;
    @JsonProperty("income_total")
    private Double incomeTotal;
    @JsonProperty("expenses_total")
    private Double expensesTotal;
    @JsonProperty("difference_summary")
    private Double differenceSummary;
    @JsonProperty("wealth")
    private Double wealth;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatistikDTO() {
    }

    public StatistikDTO(String month, Double incomeTotal, Double expensesTotal, Double differenceSummary, Double wealth) {
        super();
        this.month = month;
        this.incomeTotal = incomeTotal;
        this.expensesTotal = expensesTotal;
        this.differenceSummary = differenceSummary;
        this.wealth = wealth;
    }

    @JsonProperty("month")
    public String getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(String month) {
        this.month = month;
    }

    @JsonProperty("income_total")
    public Double getIncomeTotal() {
        return incomeTotal;
    }

    @JsonProperty("income_total")
    public void setIncomeTotal(Double incomeTotal) {
        this.incomeTotal = incomeTotal;
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

    @JsonProperty("wealth")
    public Double getWealth() {
        return wealth;
    }

    @JsonProperty("wealth")
    public void setWealth(Double wealth) {
        this.wealth = wealth;
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
