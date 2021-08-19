package com.amela.model;

import com.amela.model.house.House;
import com.amela.model.user.Tenant;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedback_id;

    @NotNull
    private String comment;

    @Min(1)
    @Max(5)
    private int rate;

    private LocalDate amt_date;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    public Feedback() {
    }

    public Feedback(String comment, int rate, LocalDate amt_date, House house, Tenant tenant) {
        this.comment = comment;
        this.rate = rate;
        this.amt_date = amt_date;
        this.house = house;
        this.tenant = tenant;
    }

    public Feedback(long feedback_id, String comment, int rate, LocalDate amt_date, House house, Tenant tenant) {
        this.feedback_id = feedback_id;
        this.comment = comment;
        this.rate = rate;
        this.amt_date = amt_date;
        this.house = house;
        this.tenant = tenant;
    }

    public long getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(long feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public LocalDate getAmt_date() {
        return amt_date;
    }

    public void setAmt_date(LocalDate amt_date) {
        this.amt_date = amt_date;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
