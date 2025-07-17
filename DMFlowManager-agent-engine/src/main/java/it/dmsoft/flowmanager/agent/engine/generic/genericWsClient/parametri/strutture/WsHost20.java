
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "VERSIONE",
    "CENTRO_COSTO"
})
@Generated("jsonschema2pojo")
public class WsHost20 {

    @JsonProperty("VERSIONE")
    private String versione;
    @JsonProperty("CENTRO_COSTO")
    private String centroCosto;

    @JsonProperty("VERSIONE")
    public String getVersione() {
        return versione;
    }

    @JsonProperty("VERSIONE")
    public void setVersione(String versione) {
        this.versione = versione;
    }

    @JsonProperty("CENTRO_COSTO")
    public String getCentroCosto() {
        return centroCosto;
    }

    @JsonProperty("CENTRO_COSTO")
    public void setCentroCosto(String centroCosto) {
        this.centroCosto = centroCosto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(WsHost20 .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("versione");
        sb.append('=');
        sb.append(((this.versione == null)?"<null>":this.versione));
        sb.append(',');
        sb.append("centroCosto");
        sb.append('=');
        sb.append(((this.centroCosto == null)?"<null>":this.centroCosto));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
