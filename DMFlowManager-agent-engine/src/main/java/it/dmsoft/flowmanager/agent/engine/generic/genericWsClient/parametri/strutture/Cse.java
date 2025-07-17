
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SERVICE_NAME",
    "SERVICE_VERS",
    "ABI"
})
@Generated("jsonschema2pojo")
public class Cse {

    @JsonProperty("SERVICE_NAME")
    private String serviceName;
    @JsonProperty("SERVICE_VERS")
    private String serviceVers;
    @JsonProperty("ABI")
    private String abi;

    @JsonProperty("SERVICE_NAME")
    public String getServiceName() {
        return serviceName;
    }

    @JsonProperty("SERVICE_NAME")
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @JsonProperty("SERVICE_VERS")
    public String getServiceVers() {
        return serviceVers;
    }

    @JsonProperty("SERVICE_VERS")
    public void setServiceVers(String serviceVers) {
        this.serviceVers = serviceVers;
    }

    @JsonProperty("ABI")
    public String getAbi() {
        return abi;
    }

    @JsonProperty("ABI")
    public void setAbi(String abi) {
        this.abi = abi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("serviceName");
        sb.append('=');
        sb.append(((this.serviceName == null)?"<null>":this.serviceName));
        sb.append(',');
        sb.append("serviceVers");
        sb.append('=');
        sb.append(((this.serviceVers == null)?"<null>":this.serviceVers));
        sb.append(',');
        sb.append("abi");
        sb.append('=');
        sb.append(((this.abi == null)?"<null>":this.abi));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
