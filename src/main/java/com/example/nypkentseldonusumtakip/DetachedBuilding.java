package com.example.nypkentseldonusumtakip;


public class DetachedBuilding extends Building {
    
    private double gardenArea; // Bahçe alanı

    public DetachedBuilding(String address, PropertyOwner owner, double gardenArea) {
        super(address, owner); // Ana sınıfın (Building) yeni yapıcısını çağırıyoruz
        this.gardenArea = gardenArea;
    }

    // Abstract metodu dolduruyoruz (Polymorphism)
    @Override
    public double calculateCost() {
        // Örnek: Sabit 1.000.000 TL + Bahçe m2 başına 200 TL
        return 1_000_000 + (gardenArea * 200);
    }

    public double getGardenArea() { return gardenArea; }
    public void setGardenArea(double gardenArea) { this.gardenArea = gardenArea; }
}