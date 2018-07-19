package com.tim5.demo.entity;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.*;
@Entity
public class Hotel
{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel(String name, Double longitude, Double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Hotel() {
    }

    @Id
    @GeneratedValue
    @Column(name = "hotel_id")
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    String name;
    Double longitude;
    Double latitude;

}
