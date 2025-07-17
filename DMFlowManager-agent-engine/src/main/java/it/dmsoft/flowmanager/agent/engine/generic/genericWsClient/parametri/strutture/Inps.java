
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "MIN_INTERVALLO",
    "NUM_MAX_TRASM"
})
@Generated("jsonschema2pojo")
public class Inps {

    @JsonProperty("MIN_INTERVALLO")
    private String minIntervallo;
    @JsonProperty("NUM_MAX_TRASM")
    private String numMaxTrasm;

    @JsonProperty("MIN_INTERVALLO")
    public String getMinIntervallo() {
        return minIntervallo;
    }

    @JsonProperty("MIN_INTERVALLO")
    public void setMinIntervallo(String minIntervallo) {
        this.minIntervallo = minIntervallo;
    }

    @JsonProperty("NUM_MAX_TRASM")
    public String getNumMaxTrasm() {
        return numMaxTrasm;
    }

    @JsonProperty("NUM_MAX_TRASM")
    public void setNumMaxTrasm(String numMaxTrasm) {
        this.numMaxTrasm = numMaxTrasm;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Inps.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("minIntervallo");
        sb.append('=');
        sb.append(((this.minIntervallo == null)?"<null>":this.minIntervallo));
        sb.append(',');
        sb.append("numMaxTrasm");
        sb.append('=');
        sb.append(((this.numMaxTrasm == null)?"<null>":this.numMaxTrasm));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
