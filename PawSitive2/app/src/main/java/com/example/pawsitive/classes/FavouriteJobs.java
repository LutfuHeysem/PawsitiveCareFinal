package com.example.pawsitive.classes;

public class FavouriteJobs extends Job{

    private String price, location, locationProperties, experienceLevel, spokenLanguages, dates;

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getLocationProperties() {
        return locationProperties;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLocationProperties(String locationProperties) {
        this.locationProperties = locationProperties;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDates() {
        return dates;
    }


}
