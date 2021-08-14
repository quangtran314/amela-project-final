package com.amela.model;

import javax.persistence.*;

@Entity
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tenant_id;
    private String full_name;
    private String PIN;
    private String address;
    private String phone_number;
    private String email;

    public Tenant() {
    }

    public Tenant(long tenant_id, String full_name, String PIN, String address, String phone_number, String email) {
        this.tenant_id = tenant_id;
        this.full_name = full_name;
        this.PIN = PIN;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    public long getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(long tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
