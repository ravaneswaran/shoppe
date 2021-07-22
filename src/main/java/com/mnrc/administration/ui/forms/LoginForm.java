package com.mnrc.administration.ui.forms;

import com.mnrc.administration.enums.UserStatus;

import javax.validation.constraints.NotEmpty;

public class LoginForm extends BaseForm {

    private String firstName;

    private String middleInitial;

    private String lastName;

    @NotEmpty(message = "Email id should not be empty")
    private String emailId;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    private String userId;

    private String status;

    private String type;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isUserBlocked() {
        return UserStatus.LOCKED.toString().equals(this.status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}