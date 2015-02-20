package com.tsystems.javaschool.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client implements Serializable{

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String passport;
    private String email;
    private String password;

    @OneToMany(mappedBy = "client")
    private List<Contract> numbers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String surname) {
        this.lastName = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date birthday) {
        this.dateOfBirth = birthday;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Contract> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Contract> numbers) {
        this.numbers = numbers;
    }
}