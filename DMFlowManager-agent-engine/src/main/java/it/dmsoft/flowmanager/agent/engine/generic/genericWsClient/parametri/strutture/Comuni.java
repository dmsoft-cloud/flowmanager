
package it.dmsoft.flowmanager.agent.engine.generic.genericWsClient.parametri.strutture;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "REMOTO_USER",
    "CERT_CLIENT_ALIAS",
    "INIT_VECTOR",
    "BASIC_PWD",
    "SECRET_KEY",
    "LOG_LEVEL_ALT",
    "CLASSE",
    "LOG_NUM_DOC",
    "PROTOCOLLO",
    "PROXY_IP",
    "REMOTO_REP",
    "TRUSTSTORE_PATH",
    "PROXY_PORTA",
    "LOG_DOCUM_ALT",
    "TRUSTSTORE_PWD",
    "ABILITA_LOG_SINGOLO",
    "KEYSTORE_SERVER_ALIAS",
    "TOKEN_JWT",
    "KEYSTORE_SERVER_PATH",
    "LOG_LEVEL",
    "PROXY_USER",
    "PROXY_PWD",
    "VISUALIZATION",
    "REMOTO_SOC",
    "AMBIENTE_WS",
    "BASIC_USER_ID",
    "FILE_PATH_OUT",
    "CERT_CLIENT_PWD",
    "VERSIONE_JAVA",
    "FILE_URL",
    "LOG_DIM",
    "LOG_DOCUM",
    "REMOTO_PWD",
    "USA_TOKEN_FISICO",
    "URL_WS_BATCH_GEDO",
    "URL_TOKEN",
    "TIMEOUT",
    "KEYSTORE_SERVER_PWD",
    "CERT_CLIENT_PATH",
    "URL_WS"
})
@Generated("jsonschema2pojo")
public class Comuni {

    @JsonProperty("REMOTO_USER")
    private String remotoUser;
    @JsonProperty("CERT_CLIENT_ALIAS")
    private String certClientAlias;
    @JsonProperty("INIT_VECTOR")
    private String initVector;
    @JsonProperty("BASIC_PWD")
    private String basicPwd;
    @JsonProperty("SECRET_KEY")
    private String secretKey;
    @JsonProperty("LOG_LEVEL_ALT")
    private String logLevelAlt;
    @JsonProperty("CLASSE")
    private String classe;
    @JsonProperty("LOG_NUM_DOC")
    private String logNumDoc;
    @JsonProperty("PROTOCOLLO")
    private String protocollo;
    @JsonProperty("PROXY_IP")
    private String proxyIp;
    @JsonProperty("REMOTO_REP")
    private String remotoRep;
    @JsonProperty("TRUSTSTORE_PATH")
    private String truststorePath;
    @JsonProperty("PROXY_PORTA")
    private String proxyPorta;
    @JsonProperty("LOG_DOCUM_ALT")
    private String logDocumAlt;
    @JsonProperty("TRUSTSTORE_PWD")
    private String truststorePwd;
    @JsonProperty("ABILITA_LOG_SINGOLO")
    private String abilitaLogSingolo;
    @JsonProperty("KEYSTORE_SERVER_ALIAS")
    private String keystoreServerAlias;
    @JsonProperty("TOKEN_JWT")
    private String tokenJwt;
    @JsonProperty("KEYSTORE_SERVER_PATH")
    private String keystoreServerPath;
    @JsonProperty("LOG_LEVEL")
    private String logLevel;
    @JsonProperty("PROXY_USER")
    private String proxyUser;
    @JsonProperty("PROXY_PWD")
    private String proxyPwd;
    @JsonProperty("VISUALIZATION")
    private String visualization;
    @JsonProperty("REMOTO_SOC")
    private String remotoSoc;
    @JsonProperty("AMBIENTE_WS")
    private String ambienteWs;
    @JsonProperty("BASIC_USER_ID")
    private String basicUserId;
    @JsonProperty("FILE_PATH_OUT")
    private String filePathOut;
    @JsonProperty("CERT_CLIENT_PWD")
    private String certClientPwd;
    @JsonProperty("VERSIONE_JAVA")
    private String versioneJava;
    @JsonProperty("FILE_URL")
    private String fileUrl;
    @JsonProperty("LOG_DIM")
    private String logDim;
    @JsonProperty("LOG_DOCUM")
    private String logDocum;
    @JsonProperty("REMOTO_PWD")
    private String remotoPwd;
    @JsonProperty("USA_TOKEN_FISICO")
    private String usaTokenFisico;
    @JsonProperty("URL_WS_BATCH_GEDO")
    private String urlWsBatchGedo;
    @JsonProperty("URL_TOKEN")
    private String urlToken;
    @JsonProperty("TIMEOUT")
    private String timeout;
    @JsonProperty("KEYSTORE_SERVER_PWD")
    private String keystoreServerPwd;
    @JsonProperty("CERT_CLIENT_PATH")
    private String certClientPath;
    @JsonProperty("URL_WS")
    private String urlWs;

