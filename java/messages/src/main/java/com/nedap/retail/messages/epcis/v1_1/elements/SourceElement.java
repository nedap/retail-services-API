package com.nedap.retail.messages.epcis.v1_1.elements;

public class SourceElement {

    public String type;

    public String source;

    public SourceElement() {
    }

    public SourceElement(final String type, final String source) {
        this.type = type;
        this.source = source;
    }

    @Override
    public String toString() {
        return "SourceElement: type=" + type + ",source=" + source;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (source == null ? 0 : source.hashCode());
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
        final SourceElement other = (SourceElement) obj;
        if (source == null) {
            if (other.source != null) {
                return false;
            }
        } else if (!source.equals(other.source)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
