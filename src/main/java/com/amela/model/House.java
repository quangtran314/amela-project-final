package com.amela.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long house_id;
    private long type_id;
    private String address;
    private String desHouse;
    private int numBedrooms;
    private int numBathrooms;
    private float price;

    @OneToMany(targetEntity = Image.class)
    private List<Image> images;

    public House() {
    }

    public House(long type_id, String address, int numBedrooms, List<Image> images, String desHouse, float price) {
        this.type_id = type_id;
        this.address = address;
        this.numBedrooms = numBedrooms;
        this.images = images;
        this.desHouse = desHouse;
        this.price = price;
    }

    public House(long house_id, long type_id, String address, int numBedrooms, List<Image> images, String desHouse, float price) {
        this.house_id = house_id;
        this.type_id = type_id;
        this.address = address;
        this.numBedrooms = numBedrooms;
        this.images = images;
        this.desHouse = desHouse;
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

    public List<Image> getImages() { return images; }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getDesHouse() {
        return desHouse;
    }

    public void setDesHouse(String des) {
        this.desHouse = des;
    }

    public int getNumBedrooms() {
        return numBedrooms;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public int getNumBathrooms() {
        return numBathrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
