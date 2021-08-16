package com.amela.model.house;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "house_type")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long type_id;

    @NotNull
    @Size(min = 5, max = 20)
    @UniqueElements
    private String name;

    @OneToMany(targetEntity = House.class)
    List<House> houses;

    public Type() {
    }

    public Type(long type_id, String name) {
        this.type_id = type_id;
        this.name = name;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }
}
