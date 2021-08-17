package com.amela.model;

import com.amela.model.user.User;
import com.amela.model.house.House;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_id;

    @NotNull
    private LocalDate start_day;

    @NotNull
    private LocalDate end_day;

    @NotNull
    private int max_person;

    @Size(min = 3)
    private String term;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User user;

    public Contract() {
    }

    public Contract( LocalDate start_day, LocalDate end_day, int max_person, String term, House house, User user) {

        this.start_day = start_day;
        this.end_day = end_day;
        this.max_person = max_person;
        this.term = term;
        this.house = house;
        this.user = user;
    }

    public Contract(long contract_id, LocalDate start_day, LocalDate end_day, int max_person, String term, House house, User user) {
        this.contract_id = contract_id;
        this.start_day = start_day;
        this.end_day = end_day;
        this.max_person = max_person;
        this.term = term;
        this.house = house;
        this.user = user;
    }

    public long getContract_id() {
        return contract_id;
    }

    public void setContract_id(long contract_id) {
        this.contract_id = contract_id;
    }

    public LocalDate getStart_day() {
        return start_day;
    }

    public void setStart_day(LocalDate start_day) {
        this.start_day = start_day;
    }

    public LocalDate getEnd_day() {
        return end_day;
    }

    public void setEnd_day(LocalDate end_day) {
        this.end_day = end_day;
    }

    public int getMax_person() {
        return max_person;
    }

    public void setMax_person(int max_person) {
        this.max_person = max_person;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public User getTenant() {
        return user;
    }

    public void setTenant(User user) {
        this.user = user;
    }
}
