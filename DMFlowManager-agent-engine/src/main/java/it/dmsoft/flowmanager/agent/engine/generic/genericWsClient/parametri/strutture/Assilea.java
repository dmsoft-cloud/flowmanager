
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "NUM_MAX_TENTATIVI"
})
@Generated("jsonschema2pojo")
public class Assilea {

    @JsonProperty("NUM_MAX_TENTATIVI")
    private String numMaxTentativi;

    @JsonProperty("NUM_MAX_TENTATIVI")
    public String getNumMaxTentativi() {
        return numMaxTentativi;
    }

    @JsonProperty("NUM_MAX_TENTATIVI")
    public void setNumMaxTentativi(String numMaxTentativi) {
        this.numMaxTentativi = numMaxTentativi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Assilea.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("numMaxTentativi");
        sb.append('=');
        sb.append(((this.numMaxTentativi == null)?"<null>":this.numMaxTentativi));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
