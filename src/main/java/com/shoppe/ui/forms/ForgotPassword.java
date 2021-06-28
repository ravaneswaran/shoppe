package com.shoppe.ui.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotPassword {

    private String userId;

    @NotEmpty(message = "{forgot.password.emailid.empty.error.message}")
    @Email(message = "{forgot.password.emailid.invalid.format.error.message}")
    private String emailId;

    private String password;

    private String firstName;
    private String middleInitial;
    private String lastName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
