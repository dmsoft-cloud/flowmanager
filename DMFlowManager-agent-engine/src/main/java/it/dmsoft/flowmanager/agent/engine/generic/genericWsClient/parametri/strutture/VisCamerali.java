
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CRIF_REMOTO_PWD",
    "CRIF_REMOTO_USER"
})
@Generated("jsonschema2pojo")
public class VisCamerali {

    @JsonProperty("CRIF_REMOTO_PWD")
    private String crifRemotoPwd;
    @JsonProperty("CRIF_REMOTO_USER")
    private String crifRemotoUser;

    @JsonProperty("CRIF_REMOTO_PWD")
    public String getCrifRemotoPwd() {
        return crifRemotoPwd;
    }

    @JsonProperty("CRIF_REMOTO_PWD")
    public void setCrifRemotoPwd(String crifRemotoPwd) {
        this.crifRemotoPwd = crifRemotoPwd;
    }

    @JsonProperty("CRIF_REMOTO_USER")
    public String getCrifRemotoUser() {
        return crifRemotoUser;
    }

    @JsonProperty("CRIF_REMOTO_USER")
    public void setCrifRemotoUser(String crifRemotoUser) {
        this.crifRemotoUser = crifRemotoUser;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VisCamerali.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("crifRemotoPwd");
        sb.append('=');
        sb.append(((this.crifRemotoPwd == null)?"<null>":this.crifRemotoPwd));
        sb.append(',');
        sb.append("crifRemotoUser");
        sb.append('=');
        sb.append(((this.crifRemotoUser == null)?"<null>":this.crifRemotoUser));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
