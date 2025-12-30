package com.example.nypkentseldonusumtakip;

// Abstract olduğu için bu sınıftan tek başına nesne üretilemez (new Building() diyemeyiz)
public abstract class Building {

    // Değişkenler (Encapsulation için private)
    private int id;
    private String address;
    private PropertyOwner owner;
    private String riskStatus; // Örn: "RISKLI", "GUVENLI", "BELIRSIZ"

    // Constructor
   public Building(String address, PropertyOwner owner) {
        this.address = address;
        this.owner = owner;
        this.riskStatus = "BELIRSIZ";
    }

    // --- SOYUT METOT ---
    // Her bina maliyetini hesaplamak zorundadır ama her bina farklı hesaplar.
    public abstract double calculateCost();

    // --- Getter ve Setter Metotları ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PropertyOwner getOwner() { return owner; }
    public void setOwner(PropertyOwner owner) { this.owner = owner; }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    // Listede güzel görünsün diye toString metodunu düzenleyelim
    @Override
    public String toString() {
        // Sahibin adını çekmek için nesne üzerinden gidiyoruz
        return address + " (Sahibi: " + owner.getFullName() + ")";
    }
}