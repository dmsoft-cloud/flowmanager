
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "filiale",
    "ruolo",
    "maxRows",
    "objectStore",
    "dogesUrl",
    "objectClass",
    "dogesPath",
    "matricola",
    "dogesCifratura"
})
@Generated("jsonschema2pojo")
public class Ifcedoges {

    @JsonProperty("filiale")
    private String filiale;
    @JsonProperty("ruolo")
    private String ruolo;
    @JsonProperty("maxRows")
    private String maxRows;
    @JsonProperty("objectStore")
    private String objectStore;
    @JsonProperty("dogesUrl")
    private String dogesUrl;
    @JsonProperty("objectClass")
    private String objectClass;
    @JsonProperty("dogesPath")
    private String dogesPath;
    @JsonProperty("matricola")
    private String matricola;
    @JsonProperty("dogesCifratura")
    private String dogesCifratura;

    @JsonProperty("filiale")
    public String getFiliale() {
        return filiale;
    }

    @JsonProperty("filiale")
    public void setFiliale(String filiale) {
        this.filiale = filiale;
    }

    @JsonProperty("ruolo")
    public String getRuolo() {
        return ruolo;
    }

    @JsonProperty("ruolo")
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    @JsonProperty("maxRows")
    public String getMaxRows() {
        return maxRows;
    }

    @JsonProperty("maxRows")
    public void setMaxRows(String maxRows) {
        this.maxRows = maxRows;
    }

    @JsonProperty("objectStore")
    public String getObjectStore() {
        return objectStore;
    }

    @JsonProperty("objectStore")
    public void setObjectStore(String objectStore) {
        this.objectStore = objectStore;
    }

    @JsonProperty("dogesUrl")
    public String getDogesUrl() {
        return dogesUrl;
    }

    @JsonProperty("dogesUrl")
    public void setDogesUrl(String dogesUrl) {
        this.dogesUrl = dogesUrl;
    }

    @JsonProperty("objectClass")
    public String getObjectClass() {
        return objectClass;
    }

    @JsonProperty("objectClass")
    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    @JsonProperty("dogesPath")
    public String getDogesPath() {
        return dogesPath;
    }

    @JsonProperty("dogesPath")
    public void setDogesPath(String dogesPath) {
        this.dogesPath = dogesPath;
    }

    @JsonProperty("matricola")
    public String getMatricola() {
        return matricola;
    }

    @JsonProperty("matricola")
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }

    @JsonProperty("dogesCifratura")
    public String getDogesCifratura() {
        return dogesCifratura;
    }

    @JsonProperty("dogesCifratura")
    public void setDogesCifratura(String dogesCifratura) {
        this.dogesCifratura = dogesCifratura;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Ifcedoges.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("filiale");
        sb.append('=');
        sb.append(((this.filiale == null)?"<null>":this.filiale));
        sb.append(',');
        sb.append("ruolo");
        sb.append('=');
        sb.append(((this.ruolo == null)?"<null>":this.ruolo));
        sb.append(',');
        sb.append("maxRows");
        sb.append('=');
        sb.append(((this.maxRows == null)?"<null>":this.maxRows));
        sb.append(',');
        sb.append("objectStore");
        sb.append('=');
        sb.append(((this.objectStore == null)?"<null>":this.objectStore));
        sb.append(',');
        sb.append("dogesUrl");
        sb.append('=');
        sb.append(((this.dogesUrl == null)?"<null>":this.dogesUrl));
        sb.append(',');
        sb.append("objectClass");
        sb.append('=');
        sb.append(((this.objectClass == null)?"<null>":this.objectClass));
        sb.append(',');
        sb.append("dogesPath");
        sb.append('=');
        sb.append(((this.dogesPath == null)?"<null>":this.dogesPath));
        sb.append(',');
        sb.append("matricola");
        sb.append('=');
        sb.append(((this.matricola == null)?"<null>":this.matricola));
        sb.append(',');
        sb.append("dogesCifratura");
        sb.append('=');
        sb.append(((this.dogesCifratura == null)?"<null>":this.dogesCifratura));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
