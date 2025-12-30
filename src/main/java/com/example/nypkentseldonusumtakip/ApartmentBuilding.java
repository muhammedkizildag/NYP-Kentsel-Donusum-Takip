package com.example.nypkentseldonusumtakip;

public class ApartmentBuilding extends Building {
    
    private int floorCount; // Kat sayısı
    private int totalUnits; // Daire sayısı

    public ApartmentBuilding(String address, PropertyOwner owner, int floorCount, int totalUnits) {
        super(address, owner); // Ana sınıfın yapıcısına nesneyi gönderiyoruz
        this.floorCount = floorCount;
        this.totalUnits = totalUnits;
    }

    // Abstract metodu dolduruyoruz (Polymorphism)
    @Override
    public double calculateCost() {
        // Örnek: Daire başı 500.000 TL. Eğer 5 kattan fazlaysa %20 zamlı.
        double cost = totalUnits * 500_000;
        
        if (floorCount > 5) {
            cost = cost * 1.20;
        }
        return cost;
    }

    public int getFloorCount() { return floorCount; }
    public void setFloorCount(int floorCount) { this.floorCount = floorCount; }

    public int getTotalUnits() { return totalUnits; }
    public void setTotalUnits(int totalUnits) { this.totalUnits = totalUnits; }
}