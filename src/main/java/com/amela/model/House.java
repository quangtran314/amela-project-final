package com.amela.model;

import javax.persistence.*;

@Entity
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long house_id;
    private long type_id;
    private String address;
    private long image_id;
    private String des;
    private float price;

    public House() {
    }

    public House(long house_id, long type_id, String address, long image_id, String des, float price) {
        this.house_id = house_id;
        this.type_id = type_id;
        this.address = address;
        this.image_id = image_id;
        this.des = des;
        this.price = price;
    }

    public long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(long house_id) {
        this.house_id = house_id;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
