package com.example.nypkentseldonusumtakip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class db {

    // Veritabanı dosyasının adı. Proje klasöründe otomatik oluşur.
    private static final String DB_URL = "jdbc:sqlite:kentsel_donusum.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Bağlantıyı oluştur
            conn = DriverManager.getConnection(DB_URL);
            // System.out.println("Veritabanına bağlandı!");
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        }
        return conn;
    }

    // Tabloları oluşturacak metot (Program ilk açıldığında çalıştırılmalı)
    public static void createNewTable() {
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {

            // 1. Tablo: Kişiler (Müteahhit ve Mülk Sahipleri)
            String sqlPerson = "CREATE TABLE IF NOT EXISTS persons (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " first_name TEXT,\n"
                    + " last_name TEXT,\n"
                    + " phone TEXT,\n"
                    + " role TEXT,\n" // 'OWNER' veya 'CONTRACTOR'
                    + " company_name TEXT\n" // Sadece contractor ise dolu olur
                    + ");";
            stmt.execute(sqlPerson);

            // 2. Tablo: Binalar (owner_id ile kişiye bağlanır)
            String sqlBuilding = "CREATE TABLE IF NOT EXISTS buildings (\n"
                    + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " owner_id INTEGER,\n" // YENİ: Hangi kişiye ait? (Foreign Key)
                    + " building_type TEXT,\n"
                    + " address TEXT,\n"
                    + " risk_status TEXT,\n"
                    + " garden_area REAL,\n"
                    + " floor_count INTEGER,\n"
                    + " total_units INTEGER,\n"
                    + " FOREIGN KEY(owner_id) REFERENCES persons(id)\n" // İlişki
                    + ");";
            stmt.execute(sqlBuilding);

            System.out.println("Tablolar oluşturuldu.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Building> getBuildings() {

        ArrayList<Building> buildings = new ArrayList<Building>();

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return buildings;
    }
}