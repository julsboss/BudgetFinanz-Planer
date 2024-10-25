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

public class MonthRO {

    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;
    /* @JsonProperty("confirmationStatus")
     private String confirmationStatus;
     @JsonProperty("source")
     private String source;
     @JsonProperty("slotValue")
     private SlotValue__1 slotValue;*/
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public MonthRO() {
    }

    /* public Id(String name, String value, String confirmationStatus, String source, SlotValue__1 slotValue) {
         super();
         this.name = name;
         this.value = value;
         this.confirmationStatus = confirmationStatus;
         this.source = source;
         this.slotValue = slotValue;
     }*/
    public MonthRO(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

   /* @JsonProperty("confirmationStatus")
    public String getConfirmationStatus() {
        return confirmationStatus;
    }

    @JsonProperty("confirmationStatus")
    public void setConfirmationStatus(String confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("slotValue")
    public SlotValue__1 getSlotValue() {
        return slotValue;
    }

    @JsonProperty("slotValue")
    public void setSlotValue(SlotValue__1 slotValue) {
        this.slotValue = slotValue;
    }*/

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
