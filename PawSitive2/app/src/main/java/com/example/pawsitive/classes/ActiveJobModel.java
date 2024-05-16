package com.example.pawsitive.classes;

public class ActiveJobModel {
    private String startDate;
    private String endDate;

    public ActiveJobModel(String startDate, String endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
