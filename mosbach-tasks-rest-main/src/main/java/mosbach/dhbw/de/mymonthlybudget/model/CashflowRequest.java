
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class CashflowRequest {

    @JsonProperty("token")
    private String token;
    @JsonProperty("cashflow")
    private CashflowDTO cashflowDTO;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CashflowRequest() {
    }

    /**
     * 
     * @param cashflowDTO
     * @param token
     */
    public CashflowRequest(String token, CashflowDTO cashflowDTO) {
        super();
        this.token = token;
        this.cashflowDTO = cashflowDTO;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    @JsonProperty("token")
    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("cashflow")
    public CashflowDTO getCashflow() {
        return cashflowDTO;
    }

    @JsonProperty("cashflow")
    public void setCashflow(CashflowDTO cashflowDTO) {
        this.cashflowDTO = cashflowDTO;
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
