package com.example.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @org.hibernate.annotations.Type(type="uuid-char")
    private UUID userId;
    @Column(unique=true)
    private String userName;
    @JsonIgnore
    private String password;
    @Column(unique=true)
    private String emailAddress;

    private String preferredPhoneNumber;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Phone> phones;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferredPhoneNumber() {
        return preferredPhoneNumber;
    }

    public void setPreferredPhoneNumber(String preferredPhoneNumber) {
        this.preferredPhoneNumber = preferredPhoneNumber;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", userName='" + userName + '\'' +
                ", password'" + password + '\'' +
                ", preferredPhoneNumber='" + preferredPhoneNumber + '\'' +
                '}';
    }
}

