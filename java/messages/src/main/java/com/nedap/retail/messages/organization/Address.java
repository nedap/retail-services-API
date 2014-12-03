package com.nedap.retail.messages.organization;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Address {

    private String address;
    private String zipcode;
    private String city;
    @JsonProperty("country_code")
    @org.codehaus.jackson.annotate.JsonProperty("country_code")
    private String countryCode;
    private Double latitude;
    private Double longitude;

    public Address() {
    }

    public Address(final String address, final String zipcode, final String city, final String countryCode,
            final Double latitude, final Double longitude) {
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.countryCode = countryCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(final String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    @JsonIgnore
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(final String countryCode) {
        this.countryCode = countryCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (address == null ? 0 : address.hashCode());
        result = prime * result + (city == null ? 0 : city.hashCode());
        result = prime * result + (countryCode == null ? 0 : countryCode.hashCode());
        result = prime * result + (latitude == null ? 0 : latitude.hashCode());
        result = prime * result + (longitude == null ? 0 : longitude.hashCode());
        result = prime * result + (zipcode == null ? 0 : zipcode.hashCode());
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
        final Address other = (Address) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        if (city == null) {
            if (other.city != null) {
                return false;
            }
        } else if (!city.equals(other.city)) {
            return false;
        }
        if (countryCode == null) {
            if (other.countryCode != null) {
                return false;
            }
        } else if (!countryCode.equals(other.countryCode)) {
            return false;
        }
        if (latitude == null) {
            if (other.latitude != null) {
                return false;
            }
        } else if (!latitude.equals(other.latitude)) {
            return false;
        }
        if (longitude == null) {
            if (other.longitude != null) {
                return false;
            }
        } else if (!longitude.equals(other.longitude)) {
            return false;
        }
        if (zipcode == null) {
            if (other.zipcode != null) {
                return false;
            }
        } else if (!zipcode.equals(other.zipcode)) {
            return false;
        }
        return true;
    }
}
