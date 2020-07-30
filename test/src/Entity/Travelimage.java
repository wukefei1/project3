package Entity;

import java.io.Serializable;
import java.sql.Date;

public class Travelimage implements Serializable {
    private int id;
    private String title;
    private String description;
    private String countryISO;
    private String cityCode;
    private String content;
    private String path;
    private String cityName;
    private String countryName;
    private String author;
    private int likeNum;
    private boolean isFavourite;
    private Date submitTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        this.title = Title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public String getCountryISO() {
        return countryISO;
    }

    public void setCountryISO(String CountryISO) {
        this.countryISO = CountryISO;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String CityCode) {
        this.cityCode = CityCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String Content) {
        this.content = Content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String CityName) {
        this.cityName = CityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String CountryName) {
        this.countryName = CountryName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public boolean getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
}