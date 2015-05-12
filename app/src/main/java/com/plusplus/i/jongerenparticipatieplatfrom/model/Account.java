package com.plusplus.i.jongerenparticipatieplatfrom.model;

import java.util.Date;

/**
 * Created by Shenno on 11/04/2015.
 */
public class Account {
    private String FullName;
    private String Email;
    private String Password;
    private String ConfirmPassword;
    private Date BirthDate;
    private int ZipCode;

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }
}
