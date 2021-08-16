package com.amela.model.house;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long house_id;
    @NotNull
    @Size(min = 5, max = 20)
    private String house_name;
    @NotNull
    @Size(min = 5, max = 100)
    private String address;
    @Size(min = 5, max = 500)
    private String des;
    @NotNull
    private int numBedrooms;
    @NotNull
    private int numBathrooms;
    @NotNull
    private float price;

    @OneToMany(targetEntity = Image.class)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    public House() {
    }

    public House(String house_name, String address, int numBedrooms, List<Image> images, String des, float price, Type type) {
        this.house_name = house_name;
        this.address = address;
        this.numBedrooms = numBedrooms;
        this.images = images;
        this.des = des;
        this.price = price;
        this.type = type;
    }

    public House(long house_id, String house_name, String address, int numBedrooms, List<Image> images, String des, float price, Type type) {
        this.house_id = house_id;
        this.house_name = house_name;
        this.address = address;
        this.numBedrooms = numBedrooms;
        this.images = images;
        this.des = des;
        this.price = price;
        this.type = type;
    }

    public long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(long house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
