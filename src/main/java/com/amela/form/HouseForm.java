package com.amela.form;

import com.amela.model.house.Image;
import com.amela.model.house.Type;
import com.amela.model.user.User;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

public class HouseForm {

    @NotNull
    @Size(min = 5, max = 20)
    private String house_name;

    @NotNull
    @Size(min = 5, max = 100)
    private String address;

    @Size(min = 5, max = 500)
    private String des;

    @NotNull
    @Min(1)
    private int numBedrooms;

    @NotNull
    @Min(1)
    private int numBathrooms;

    @NotNull
    private float price;

    @Min(1)
    @Max(5)
    private int cancelableTime = 1;

    @OneToMany(targetEntity = Image.class)
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private MultipartFile sourcePath;

    public HouseForm() {
    }

    public HouseForm(String house_name, String address, int numBedrooms, int numBathrooms, String des, float price, Type type ,MultipartFile sourcePath, User owner, int cancelableTime) {
        this.house_name = house_name;
        this.address = address;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.des = des;
        this.price = price;
        this.type = type;
        this.sourcePath = sourcePath;
        this.owner = owner;
        this.cancelableTime = cancelableTime;
    }

    public MultipartFile getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(MultipartFile sourcePath) {
        this.sourcePath = sourcePath;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getCancelableTime() {
        return cancelableTime;
    }

    public void setCancelableTime(int cancelableTime) {
        this.cancelableTime = cancelableTime;
    }
}
