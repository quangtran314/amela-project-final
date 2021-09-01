package com.amela.model;

import com.amela.model.user.User;
import com.amela.model.house.House;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startDay;

    @NotNull
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endDay;

    @NotNull
    @Min(1)
    @Max(10)
    private int maxPerson;

    @NotNull
    private double totalPrice;
    
    @NotNull
    private LocalDate dateContractSign;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User user;

    public Contract() {
    }

    public Contract( LocalDate startDay, LocalDate endDay, int maxPerson, double totalPrice, LocalDate dateContractSign, House house, User user) {
        this.totalPrice = totalPrice;
        this.dateContractSign = dateContractSign;
        this.startDay = startDay;
        this.endDay = endDay;
        this.maxPerson = maxPerson;
        this.house = house;
        this.user = user;
    }

    public Contract(long id, LocalDate startDay, LocalDate endDay, int maxPerson, double totalPrice, LocalDate dateContractSign, House house, User user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.dateContractSign = dateContractSign;
        this.startDay = startDay;
        this.endDay = endDay;
        this.maxPerson = maxPerson;
        this.house = house;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public int getMaxPerson() {
        return maxPerson;
    }

    public void setMaxPerson(int maxPerson) {
        this.maxPerson = maxPerson;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDateContractSign() {
        return dateContractSign;
    }

    public void setDateContractSign(LocalDate dateContractSign) {
        this.dateContractSign = dateContractSign;
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