    @JsonProperty("REMOTO_USER")
    public String getRemotoUser() {
        return remotoUser;
    }

    @JsonProperty("REMOTO_USER")
    public void setRemotoUser(String remotoUser) {
        this.remotoUser = remotoUser;
    }

    @JsonProperty("CERT_CLIENT_ALIAS")
    public String getCertClientAlias() {
        return certClientAlias;
    }

    @JsonProperty("CERT_CLIENT_ALIAS")
    public void setCertClientAlias(String certClientAlias) {
        this.certClientAlias = certClientAlias;
    }

    @JsonProperty("INIT_VECTOR")
    public String getInitVector() {
        return initVector;
    }

    @JsonProperty("INIT_VECTOR")
    public void setInitVector(String initVector) {
        this.initVector = initVector;
    }

    @JsonProperty("BASIC_PWD")
    public String getBasicPwd() {
        return basicPwd;
    }

    @JsonProperty("BASIC_PWD")
    public void setBasicPwd(String basicPwd) {
        this.basicPwd = basicPwd;
    }

    @JsonProperty("SECRET_KEY")
    public String getSecretKey() {
        return secretKey;
    }

    @JsonProperty("SECRET_KEY")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @JsonProperty("LOG_LEVEL_ALT")
    public String getLogLevelAlt() {
        return logLevelAlt;
    }

    @JsonProperty("LOG_LEVEL_ALT")
    public void setLogLevelAlt(String logLevelAlt) {
        this.logLevelAlt = logLevelAlt;
    }

    @JsonProperty("CLASSE")
    public String getClasse() {
        return classe;
    }

    @JsonProperty("CLASSE")
    public void setClasse(String classe) {
        this.classe = classe;
    }

    @JsonProperty("LOG_NUM_DOC")
    public String getLogNumDoc() {
        return logNumDoc;
    }

    @JsonProperty("LOG_NUM_DOC")
    public void setLogNumDoc(String logNumDoc) {
        this.logNumDoc = logNumDoc;
    }

    @JsonProperty("PROTOCOLLO")
    public String getProtocollo() {
        return protocollo;
    }

    @JsonProperty("PROTOCOLLO")
    public void setProtocollo(String protocollo) {
        this.protocollo = protocollo;
    }

    @JsonProperty("PROXY_IP")
    public String getProxyIp() {
        return proxyIp;
    }

    @JsonProperty("PROXY_IP")
    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    @JsonProperty("REMOTO_REP")
    public String getRemotoRep() {
        return remotoRep;
    }

    @JsonProperty("REMOTO_REP")
    public void setRemotoRep(String remotoRep) {
        this.remotoRep = remotoRep;
    }

    @JsonProperty("TRUSTSTORE_PATH")
    public String getTruststorePath() {
        return truststorePath;
    }

    @JsonProperty("TRUSTSTORE_PATH")
    public void setTruststorePath(String truststorePath) {
        this.truststorePath = truststorePath;
    }

    @JsonProperty("PROXY_PORTA")
    public String getProxyPorta() {
        return proxyPorta;
    }

    @JsonProperty("PROXY_PORTA")
    public void setProxyPorta(String proxyPorta) {
        this.proxyPorta = proxyPorta;
    }

    @JsonProperty("LOG_DOCUM_ALT")
    public String getLogDocumAlt() {
        return logDocumAlt;
    }

    @JsonProperty("LOG_DOCUM_ALT")
    public void setLogDocumAlt(String logDocumAlt) {
        this.logDocumAlt = logDocumAlt;
    }

    @JsonProperty("TRUSTSTORE_PWD")
    public String getTruststorePwd() {
        return truststorePwd;
    }

