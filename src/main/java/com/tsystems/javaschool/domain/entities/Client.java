package com.tsystems.javaschool.domain.entities;

import com.tsystems.javaschool.facade.dto.ClientDTO;
import com.tsystems.javaschool.facade.dto.ContractDTO;
import com.tsystems.javaschool.facade.dto.RoleDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class Client implements Serializable{

    @Id
    @Column(name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PASSPORT")
    private String passport;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(mappedBy = "client")
    private List<Contract> contracts = new ArrayList<Contract>(0);
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "CLIENT_ROLES",
            joinColumns = @JoinColumn(name = "CLIENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles = new ArrayList<Role>(0);

    public Client() {
    }

    public Client(String firstName, String lastName, Date dateOfBirth, String address,
                  String passport, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.passport = passport;
        this.email = email;
        this.password = password;
    }

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

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> numbers) {
        this.contracts = numbers;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public ClientDTO toDTO(){
        String dateOfBirth = "";
        if (getDateOfBirth() != null){
            Date date = getDateOfBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            dateOfBirth = sdf.format(date);
        }
        ClientDTO clientDTO = new ClientDTO(getFirstName(), getLastName(),
                dateOfBirth, getAddress(), getPassport(), getEmail());
        List<Contract> contracts = getContracts();
        List<ContractDTO> contractDTOs = new ArrayList<ContractDTO>();
        for (Contract contract : contracts) {
            contractDTOs.add(contract.toDTO(clientDTO));
        }
        clientDTO.setContracts(contractDTOs);
        List<Role> roles = getRoles();
        List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
        for (Role role : roles) {
            RoleDTO roleDTO = new RoleDTO(role.getRole());
            roleDTOs.add(roleDTO);
        }
        clientDTO.setRoles(roleDTOs);
        return clientDTO;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}