
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "WS_PWD",
    "WS_USER",
    "AS400_IP"
})
@Generated("jsonschema2pojo")
public class Fdi {

    @JsonProperty("WS_PWD")
    private String wsPwd;
    @JsonProperty("WS_USER")
    private String wsUser;
    @JsonProperty("AS400_IP")
    private String as400Ip;

    @JsonProperty("WS_PWD")
    public String getWsPwd() {
        return wsPwd;
    }

    @JsonProperty("WS_PWD")
    public void setWsPwd(String wsPwd) {
        this.wsPwd = wsPwd;
    }

    @JsonProperty("WS_USER")
    public String getWsUser() {
        return wsUser;
    }

    @JsonProperty("WS_USER")
    public void setWsUser(String wsUser) {
        this.wsUser = wsUser;
    }

    @JsonProperty("AS400_IP")
    public String getAs400Ip() {
        return as400Ip;
    }

    @JsonProperty("AS400_IP")
    public void setAs400Ip(String as400Ip) {
        this.as400Ip = as400Ip;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Fdi.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("wsPwd");
        sb.append('=');
        sb.append(((this.wsPwd == null)?"<null>":this.wsPwd));
        sb.append(',');
        sb.append("wsUser");
        sb.append('=');
        sb.append(((this.wsUser == null)?"<null>":this.wsUser));
        sb.append(',');
        sb.append("as400Ip");
        sb.append('=');
        sb.append(((this.as400Ip == null)?"<null>":this.as400Ip));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
