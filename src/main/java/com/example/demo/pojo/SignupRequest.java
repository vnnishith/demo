package com.example.demo.pojo;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class SignupRequest {


    @NotBlank(message="Username cannot be missing or empty")
    String userName;

    @NotBlank(message="Password cannot be missing or empty")
    String password;


    @NotBlank(message="confirmPassword cannot be missing or empty")
    String confirmPassword;


    @NotBlank(message="Email cannot be missing or empty")
    String emailAddress;


    String preferredPhoneNumber;


    String phoneName;


    @Pattern(regexp="^(IPHONE|ANDROID|DESK_PHONE|SOFT_PHONE)$",message="Invalid Phone Model")
    String phoneModel;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPreferredPhoneNumber() {
        return preferredPhoneNumber;
    }

    public void setPreferredPhoneNumber(String preferredPhoneNumber) {
        this.preferredPhoneNumber = preferredPhoneNumber;
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