//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package mosbach.dhbw.de.mymonthlybudget.dto;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


import mosbach.dhbw.de.mymonthlybudget.model.User;

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
    @JsonProperty("pat")
    private String pat;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public UserDTO() {
    }


    public UserDTO(String firstName, String lastName, String email, String password, String pat) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.pat = pat;
    }

    public UserDTO(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = "";
        this.pat = user.getPat();
    }



    public UserDTO(int userID, String firstName, String lastName, String email, String password){
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    @JsonProperty("firstName")
    public String getFirstName() {

        return this.firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return this.lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(
            String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("email")
    public String getEmail() {

        return this.email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {

        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {

        return this.password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {

        this.password = password;
    }

    @JsonProperty("pat")
    public String getPat() {

        return this.pat;
    }

    @JsonProperty("pat")
    public void setPat(String pat) {

        this.pat = pat;
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
