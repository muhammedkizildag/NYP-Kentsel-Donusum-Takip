package com.example.nypkentseldonusumtakip;


import java.util.ArrayList;
import java.util.List;

public class PropertyOwner extends Person {
    
    // Mülk sahibinin birden fazla binası olabilir
    private List<Building> ownedBuildings; 

    public PropertyOwner(String firstName, String lastName, String phoneNumber) {
        super(firstName, lastName, phoneNumber);
        this.ownedBuildings = new ArrayList<>(); // Listeyi başlatıyoruz
    }

    // Listeye bina ekleme metodu
    public void addBuilding(Building building) {
        this.ownedBuildings.add(building);
        // İstersen burada binanın sahibini de 'this' (bu kişi) olarak güncelleyebilirsin.
        building.setOwner(this);
    }

    public List<Building> getOwnedBuildings() { return ownedBuildings; }
}