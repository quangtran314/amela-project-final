package com.amela.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long image_id;
    private String name;
    private String sourcePath;
    private String desImage;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    public Image() {
    }

    public Image(String name, String sourcePath, String desImage, House house) {
        this.name = name;
        this.sourcePath = sourcePath;
        this.desImage = desImage;
        this.house = house;
    }

    public Image(long image_id, String name, String sourcePath, String desImage, House house) {
        this.image_id = image_id;
        this.name = name;
        this.sourcePath = sourcePath;
        this.desImage = desImage;
        this.house = house;
    }

    public long getImage_id() {
        return image_id;
    }

    public void setImage_id(long image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String source) {
        this.sourcePath = source;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getDesImage() {
        return desImage;
    }

    public void setDesImage(String desImage) {
        this.desImage = desImage;
    }
}
