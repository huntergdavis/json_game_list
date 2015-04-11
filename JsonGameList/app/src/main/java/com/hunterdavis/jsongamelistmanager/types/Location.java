package com.hunterdavis.jsongamelistmanager.types;

import java.util.HashMap;
import java.util.Map;

public class Location extends ObjectWithAdditionalProperty{

    private String address;
    private String postalCode;
    private String city;
    private String countryCode;
    private String region;


    @Override
    public String toString() {
        return address + "\n" +
                postalCode + "\n" +
                city + ", " + region + "\n" +
                countryCode;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode The postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param countryCode The countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return The region
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region The region
     */
    public void setRegion(String region) {
        this.region = region;
    }


}
