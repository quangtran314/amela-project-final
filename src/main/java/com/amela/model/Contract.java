package com.amela.model;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long contract_id;
    private long tenant_id;
    private long house_id;
    private LocalDate start_day;
    private LocalDate end_day;
    private int max_person;
    private String term;

    public Contract() {
    }

    public Contract(long contract_id, long tenant_id, long house_id, LocalDate start_day, LocalDate end_day, int max_person, String term) {
        this.contract_id = contract_id;
        this.tenant_id = tenant_id;
        this.house_id = house_id;
        this.start_day = start_day;
        this.end_day = end_day;
        this.max_person = max_person;
        this.term = term;
    }

    public long getContract_id() {
        return contract_id;
    }

    public void setContract_id(long contract_id) {
        this.contract_id = contract_id;
    }

    public long getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(long tenant_id) {
        this.tenant_id = tenant_id;
    }

    public long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(long house_id) {
        this.house_id = house_id;
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
}
