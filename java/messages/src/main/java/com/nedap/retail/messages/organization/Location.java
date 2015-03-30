package com.nedap.retail.messages.organization;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Location implements Serializable {

    private static final long serialVersionUID = 1096488161535906856L;

    private String id;
    @JsonProperty("parent_id")
    @org.codehaus.jackson.annotate.JsonProperty("parent_id")
    private String parentId;
    private LocationType type;
    private LocationSubType subtype;
    private String name;
    @JsonProperty("store_code")
    @org.codehaus.jackson.annotate.JsonProperty("store_code")
    private String storeCode;
    private Address address;
    private List<Location> children;

    public Location() {
    }

    public Location(final String id, final String parentId, final LocationType type, final LocationSubType subtype,
            final String name, final String storeCode, final Address address, final List<Location> children) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.subtype = subtype;
        this.name = name;
        this.storeCode = storeCode;
        this.address = address;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(final LocationType type) {
        this.type = type;
    }

    public LocationSubType getSubtype() {
        return subtype;
    }

    public void setSubtype(final LocationSubType subtype) {
        this.subtype = subtype;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(final String storeCode) {
        this.storeCode = storeCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    public List<Location> getChildren() {
        return children;
    }

    public void setChildren(final List<Location> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return StringUtils.join(new Object[] {"location ", id, parentId, type, subtype, name, address,
                "children(size)", children == null ? null : children.size()}, ";");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (address == null ? 0 : address.hashCode());
        result = prime * result + (children == null ? 0 : children.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
        result = prime * result + (name == null ? 0 : name.hashCode());
        result = prime * result + (parentId == null ? 0 : parentId.hashCode());
        result = prime * result + (storeCode == null ? 0 : storeCode.hashCode());
        result = prime * result + (subtype == null ? 0 : subtype.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        if (children == null) {
            if (other.children != null) {
                return false;
            }
        } else if (!children.equals(other.children)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (parentId == null) {
            if (other.parentId != null) {
                return false;
            }
        } else if (!parentId.equals(other.parentId)) {
            return false;
        }
        if (storeCode == null) {
            if (other.storeCode != null) {
                return false;
            }
        } else if (!storeCode.equals(other.storeCode)) {
            return false;
        }
        if (subtype != other.subtype) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}
