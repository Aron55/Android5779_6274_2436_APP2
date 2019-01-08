package com.arons.android5779_6274_2436_app2.Model.Entities.Classes;

public class Driver {
    private String LastName;
    private String FirstName;
    private String IdNumber;
    private String PhoneNumber;
    private String MailAddress;
    private String CreditCardNumber;

    public Driver(String lastName, String firstName, String idNumber, String phoneNumber, String mailAddress, String creditCardNumber) {
        LastName = lastName;
        FirstName = firstName;
        IdNumber = idNumber;
        PhoneNumber = phoneNumber;
        MailAddress = mailAddress;
        CreditCardNumber = creditCardNumber;
    }

    public Driver(String firstName, String lastName) {
        LastName = lastName;
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getIdNumber() {
        return IdNumber;
    }

    public void setIdNumber(String idNumber) {
        IdNumber = idNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getMailAddress() {
        return MailAddress;
    }

    public void setMailAddress(String mailAddress) {
        MailAddress = mailAddress;
    }

    public String getCreditCardNumber() {
        return CreditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        CreditCardNumber = creditCardNumber;
    }
}
