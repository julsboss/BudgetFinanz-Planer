package mosbach.dhbw.de.mymonthlybudget.model.alexa;

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
