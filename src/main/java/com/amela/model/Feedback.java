package com.amela.model;

import com.amela.model.house.House;
import com.amela.model.user.User;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedback_id;

    @NotNull
    @Size(min = 5, max = 1000)
    private String comment;

    @NotNull
    @Min(1)
    @Max(5)
    private int rate;

    private LocalDate amt_date;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Feedback() {
    }

    public Feedback(String comment, int rate, LocalDate amt_date, House house, User owner) {
        this.comment = comment;
        this.rate = rate;
        this.amt_date = amt_date;
        this.house = house;
        this.owner = owner;
    }

    public Feedback(long feedback_id, String comment, int rate, LocalDate amt_date, House house, User owner) {
        this.feedback_id = feedback_id;
        this.comment = comment;
        this.rate = rate;
        this.amt_date = amt_date;
        this.house = house;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
