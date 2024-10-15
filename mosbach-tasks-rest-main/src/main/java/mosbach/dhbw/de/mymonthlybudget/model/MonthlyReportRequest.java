
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class MonthlyReportRequest {

    @JsonProperty("token")
    private String token;
    @JsonProperty("monthly-report")
    private MonthlyReport monthlyReport;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public MonthlyReportRequest() {
    }

    public MonthlyReportRequest(String token, MonthlyReport monthlyReport) {
        super();
        this.token = token;
        this.monthlyReport = monthlyReport;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("monthly-report")
    public MonthlyReport getMonthlyReport() {
        return monthlyReport;
    }

    @JsonProperty("monthly-report")
    public void setMonthlyReport(MonthlyReport monthlyReport) {
        this.monthlyReport = monthlyReport;
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
