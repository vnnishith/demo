package com.example.demo.pojo;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class PhoneRequest {


    @NotBlank(message="Preferred Phone Number cannot be missing or empty")
    String phoneNumber;


    @NotBlank(message="Phone Name cannot be missing or empty")
    String phoneName;


    @Pattern(regexp="^(IPHONE|ANDROID|DESK_PHONE|SOFT_PHONE)$",message="Invalid Phone Model")
    @NotBlank(message="Phone Model cannot be missing or empty")
    String phoneModel;
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String preferredPhoneNumber) {
        this.phoneNumber = preferredPhoneNumber;
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

}