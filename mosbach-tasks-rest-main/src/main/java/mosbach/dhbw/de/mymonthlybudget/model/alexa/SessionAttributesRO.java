package mosbach.dhbw.de.mymonthlybudget.model.alexa;


import com.fasterxml.jackson.annotation.*;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


@JsonTypeName(value = SessionAttributesRO.TYPENAME)
public class SessionAttributesRO
{
    protected final static String TYPENAME = "SessionAttributesRO";

    @JsonProperty("attributes")
    private HashMap<String, Integer> attributes;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public SessionAttributesRO()
    {
        super();
    }

    @JsonProperty("sessionId")
    public HashMap<String, Integer> getAttributes() {
        return attributes;
    }

    @JsonProperty("sessionId")
    public void setAttributes(HashMap<String, Integer> attributes) {
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

