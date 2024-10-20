
package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class CashflowDTO {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("category")
    private String category;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("date")
    private String date;
    @JsonProperty("payment_method")
    private String paymentMethod;
    @JsonProperty("repetition")
    private String repetition;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("user_id")
    private Integer user_id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public CashflowDTO() {
    }

    /**
     * 
     * @param date
     * @param amount
     * @param paymentMethod
     * @param comment
     * @param type
     * @param category
     * @param repetition
     */
   /* public CashflowDTO(Integer id, String type, String category, Double amount, String date, String paymentMethod, String repetition, String comment, Integer user_id) {
        super();
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comment = comment;
        this.user_id = user_id;
    }*/
    public CashflowDTO(Integer id, String type, String category, Double amount, String date, String paymentMethod, String repetition, String comment) {
        super();
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comment = comment;
            }
    public CashflowDTO(String type, String category, Double amount, String date, String paymentMethod, String repetition, String comment) {
        super();
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.repetition = repetition;
        this.comment = comment;
    }

    public CashflowDTO(Cashflow cashflow){
        this.id = cashflow.getCashflowID();
        this.type = cashflow.getType();
        this.category = cashflow.getCategory();
        this.amount = cashflow.getAmount();
        this.date = cashflow.getDate();
        this.paymentMethod = cashflow.getPaymentMethod();
        this.repetition = cashflow.getRepetition();
        this.comment = cashflow.getComment();
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("amount")
    public Double getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("payment_method")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("payment_method")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @JsonProperty("repetition")
    public String getRepetition() {
        return repetition;
    }

    @JsonProperty("repetition")
    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
    @JsonProperty("id")
    public Integer getId() {
        return id;  // Getter for ID
    }
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;  // Setter for ID
    }
    @JsonProperty("user_id")
    public void setUserId(Integer user_id) {
        this.user_id = user_id;  // Setter for ID
    }
    @JsonProperty("user_id")
    public Integer getUserId() {
        return user_id;  // Getter for ID
    }


}
