package com.amela.model.user;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tenant")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tenant_id;

    @NotNull
    private String full_name;

    @NotNull
    private String PIN;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 20)
    private String phone_number;

    @NotNull
    @Email
    private String email;

    @OneToMany(targetEntity = Contract.class)
    private List<Contract> contracts;

    public Tenant() {
    }

    public Tenant(String full_name, String PIN, String address, String phone_number, String email, List<Contract> contracts) {
        this.full_name = full_name;
        this.PIN = PIN;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.contracts = contracts;
    }

    public Tenant(long tenant_id, String full_name, String PIN, String address, String phone_number, String email, List<Contract> contracts) {
        this.tenant_id = tenant_id;
        this.full_name = full_name;
        this.PIN = PIN;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
        this.contracts = contracts;
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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
}
