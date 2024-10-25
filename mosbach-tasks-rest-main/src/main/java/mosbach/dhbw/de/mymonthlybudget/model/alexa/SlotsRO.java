package mosbach.dhbw.de.mymonthlybudget.model.alexa;

import java.time.Year;
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
@JsonPropertyOrder({
        "password",
        "id"
})
@Generated("jsonschema2pojo")
public class SlotsRO {

    @JsonProperty("password")
    private PasswordRO password;
    @JsonProperty("id")
    private UserIDRO id;
    @JsonProperty("month")
    private MonthRO month;
    @JsonProperty("year")
    private YearRO year;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public SlotsRO() {
    }

    public SlotsRO(PasswordRO password, UserIDRO id) {
        super();
        this.password = password;
        this.id = id;
    }

    @JsonProperty("password")
    public PasswordRO getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(PasswordRO password) {
        this.password = password;
    }

    @JsonProperty("id")
    public UserIDRO getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UserIDRO id) {
        this.id = id;
    }

    @JsonProperty("month")
    public MonthRO getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(MonthRO month) {
        this.month = month;
    }

    @JsonProperty("year")
    public YearRO getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(YearRO year) {
        this.year = year;
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
