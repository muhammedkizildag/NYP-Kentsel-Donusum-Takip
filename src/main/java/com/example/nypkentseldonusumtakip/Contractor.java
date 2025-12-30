package com.example.nypkentseldonusumtakip;

public class Contractor extends Person {
    private String companyName;

    public Contractor(String firstName, String lastName, String phoneNumber, String companyName) {
        super(firstName, lastName, phoneNumber);
        this.companyName = companyName;
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}