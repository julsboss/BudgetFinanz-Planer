
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class CashflowResponse {

    @JsonProperty("sort-order")
    private String sortOrder;
    @JsonProperty("cashflow")
    private List<CashflowDTO> cashflowDTO;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CashflowResponse() {
    }

    /**
     * 
     * @param cashflowDTO
     * @param sortOrder
     */
    public CashflowResponse(String sortOrder, List<CashflowDTO> cashflowDTO) {
        super();
        this.sortOrder = sortOrder;
        this.cashflowDTO = cashflowDTO;
    }

    @JsonProperty("sort-order")
    public String getSortOrder() {
        return sortOrder;
    }

    @JsonProperty("sort-order")
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @JsonProperty("cashflow")
    public List<CashflowDTO> getCashflow() {
        return cashflowDTO;
    }

    @JsonProperty("cashflow")
    public void setCashflow(List<CashflowDTO> cashflowDTO) {
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
