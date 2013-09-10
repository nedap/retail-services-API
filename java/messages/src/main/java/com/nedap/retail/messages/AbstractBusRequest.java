package com.nedap.retail.messages;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL) //  to prevent writing of null properties
public abstract class AbstractBusRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String id;
    protected String namespace;
    protected String version;
    protected String type;
    /**
     * OAuth 2.0 access token.
     */
    @JsonProperty("access_token")
    protected String accessToken;

    /**
     * When binding JSON data into Java objects, all JSON properties need to map to properties of Java object to bind to.
     * If an unknown property is encountered, an exception is thrown. But in this case whe want to ignore unknown
     * JSON properties when reading content to bind to existing Java objects.
     *
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    abstract public Object getPayload();

    /**
     * JsonIgnore is added to make sure that not both "access_token" and "accessToken" are serialized.
     *
     * @return OAuth 2.0 access token.
     */
    @JsonIgnore
    public String getAccessToken() {
        return accessToken;
    }

    @JsonIgnore
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Creates a routing key in the form off: <namespace>.<version>.<type>
     */
    @JsonIgnore
    public String getRoutingKey() {
        final StringBuilder sb = new StringBuilder();
        sb.append(namespace).append(".").append(version).append(".").append(type);
        return sb.toString();
    }
}
