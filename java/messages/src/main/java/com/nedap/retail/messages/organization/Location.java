package com.nedap.retail.messages.organization;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Location implements Serializable {

    private static final long serialVersionUID = 1096488161535906856L;

    public String id;

    @JsonProperty("parent_id")
    public String parentId;

    public LocationType type;
    public LocationSubType subtype;
    public String name;

    @JsonProperty("store_code")
    public String storeCode;

    public Address address;
    public List<Location> children;

    @JsonProperty("foreign_locations")
    public List<Location> foreignLocations;

    public Location() {
    }

    public Location(final String id, final String parentId, final LocationType type, final LocationSubType subtype,
            final String name, final String storeCode, final Address address, final List<Location> children) {
        this(id, parentId, type, subtype, name, storeCode, address, children, null);
    }

    public Location(final String id, final String parentId, final LocationType type, final LocationSubType subtype,
            final String name, final String storeCode, final Address address, final List<Location> children,
            final List<Location> foreignLocations) {
        this.id = id;
        this.parentId = parentId;
        this.type = type;
        this.subtype = subtype;
        this.name = name;
        this.storeCode = storeCode;
        this.address = address;
        this.children = children;
        this.foreignLocations = foreignLocations;
    }

    @Override
    public String toString() {
        return StringUtils.join(new Object[] {"location ", id, parentId, type, subtype, name, address, "children(size)",
                children == null ? null : children.size(), "foreign locations(size)",
                foreignLocations == null ? null : foreignLocations.size()}, ";");
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
        result = prime * result + (foreignLocations == null ? 0 : foreignLocations.hashCode());
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
        if (foreignLocations == null) {
            if (other.foreignLocations != null) {
                return false;
            }
        } else if (!foreignLocations.equals(other.foreignLocations)) {
            return false;
        }
        return true;
    }
}