    @JsonProperty("TRUSTSTORE_PWD")
    public void setTruststorePwd(String truststorePwd) {
        this.truststorePwd = truststorePwd;
    }

    @JsonProperty("ABILITA_LOG_SINGOLO")
    public String getAbilitaLogSingolo() {
        return abilitaLogSingolo;
    }

    @JsonProperty("ABILITA_LOG_SINGOLO")
    public void setAbilitaLogSingolo(String abilitaLogSingolo) {
        this.abilitaLogSingolo = abilitaLogSingolo;
    }

    @JsonProperty("KEYSTORE_SERVER_ALIAS")
    public String getKeystoreServerAlias() {
        return keystoreServerAlias;
    }

    @JsonProperty("KEYSTORE_SERVER_ALIAS")
    public void setKeystoreServerAlias(String keystoreServerAlias) {
        this.keystoreServerAlias = keystoreServerAlias;
    }

    @JsonProperty("TOKEN_JWT")
    public String getTokenJwt() {
        return tokenJwt;
    }

    @JsonProperty("TOKEN_JWT")
    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    @JsonProperty("KEYSTORE_SERVER_PATH")
    public String getKeystoreServerPath() {
        return keystoreServerPath;
    }

    @JsonProperty("KEYSTORE_SERVER_PATH")
    public void setKeystoreServerPath(String keystoreServerPath) {
        this.keystoreServerPath = keystoreServerPath;
    }

    @JsonProperty("LOG_LEVEL")
    public String getLogLevel() {
        return logLevel;
    }

    @JsonProperty("LOG_LEVEL")
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    @JsonProperty("PROXY_USER")
    public String getProxyUser() {
        return proxyUser;
    }

    @JsonProperty("PROXY_USER")
    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    @JsonProperty("PROXY_PWD")
    public String getProxyPwd() {
        return proxyPwd;
    }

    @JsonProperty("PROXY_PWD")
    public void setProxyPwd(String proxyPwd) {
        this.proxyPwd = proxyPwd;
    }

    @JsonProperty("VISUALIZATION")
    public String getVisualization() {
        return visualization;
    }

    @JsonProperty("VISUALIZATION")
    public void setVisualization(String visualization) {
        this.visualization = visualization;
    }

    @JsonProperty("REMOTO_SOC")
    public String getRemotoSoc() {
        return remotoSoc;
    }

    @JsonProperty("REMOTO_SOC")
    public void setRemotoSoc(String remotoSoc) {
        this.remotoSoc = remotoSoc;
    }

    @JsonProperty("AMBIENTE_WS")
    public String getAmbienteWs() {
        return ambienteWs;
    }

    @JsonProperty("AMBIENTE_WS")
    public void setAmbienteWs(String ambienteWs) {
        this.ambienteWs = ambienteWs;
    }

    @JsonProperty("BASIC_USER_ID")
    public String getBasicUserId() {
        return basicUserId;
    }

    @JsonProperty("BASIC_USER_ID")
    public void setBasicUserId(String basicUserId) {
        this.basicUserId = basicUserId;
    }

    @JsonProperty("FILE_PATH_OUT")
    public String getFilePathOut() {
        return filePathOut;
    }

    @JsonProperty("FILE_PATH_OUT")
    public void setFilePathOut(String filePathOut) {
        this.filePathOut = filePathOut;
    }

    @JsonProperty("CERT_CLIENT_PWD")
    public String getCertClientPwd() {
        return certClientPwd;
    }

    @JsonProperty("CERT_CLIENT_PWD")
    public void setCertClientPwd(String certClientPwd) {
        this.certClientPwd = certClientPwd;
    }

    @JsonProperty("VERSIONE_JAVA")
    public String getVersioneJava() {
        return versioneJava;
    }

    @JsonProperty("VERSIONE_JAVA")
    public void setVersioneJava(String versioneJava) {
        this.versioneJava = versioneJava;
    }

    @JsonProperty("FILE_URL")
    public String getFileUrl() {
        return fileUrl;
    }

    @JsonProperty("FILE_URL")
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @JsonProperty("LOG_DIM")
    public String getLogDim() {
        return logDim;
    }

    @JsonProperty("LOG_DIM")
    public void setLogDim(String logDim) {
        this.logDim = logDim;
    }

    @JsonProperty("LOG_DOCUM")
    public String getLogDocum() {
        return logDocum;
    }

    @JsonProperty("LOG_DOCUM")
    public void setLogDocum(String logDocum) {
        this.logDocum = logDocum;
    }

