package Entity;

import java.io.Serializable;

public class Country implements Serializable {
    private String countryISO;
    private String country_RegionName;

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String CountryISO) {
        this.countryISO = CountryISO;
    }

    public String getCountry_RegionName() {
        return country_RegionName;
    }

    public void setCountry_RegionName(String Country_RegionName) {
        this.country_RegionName = Country_RegionName;
    }
}