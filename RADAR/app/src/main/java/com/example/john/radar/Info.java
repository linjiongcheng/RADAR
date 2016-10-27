package com.example.john.radar;

import java.io.Serializable;

public class Info implements Serializable {
    private String name;
    private String tele;
    private String latitude;
    private String longitude;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name=name;
    }

    public String getTele() {
        return this.tele;
    }
    public void setTele(String tele) {
        this.tele=tele;
    }

    public String getLatitude() {
        return this.latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude=latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude=longitude;
    }
}