    @JsonProperty("REMOTO_PWD")
    public String getRemotoPwd() {
        return remotoPwd;
    }

    @JsonProperty("REMOTO_PWD")
    public void setRemotoPwd(String remotoPwd) {
        this.remotoPwd = remotoPwd;
    }

    @JsonProperty("USA_TOKEN_FISICO")
    public String getUsaTokenFisico() {
        return usaTokenFisico;
    }

    @JsonProperty("USA_TOKEN_FISICO")
    public void setUsaTokenFisico(String usaTokenFisico) {
        this.usaTokenFisico = usaTokenFisico;
    }

    @JsonProperty("URL_WS_BATCH_GEDO")
    public String getUrlWsBatchGedo() {
        return urlWsBatchGedo;
    }

    @JsonProperty("URL_WS_BATCH_GEDO")
    public void setUrlWsBatchGedo(String urlWsBatchGedo) {
        this.urlWsBatchGedo = urlWsBatchGedo;
    }

    @JsonProperty("URL_TOKEN")
    public String getUrlToken() {
        return urlToken;
    }

    @JsonProperty("URL_TOKEN")
    public void setUrlToken(String urlToken) {
        this.urlToken = urlToken;
    }

    @JsonProperty("TIMEOUT")
    public String getTimeout() {
        return timeout;
    }

    @JsonProperty("TIMEOUT")
    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    @JsonProperty("KEYSTORE_SERVER_PWD")
    public String getKeystoreServerPwd() {
        return keystoreServerPwd;
    }

    @JsonProperty("KEYSTORE_SERVER_PWD")
    public void setKeystoreServerPwd(String keystoreServerPwd) {
        this.keystoreServerPwd = keystoreServerPwd;
    }

    @JsonProperty("CERT_CLIENT_PATH")
    public String getCertClientPath() {
        return certClientPath;
    }

    @JsonProperty("CERT_CLIENT_PATH")
    public void setCertClientPath(String certClientPath) {
        this.certClientPath = certClientPath;
    }

    @JsonProperty("URL_WS")
    public String getUrlWs() {
        return urlWs;
    }

