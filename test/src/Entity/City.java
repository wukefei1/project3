package Entity;

import java.io.Serializable;

public class City implements Serializable {
    private String geoNameID;
    private String asciiName;

    public String getGeoNameID() {
        return geoNameID;
    }

    public void setGeoNameID(String CountryISO) {
        this.geoNameID = CountryISO;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public void setAsciiName(String Country_RegionName) {
        this.asciiName = Country_RegionName;
    }
}