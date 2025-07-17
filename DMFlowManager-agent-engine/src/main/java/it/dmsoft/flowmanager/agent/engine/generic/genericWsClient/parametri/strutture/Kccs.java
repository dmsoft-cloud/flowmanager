
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "CERT_ALIAS_REQ",
    "CERT_ALIAS_RES"
})
@Generated("jsonschema2pojo")
public class Kccs {

    @JsonProperty("CERT_ALIAS_REQ")
    private String certAliasReq;
    @JsonProperty("CERT_ALIAS_RES")
    private String certAliasRes;

    @JsonProperty("CERT_ALIAS_REQ")
    public String getCertAliasReq() {
        return certAliasReq;
    }

    @JsonProperty("CERT_ALIAS_REQ")
    public void setCertAliasReq(String certAliasReq) {
        this.certAliasReq = certAliasReq;
    }

    @JsonProperty("CERT_ALIAS_RES")
    public String getCertAliasRes() {
        return certAliasRes;
    }

    @JsonProperty("CERT_ALIAS_RES")
    public void setCertAliasRes(String certAliasRes) {
        this.certAliasRes = certAliasRes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Kccs.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("certAliasReq");
        sb.append('=');
        sb.append(((this.certAliasReq == null)?"<null>":this.certAliasReq));
        sb.append(',');
        sb.append("certAliasRes");
        sb.append('=');
        sb.append(((this.certAliasRes == null)?"<null>":this.certAliasRes));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
