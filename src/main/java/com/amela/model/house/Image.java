package com.amela.model.house;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long image_id;

    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    @NotNull
    private String sourcePath;
    
    @Size(min = 5, max = 500)
    private String des;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    public Image() {
    }

    public Image(String name, String sourcePath, String des, House house) {
        this.name = name;
        this.sourcePath = sourcePath;
        this.des = des;
        this.house = house;
    }

    public Image(long image_id, String name, String sourcePath, String des, House house) {
        this.image_id = image_id;
        this.name = name;
        this.sourcePath = sourcePath;
        this.des = des;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
