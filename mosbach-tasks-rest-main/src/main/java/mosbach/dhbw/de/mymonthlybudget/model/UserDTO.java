package mosbach.dhbw.de.mymonthlybudget.model;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import mosbach.dhbw.de.mymonthlybudget.data.impl.User;


@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserDTO {

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public UserDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    /* public UserDTO(String firstName, String lastName, String email, String password, String pat) {
         this.firstName = firstName;
         this.lastName = lastName;
         this.email = email;
         this.password = password;
         this.pat = pat;

     }*/
    public UserDTO(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email =user.getEmail();
        this.password =user.getPassword();
        //this.pat = "";

    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }
    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }
    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

   /* @JsonProperty("pat")
    public String getPat() {
        return pat;
    }
    @JsonProperty("pat")
    public void setPat(String pat) {
        this.pat = pat;
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
