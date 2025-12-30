package com.example.nypkentseldonusumtakip;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class db {

    private static final String DB_URL = "jdbc:sqlite:kentsel_donusum.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
        }
        return conn;
    }

    // Tabloları oluştur
    public static void createNewTable() {
        String sqlOwners = """
            CREATE TABLE IF NOT EXISTS owners (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                phone TEXT NOT NULL
            );
        """;

        String sqlContractors = """
            CREATE TABLE IF NOT EXISTS contractors (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                phone TEXT NOT NULL,
                company_name TEXT NOT NULL
            );
        """;

        String sqlBuildings = """
            CREATE TABLE IF NOT EXISTS buildings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                building_type TEXT NOT NULL,
                address TEXT NOT NULL,
                owner_id INTEGER NOT NULL,
                risk_status TEXT,
                floor_count INTEGER,
                total_units INTEGER,
                garden_area REAL,
                FOREIGN KEY (owner_id) REFERENCES owners(id)
            );
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlOwners);
            stmt.execute(sqlContractors);
            stmt.execute(sqlBuildings);
            System.out.println("Tablolar oluşturuldu.");
        } catch (SQLException e) {
            System.out.println("Tablo oluşturma hatası: " + e.getMessage());
        }
    }

    // ========== MÜLK SAHİBİ İŞLEMLERİ ==========

    public static int insertOwner(PropertyOwner owner) {
        String sql = "INSERT INTO owners(first_name, last_name, phone) VALUES(?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            pstmt.setString(3, owner.getPhoneNumber());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                owner.setId(id);
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Owner ekleme hatası: " + e.getMessage());
        }
        return -1;
    }

    public static void updateOwner(PropertyOwner owner) {
        String sql = "UPDATE owners SET first_name = ?, last_name = ?, phone = ? WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, owner.getFirstName());
            pstmt.setString(2, owner.getLastName());
            pstmt.setString(3, owner.getPhoneNumber());
            pstmt.setInt(4, owner.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Owner güncelleme hatası: " + e.getMessage());
        }
    }

    public static void deleteOwner(int id) {
        // Önce bu sahibe ait binaları sil
        String sqlBuildings = "DELETE FROM buildings WHERE owner_id = ?";
        String sqlOwner = "DELETE FROM owners WHERE id = ?";
        
        try (Connection conn = connect()) {
            PreparedStatement pstmt1 = conn.prepareStatement(sqlBuildings);
            pstmt1.setInt(1, id);
            pstmt1.executeUpdate();
            
            PreparedStatement pstmt2 = conn.prepareStatement(sqlOwner);
            pstmt2.setInt(1, id);
            pstmt2.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Owner silme hatası: " + e.getMessage());
        }
    }

    public static List<PropertyOwner> getAllOwners() {
        List<PropertyOwner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owners";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                PropertyOwner owner = new PropertyOwner(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone")
                );
                owner.setId(rs.getInt("id"));
                owners.add(owner);
            }
        } catch (SQLException e) {
            System.out.println("Owner listeleme hatası: " + e.getMessage());
        }
        return owners;
    }

    // ========== MÜTEAHHİT İŞLEMLERİ ==========

    public static int insertContractor(Contractor contractor) {
        String sql = "INSERT INTO contractors(first_name, last_name, phone, company_name) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, contractor.getFirstName());
            pstmt.setString(2, contractor.getLastName());
            pstmt.setString(3, contractor.getPhoneNumber());
            pstmt.setString(4, contractor.getCompanyName());
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                contractor.setId(id);
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Contractor ekleme hatası: " + e.getMessage());
        }
        return -1;
    }

    public static void updateContractor(Contractor contractor) {
        String sql = "UPDATE contractors SET first_name = ?, last_name = ?, phone = ?, company_name = ? WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, contractor.getFirstName());
            pstmt.setString(2, contractor.getLastName());
            pstmt.setString(3, contractor.getPhoneNumber());
            pstmt.setString(4, contractor.getCompanyName());
            pstmt.setInt(5, contractor.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Contractor güncelleme hatası: " + e.getMessage());
        }
    }

    public static void deleteContractor(int id) {
        String sql = "DELETE FROM contractors WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Contractor silme hatası: " + e.getMessage());
        }
    }

    public static List<Contractor> getAllContractors() {
        List<Contractor> contractors = new ArrayList<>();
        String sql = "SELECT * FROM contractors";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contractor contractor = new Contractor(
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("company_name")
                );
                contractor.setId(rs.getInt("id"));
                contractors.add(contractor);
            }
        } catch (SQLException e) {
            System.out.println("Contractor listeleme hatası: " + e.getMessage());
        }
        return contractors;
    }

    // ========== BİNA İŞLEMLERİ ==========

    public static int insertBuilding(Building building) {
        String sql = "INSERT INTO buildings(building_type, address, owner_id, risk_status, floor_count, total_units, garden_area) VALUES(?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            String type = (building instanceof ApartmentBuilding) ? "APARTMAN" : "MUSTAKIL";
            pstmt.setString(1, type);
            pstmt.setString(2, building.getAddress());
            pstmt.setInt(3, building.getOwner().getId());
            pstmt.setString(4, building.getRiskStatus());
            
            if (building instanceof ApartmentBuilding) {
                ApartmentBuilding apt = (ApartmentBuilding) building;
                pstmt.setInt(5, apt.getFloorCount());
                pstmt.setInt(6, apt.getTotalUnits());
                pstmt.setNull(7, java.sql.Types.REAL);
            } else {
                DetachedBuilding det = (DetachedBuilding) building;
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setNull(6, java.sql.Types.INTEGER);
                pstmt.setDouble(7, det.getGardenArea());
            }
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                building.setId(id);
                return id;
            }
        } catch (SQLException e) {
            System.out.println("Building ekleme hatası: " + e.getMessage());
        }
        return -1;
    }

    public static void updateBuilding(Building building) {
        String sql = "UPDATE buildings SET address = ?, owner_id = ?, risk_status = ?, floor_count = ?, total_units = ?, garden_area = ? WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, building.getAddress());
            pstmt.setInt(2, building.getOwner().getId());
            pstmt.setString(3, building.getRiskStatus());
            
            if (building instanceof ApartmentBuilding) {
                ApartmentBuilding apt = (ApartmentBuilding) building;
                pstmt.setInt(4, apt.getFloorCount());
                pstmt.setInt(5, apt.getTotalUnits());
                pstmt.setNull(6, java.sql.Types.REAL);
            } else {
                DetachedBuilding det = (DetachedBuilding) building;
                pstmt.setNull(4, java.sql.Types.INTEGER);
                pstmt.setNull(5, java.sql.Types.INTEGER);
                pstmt.setDouble(6, det.getGardenArea());
            }
            
            pstmt.setInt(7, building.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Building güncelleme hatası: " + e.getMessage());
        }
    }

    public static void deleteBuilding(int id) {
        String sql = "DELETE FROM buildings WHERE id = ?";
        
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Building silme hatası: " + e.getMessage());
        }
    }

    public static List<Building> getAllBuildings(Map<Integer, PropertyOwner> ownerMap) {
        List<Building> buildings = new ArrayList<>();
        String sql = "SELECT * FROM buildings";
        
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int ownerId = rs.getInt("owner_id");
                PropertyOwner owner = ownerMap.get(ownerId);
                
                if (owner == null) continue;
                
                Building building;
                String type = rs.getString("building_type");
                
                if ("APARTMAN".equals(type)) {
                    building = new ApartmentBuilding(
                        rs.getString("address"),
                        owner,
                        rs.getInt("floor_count"),
                        rs.getInt("total_units")
                    );
                } else {
                    building = new DetachedBuilding(
                        rs.getString("address"),
                        owner,
                        rs.getDouble("garden_area")
                    );
                }
                
                building.setId(rs.getInt("id"));
                building.setRiskStatus(rs.getString("risk_status"));
                buildings.add(building);
            }
        } catch (SQLException e) {
            System.out.println("Building listeleme hatası: " + e.getMessage());
        }
        return buildings;
    }
}