
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import mosbach.dhbw.de.mymonthlybudget.model.StatistikDTO;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class StatistikResponse {

    @JsonProperty("statistik")
    private List<StatistikDTO> statistikDTO;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatistikResponse() {
    }

    public StatistikResponse(List<StatistikDTO> statistikDTO) {
        super();
        this.statistikDTO = statistikDTO;
    }

    @JsonProperty("statistik")
    public List<StatistikDTO> getStatistik() {
        return statistikDTO;
    }

    @JsonProperty("statistik")
    public void setStatistik(List<StatistikDTO> statistikDTO) {
        this.statistikDTO = statistikDTO;
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
