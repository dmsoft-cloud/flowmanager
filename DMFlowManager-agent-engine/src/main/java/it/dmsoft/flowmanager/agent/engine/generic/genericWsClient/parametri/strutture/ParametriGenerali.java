
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wsHost20",
    "NUMERO_JOB_BCI",
    "APP_OPEN",
    "fdi",
    "CLASS",
    "ifcedoges",
    "NUMERO_JOB",
    "comuni",
    "cerved",
    "cse",
    "visCamerali",
    "PACKAGE",
    "METHOD",
    "inps",
    "kccs",
    "crim",
    "assilea"
})
@Generated("jsonschema2pojo")
public class ParametriGenerali {

    @JsonProperty("wsHost20")
    private WsHost20 wsHost20;
    @JsonProperty("NUMERO_JOB_BCI")
    private String numeroJobBci;
    @JsonProperty("APP_OPEN")
    private String appOpen;
    @JsonProperty("fdi")
    private Fdi fdi;
    @JsonProperty("CLASS")
    private String _class;
    @JsonProperty("ifcedoges")
    private Ifcedoges ifcedoges;
    @JsonProperty("NUMERO_JOB")
    private String numeroJob;
    @JsonProperty("comuni")
    private Comuni comuni;
    @JsonProperty("cerved")
    private Cerved cerved;
    @JsonProperty("cse")
    private Cse cse;
    @JsonProperty("visCamerali")
    private VisCamerali visCamerali;
    @JsonProperty("PACKAGE")
    private String _package;
    @JsonProperty("METHOD")
    private String method;
    @JsonProperty("inps")
    private Inps inps;
    @JsonProperty("kccs")
    private Kccs kccs;
    @JsonProperty("crim")
    private Crim crim;
    @JsonProperty("assilea")
    private Assilea assilea;

    @JsonProperty("wsHost20")
    public WsHost20 getWsHost20() {
        return wsHost20;
    }

    @JsonProperty("wsHost20")
    public void setWsHost20(WsHost20 wsHost20) {
        this.wsHost20 = wsHost20;
    }

    @JsonProperty("NUMERO_JOB_BCI")
    public String getNumeroJobBci() {
        return numeroJobBci;
    }

    @JsonProperty("NUMERO_JOB_BCI")
    public void setNumeroJobBci(String numeroJobBci) {
        this.numeroJobBci = numeroJobBci;
    }

    @JsonProperty("APP_OPEN")
    public String getAppOpen() {
        return appOpen;
    }

    @JsonProperty("APP_OPEN")
    public void setAppOpen(String appOpen) {
        this.appOpen = appOpen;
    }

    @JsonProperty("fdi")
    public Fdi getFdi() {
        return fdi;
    }

    @JsonProperty("fdi")
    public void setFdi(Fdi fdi) {
        this.fdi = fdi;
    }

    @JsonProperty("CLASS")
    public String getClass_() {
        return _class;
    }

    @JsonProperty("CLASS")
    public void setClass_(String _class) {
        this._class = _class;
    }

    @JsonProperty("ifcedoges")
    public Ifcedoges getIfcedoges() {
        return ifcedoges;
    }

    @JsonProperty("ifcedoges")
    public void setIfcedoges(Ifcedoges ifcedoges) {
        this.ifcedoges = ifcedoges;
    }

    @JsonProperty("NUMERO_JOB")
    public String getNumeroJob() {
        return numeroJob;
    }

    @JsonProperty("NUMERO_JOB")
    public void setNumeroJob(String numeroJob) {
        this.numeroJob = numeroJob;
    }

    @JsonProperty("comuni")
    public Comuni getComuni() {
        return comuni;
    }

    @JsonProperty("comuni")
    public void setComuni(Comuni comuni) {
        this.comuni = comuni;
    }

    @JsonProperty("cerved")
    public Cerved getCerved() {
        return cerved;
    }

    @JsonProperty("cerved")
    public void setCerved(Cerved cerved) {
        this.cerved = cerved;
    }

    @JsonProperty("cse")
    public Cse getCse() {
        return cse;
    }

