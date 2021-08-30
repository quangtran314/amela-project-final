package com.amela.form;

import com.amela.model.house.House;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Entity
@Table(name = "image_form")
public class ImageForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long image_id;

    @NotNull
    @Size(min = 5, max = 20)
    private String name;

    @NotNull
    private MultipartFile[] sourcePath;

    @Size(min = 5, max = 500)
    private String des;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    public ImageForm() {
    }

    public ImageForm(String name, MultipartFile[] sourcePath, String des, House house) {
        this.name = name;
        this.sourcePath = sourcePath;
        this.des = des;
        this.house = house;
    }

    public ImageForm(long image_id, String name, MultipartFile[] sourcePath, String des, House house) {
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

    public MultipartFile[] getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(MultipartFile[] sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
