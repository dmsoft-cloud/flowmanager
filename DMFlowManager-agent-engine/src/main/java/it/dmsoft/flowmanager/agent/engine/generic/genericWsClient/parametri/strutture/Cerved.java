
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "USER_CERVED",
    "CLASSE_VER",
    "CHK_CERTIFICATO",
    "CLASSE_INS",
    "CLASSE_ANN",
    "PWD_CERVED",
    "INDEPENDENT_ASP",
    "MACCHINA"
})
@Generated("jsonschema2pojo")
public class Cerved {

    @JsonProperty("USER_CERVED")
    private String userCerved;
    @JsonProperty("CLASSE_VER")
    private String classeVer;
    @JsonProperty("CHK_CERTIFICATO")
    private String chkCertificato;
    @JsonProperty("CLASSE_INS")
    private String classeIns;
    @JsonProperty("CLASSE_ANN")
    private String classeAnn;
    @JsonProperty("PWD_CERVED")
    private String pwdCerved;
    @JsonProperty("INDEPENDENT_ASP")
    private String independentAsp;
    @JsonProperty("MACCHINA")
    private String macchina;

    @JsonProperty("USER_CERVED")
    public String getUserCerved() {
        return userCerved;
    }

    @JsonProperty("USER_CERVED")
    public void setUserCerved(String userCerved) {
        this.userCerved = userCerved;
    }

    @JsonProperty("CLASSE_VER")
    public String getClasseVer() {
        return classeVer;
    }

    @JsonProperty("CLASSE_VER")
    public void setClasseVer(String classeVer) {
        this.classeVer = classeVer;
    }

    @JsonProperty("CHK_CERTIFICATO")
    public String getChkCertificato() {
        return chkCertificato;
    }

    @JsonProperty("CHK_CERTIFICATO")
    public void setChkCertificato(String chkCertificato) {
        this.chkCertificato = chkCertificato;
    }

    @JsonProperty("CLASSE_INS")
    public String getClasseIns() {
        return classeIns;
    }

    @JsonProperty("CLASSE_INS")
    public void setClasseIns(String classeIns) {
        this.classeIns = classeIns;
    }

    @JsonProperty("CLASSE_ANN")
    public String getClasseAnn() {
        return classeAnn;
    }

    @JsonProperty("CLASSE_ANN")
    public void setClasseAnn(String classeAnn) {
        this.classeAnn = classeAnn;
    }

    @JsonProperty("PWD_CERVED")
    public String getPwdCerved() {
        return pwdCerved;
    }

    @JsonProperty("PWD_CERVED")
    public void setPwdCerved(String pwdCerved) {
        this.pwdCerved = pwdCerved;
    }

    @JsonProperty("INDEPENDENT_ASP")
    public String getIndependentAsp() {
        return independentAsp;
    }

    @JsonProperty("INDEPENDENT_ASP")
    public void setIndependentAsp(String independentAsp) {
        this.independentAsp = independentAsp;
    }

    @JsonProperty("MACCHINA")
    public String getMacchina() {
        return macchina;
    }

    @JsonProperty("MACCHINA")
    public void setMacchina(String macchina) {
        this.macchina = macchina;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Cerved.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("userCerved");
        sb.append('=');
        sb.append(((this.userCerved == null)?"<null>":this.userCerved));
        sb.append(',');
        sb.append("classeVer");
        sb.append('=');
        sb.append(((this.classeVer == null)?"<null>":this.classeVer));
        sb.append(',');
        sb.append("chkCertificato");
        sb.append('=');
        sb.append(((this.chkCertificato == null)?"<null>":this.chkCertificato));
        sb.append(',');
        sb.append("classeIns");
        sb.append('=');
        sb.append(((this.classeIns == null)?"<null>":this.classeIns));
        sb.append(',');
        sb.append("classeAnn");
        sb.append('=');
        sb.append(((this.classeAnn == null)?"<null>":this.classeAnn));
        sb.append(',');
        sb.append("pwdCerved");
        sb.append('=');
        sb.append(((this.pwdCerved == null)?"<null>":this.pwdCerved));
        sb.append(',');
        sb.append("independentAsp");
        sb.append('=');
        sb.append(((this.independentAsp == null)?"<null>":this.independentAsp));
        sb.append(',');
        sb.append("macchina");
        sb.append('=');
        sb.append(((this.macchina == null)?"<null>":this.macchina));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
