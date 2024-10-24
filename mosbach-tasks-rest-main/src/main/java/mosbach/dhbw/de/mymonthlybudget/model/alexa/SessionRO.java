package mosbach.dhbw.de.mymonthlybudget.model.alexa;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;


@JsonTypeName(value = SessionRO.TYPENAME)
public class SessionRO
{
    protected final static String TYPENAME = "SessionRO";
    @JsonProperty("attributes")
    private HashMap<String, String> attributes;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public SessionRO()
    {
        super();
    }

    @JsonProperty("attributes")
    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
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