    @JsonProperty("cse")
    public void setCse(Cse cse) {
        this.cse = cse;
    }

    @JsonProperty("visCamerali")
    public VisCamerali getVisCamerali() {
        return visCamerali;
    }

    @JsonProperty("visCamerali")
    public void setVisCamerali(VisCamerali visCamerali) {
        this.visCamerali = visCamerali;
    }

    @JsonProperty("PACKAGE")
    public String getPackage() {
        return _package;
    }

    @JsonProperty("PACKAGE")
    public void setPackage(String _package) {
        this._package = _package;
    }

    @JsonProperty("METHOD")
    public String getMethod() {
        return method;
    }

    @JsonProperty("METHOD")
    public void setMethod(String method) {
        this.method = method;
    }

    @JsonProperty("inps")
    public Inps getInps() {
        return inps;
    }

    @JsonProperty("inps")
    public void setInps(Inps inps) {
        this.inps = inps;
    }

    @JsonProperty("kccs")
    public Kccs getKccs() {
        return kccs;
    }

    @JsonProperty("kccs")
    public void setKccs(Kccs kccs) {
        this.kccs = kccs;
    }

    @JsonProperty("crim")
    public Crim getCrim() {
        return crim;
    }

    @JsonProperty("crim")
    public void setCrim(Crim crim) {
        this.crim = crim;
    }

    @JsonProperty("assilea")
    public Assilea getAssilea() {
        return assilea;
    }

    @JsonProperty("assilea")
    public void setAssilea(Assilea assilea) {
        this.assilea = assilea;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ParametriGenerali.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("wsHost20");
        sb.append('=');
        sb.append(((this.wsHost20 == null)?"<null>":this.wsHost20));
        sb.append(',');
        sb.append("numeroJobBci");
        sb.append('=');
        sb.append(((this.numeroJobBci == null)?"<null>":this.numeroJobBci));
        sb.append(',');
        sb.append("appOpen");
        sb.append('=');
        sb.append(((this.appOpen == null)?"<null>":this.appOpen));
        sb.append(',');
        sb.append("fdi");
        sb.append('=');
        sb.append(((this.fdi == null)?"<null>":this.fdi));
        sb.append(',');
        sb.append("_class");
        sb.append('=');
        sb.append(((this._class == null)?"<null>":this._class));
        sb.append(',');
        sb.append("ifcedoges");
        sb.append('=');
        sb.append(((this.ifcedoges == null)?"<null>":this.ifcedoges));
        sb.append(',');
        sb.append("numeroJob");
        sb.append('=');
        sb.append(((this.numeroJob == null)?"<null>":this.numeroJob));
        sb.append(',');
        sb.append("comuni");
        sb.append('=');
        sb.append(((this.comuni == null)?"<null>":this.comuni));
        sb.append(',');
        sb.append("cerved");
        sb.append('=');
        sb.append(((this.cerved == null)?"<null>":this.cerved));
        sb.append(',');
        sb.append("cse");
        sb.append('=');
        sb.append(((this.cse == null)?"<null>":this.cse));
        sb.append(',');
        sb.append("visCamerali");
        sb.append('=');
        sb.append(((this.visCamerali == null)?"<null>":this.visCamerali));
        sb.append(',');
        sb.append("_package");
        sb.append('=');
        sb.append(((this._package == null)?"<null>":this._package));
        sb.append(',');
        sb.append("method");
        sb.append('=');
        sb.append(((this.method == null)?"<null>":this.method));
        sb.append(',');
        sb.append("inps");
        sb.append('=');
        sb.append(((this.inps == null)?"<null>":this.inps));
        sb.append(',');
        sb.append("kccs");
        sb.append('=');
        sb.append(((this.kccs == null)?"<null>":this.kccs));
        sb.append(',');
        sb.append("crim");
        sb.append('=');
        sb.append(((this.crim == null)?"<null>":this.crim));
        sb.append(',');
        sb.append("assilea");
        sb.append('=');
        sb.append(((this.assilea == null)?"<null>":this.assilea));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
