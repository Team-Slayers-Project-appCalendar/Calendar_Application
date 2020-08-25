package com.example.calendar_application;

public class Events {
    int ID;
    String EVENT,DESCRIPTION,LOCATION,TIME,DATE,MONTH,YEAR;

    public Events(int ID, String EVENT,String DESCRIPTION, String LOCATION, String TIME, String DATE, String MONTH, String YEAR) {
        this.ID = ID;
        this.EVENT = EVENT;
        this.DESCRIPTION = DESCRIPTION;
        this.LOCATION = LOCATION;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MONTH = MONTH;
        this.YEAR = YEAR;
    }

    public int getID() {return  ID;}
    public void setID(int ID) {this.ID = ID;}

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMONTH() {
        return MONTH;
    }

    public void setMONTH(String MONTH) {
        this.MONTH = MONTH;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

}

