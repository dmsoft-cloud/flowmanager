
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "LANGUAGE",
    "ARCH_PASSWORD",
    "VERSION_MAJOR",
    "VERSION_MINOR",
    "APPLICAZIONE",
    "SERVICE_TYPE",
    "ENV",
    "ARCH_USER_ID",
    "HEADER_VER"
})
@Generated("jsonschema2pojo")
public class Crim {

    @JsonProperty("LANGUAGE")
    private String language;
    @JsonProperty("ARCH_PASSWORD")
    private String archPassword;
    @JsonProperty("VERSION_MAJOR")
    private String versionMajor;
    @JsonProperty("VERSION_MINOR")
    private String versionMinor;
    @JsonProperty("APPLICAZIONE")
    private String applicazione;
    @JsonProperty("SERVICE_TYPE")
    private String serviceType;
    @JsonProperty("ENV")
    private String env;
    @JsonProperty("ARCH_USER_ID")
    private String archUserId;
    @JsonProperty("HEADER_VER")
    private String headerVer;

    @JsonProperty("LANGUAGE")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("LANGUAGE")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("ARCH_PASSWORD")
    public String getArchPassword() {
        return archPassword;
    }

    @JsonProperty("ARCH_PASSWORD")
    public void setArchPassword(String archPassword) {
        this.archPassword = archPassword;
    }

    @JsonProperty("VERSION_MAJOR")
    public String getVersionMajor() {
        return versionMajor;
    }

    @JsonProperty("VERSION_MAJOR")
    public void setVersionMajor(String versionMajor) {
        this.versionMajor = versionMajor;
    }

    @JsonProperty("VERSION_MINOR")
    public String getVersionMinor() {
        return versionMinor;
    }

    @JsonProperty("VERSION_MINOR")
    public void setVersionMinor(String versionMinor) {
        this.versionMinor = versionMinor;
    }

    @JsonProperty("APPLICAZIONE")
    public String getApplicazione() {
        return applicazione;
    }

    @JsonProperty("APPLICAZIONE")
    public void setApplicazione(String applicazione) {
        this.applicazione = applicazione;
    }

    @JsonProperty("SERVICE_TYPE")
    public String getServiceType() {
        return serviceType;
    }

    @JsonProperty("SERVICE_TYPE")
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @JsonProperty("ENV")
    public String getEnv() {
        return env;
    }

    @JsonProperty("ENV")
    public void setEnv(String env) {
        this.env = env;
    }

    @JsonProperty("ARCH_USER_ID")
    public String getArchUserId() {
        return archUserId;
    }

    @JsonProperty("ARCH_USER_ID")
    public void setArchUserId(String archUserId) {
        this.archUserId = archUserId;
    }

    @JsonProperty("HEADER_VER")
    public String getHeaderVer() {
        return headerVer;
    }

    @JsonProperty("HEADER_VER")
    public void setHeaderVer(String headerVer) {
        this.headerVer = headerVer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Crim.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("language");
        sb.append('=');
        sb.append(((this.language == null)?"<null>":this.language));
        sb.append(',');
        sb.append("archPassword");
        sb.append('=');
        sb.append(((this.archPassword == null)?"<null>":this.archPassword));
        sb.append(',');
        sb.append("versionMajor");
        sb.append('=');
        sb.append(((this.versionMajor == null)?"<null>":this.versionMajor));
        sb.append(',');
        sb.append("versionMinor");
        sb.append('=');
        sb.append(((this.versionMinor == null)?"<null>":this.versionMinor));
        sb.append(',');
        sb.append("applicazione");
        sb.append('=');
        sb.append(((this.applicazione == null)?"<null>":this.applicazione));
        sb.append(',');
        sb.append("serviceType");
        sb.append('=');
        sb.append(((this.serviceType == null)?"<null>":this.serviceType));
        sb.append(',');
        sb.append("env");
        sb.append('=');
        sb.append(((this.env == null)?"<null>":this.env));
        sb.append(',');
        sb.append("archUserId");
        sb.append('=');
        sb.append(((this.archUserId == null)?"<null>":this.archUserId));
        sb.append(',');
        sb.append("headerVer");
        sb.append('=');
        sb.append(((this.headerVer == null)?"<null>":this.headerVer));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
