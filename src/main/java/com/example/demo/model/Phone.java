package com.example.demo.model;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "PHONES")
public class Phone {

    @Id
    @org.hibernate.annotations.Type(type = "uuid-char")
    private UUID phoneId;
    private String phoneName;
    private String phoneModel;
    @Column(unique=true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    public UUID getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(UUID phoneId) {
        this.phoneId = phoneId;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void setUser(User user) {
        this.user = user;
    }

}