    @JsonProperty("URL_WS")
    public void setUrlWs(String urlWs) {
        this.urlWs = urlWs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Comuni.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("remotoUser");
        sb.append('=');
        sb.append(((this.remotoUser == null)?"<null>":this.remotoUser));
        sb.append(',');
        sb.append("certClientAlias");
        sb.append('=');
        sb.append(((this.certClientAlias == null)?"<null>":this.certClientAlias));
        sb.append(',');
        sb.append("initVector");
        sb.append('=');
        sb.append(((this.initVector == null)?"<null>":this.initVector));
        sb.append(',');
        sb.append("basicPwd");
        sb.append('=');
        sb.append(((this.basicPwd == null)?"<null>":this.basicPwd));
        sb.append(',');
        sb.append("secretKey");
        sb.append('=');
        sb.append(((this.secretKey == null)?"<null>":this.secretKey));
        sb.append(',');
        sb.append("logLevelAlt");
        sb.append('=');
        sb.append(((this.logLevelAlt == null)?"<null>":this.logLevelAlt));
        sb.append(',');
        sb.append("classe");
        sb.append('=');
        sb.append(((this.classe == null)?"<null>":this.classe));
        sb.append(',');
        sb.append("logNumDoc");
        sb.append('=');
        sb.append(((this.logNumDoc == null)?"<null>":this.logNumDoc));
        sb.append(',');
        sb.append("protocollo");
        sb.append('=');
        sb.append(((this.protocollo == null)?"<null>":this.protocollo));
        sb.append(',');
        sb.append("proxyIp");
        sb.append('=');
        sb.append(((this.proxyIp == null)?"<null>":this.proxyIp));
        sb.append(',');
        sb.append("remotoRep");
        sb.append('=');
        sb.append(((this.remotoRep == null)?"<null>":this.remotoRep));
        sb.append(',');
        sb.append("truststorePath");
        sb.append('=');
        sb.append(((this.truststorePath == null)?"<null>":this.truststorePath));
        sb.append(',');
        sb.append("proxyPorta");
        sb.append('=');
        sb.append(((this.proxyPorta == null)?"<null>":this.proxyPorta));
        sb.append(',');
        sb.append("logDocumAlt");
        sb.append('=');
        sb.append(((this.logDocumAlt == null)?"<null>":this.logDocumAlt));
        sb.append(',');
        sb.append("truststorePwd");
        sb.append('=');
        sb.append(((this.truststorePwd == null)?"<null>":this.truststorePwd));
        sb.append(',');
        sb.append("abilitaLogSingolo");
        sb.append('=');
        sb.append(((this.abilitaLogSingolo == null)?"<null>":this.abilitaLogSingolo));
        sb.append(',');
        sb.append("keystoreServerAlias");
        sb.append('=');
        sb.append(((this.keystoreServerAlias == null)?"<null>":this.keystoreServerAlias));
        sb.append(',');
        sb.append("tokenJwt");
        sb.append('=');
        sb.append(((this.tokenJwt == null)?"<null>":this.tokenJwt));
        sb.append(',');
        sb.append("keystoreServerPath");
        sb.append('=');
        sb.append(((this.keystoreServerPath == null)?"<null>":this.keystoreServerPath));
        sb.append(',');
        sb.append("logLevel");
        sb.append('=');
        sb.append(((this.logLevel == null)?"<null>":this.logLevel));
        sb.append(',');
        sb.append("proxyUser");
        sb.append('=');
        sb.append(((this.proxyUser == null)?"<null>":this.proxyUser));
        sb.append(',');
        sb.append("proxyPwd");
        sb.append('=');
        sb.append(((this.proxyPwd == null)?"<null>":this.proxyPwd));
        sb.append(',');
        sb.append("visualization");
        sb.append('=');
        sb.append(((this.visualization == null)?"<null>":this.visualization));
        sb.append(',');
        sb.append("remotoSoc");
        sb.append('=');
        sb.append(((this.remotoSoc == null)?"<null>":this.remotoSoc));
        sb.append(',');
        sb.append("ambienteWs");
        sb.append('=');
        sb.append(((this.ambienteWs == null)?"<null>":this.ambienteWs));
        sb.append(',');
        sb.append("basicUserId");
        sb.append('=');
        sb.append(((this.basicUserId == null)?"<null>":this.basicUserId));
        sb.append(',');
        sb.append("filePathOut");
        sb.append('=');
        sb.append(((this.filePathOut == null)?"<null>":this.filePathOut));
        sb.append(',');
        sb.append("certClientPwd");
        sb.append('=');
        sb.append(((this.certClientPwd == null)?"<null>":this.certClientPwd));
        sb.append(',');
        sb.append("versioneJava");
        sb.append('=');
        sb.append(((this.versioneJava == null)?"<null>":this.versioneJava));
        sb.append(',');
        sb.append("fileUrl");
        sb.append('=');
        sb.append(((this.fileUrl == null)?"<null>":this.fileUrl));
        sb.append(',');
        sb.append("logDim");
        sb.append('=');
        sb.append(((this.logDim == null)?"<null>":this.logDim));
        sb.append(',');
        sb.append("logDocum");
        sb.append('=');
        sb.append(((this.logDocum == null)?"<null>":this.logDocum));
        sb.append(',');
        sb.append("remotoPwd");
        sb.append('=');
        sb.append(((this.remotoPwd == null)?"<null>":this.remotoPwd));
        sb.append(',');
        sb.append("usaTokenFisico");
        sb.append('=');
        sb.append(((this.usaTokenFisico == null)?"<null>":this.usaTokenFisico));
        sb.append(',');
        sb.append("urlWsBatchGedo");
        sb.append('=');
        sb.append(((this.urlWsBatchGedo == null)?"<null>":this.urlWsBatchGedo));
        sb.append(',');
        sb.append("urlToken");
        sb.append('=');
        sb.append(((this.urlToken == null)?"<null>":this.urlToken));
        sb.append(',');
        sb.append("timeout");
        sb.append('=');
        sb.append(((this.timeout == null)?"<null>":this.timeout));
        sb.append(',');
        sb.append("keystoreServerPwd");
        sb.append('=');
        sb.append(((this.keystoreServerPwd == null)?"<null>":this.keystoreServerPwd));
        sb.append(',');
        sb.append("certClientPath");
        sb.append('=');
        sb.append(((this.certClientPath == null)?"<null>":this.certClientPath));
        sb.append(',');
        sb.append("urlWs");
        sb.append('=');
        sb.append(((this.urlWs == null)?"<null>":this.urlWs));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
