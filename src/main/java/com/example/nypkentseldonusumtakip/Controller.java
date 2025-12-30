package com.example.nypkentseldonusumtakip;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    
    // ========== BİNALAR TAB ==========
    @FXML
    private TableView<Building> buildingsTable;
    
    @FXML
    private TableColumn<Building, String> colType;
    
    @FXML
    private TableColumn<Building, String> colAddress;
    
    @FXML
    private TableColumn<Building, String> colOwner;
    
    @FXML
    private TableColumn<Building, String> colContractor;
    
    @FXML
    private TableColumn<Building, String> colStatus;
    
    @FXML
    private TableColumn<Building, String> colCost;
    
    @FXML
    private ComboBox<String> cmbBuildingType;
    
    @FXML
    private TextField txtBuildingAddress;
    
    @FXML
    private ComboBox<PropertyOwner> cmbBuildingOwner;
    
    @FXML
    private ComboBox<Contractor> cmbBuildingContractor;
    
    @FXML
    private ComboBox<String> cmbRiskStatus;
    
    @FXML
    private Label lblFloors;
    
    @FXML
    private TextField txtFloors;
    
    @FXML
    private Label lblUnits;
    
    @FXML
    private TextField txtUnits;
    
    @FXML
    private Label lblGardenArea;
    
    @FXML
    private TextField txtGardenArea;
    
    @FXML
    private Button btnAddBuilding;
    
    @FXML
    private Button btnUpdateBuilding;
    
    @FXML
    private Button btnDeleteBuilding;
    
    @FXML
    private Button btnClearBuilding;
    
    private ObservableList<Building> buildingList = FXCollections.observableArrayList();

    // ========== MÜLK SAHİPLERİ TAB ==========
    @FXML
    private TableView<PropertyOwner> ownersTable;
    
    @FXML
    private TableColumn<PropertyOwner, String> colOwnerFirstName;
    
    @FXML
    private TableColumn<PropertyOwner, String> colOwnerLastName;
    
    @FXML
    private TableColumn<PropertyOwner, String> colOwnerPhone;
    
    @FXML
    private TableColumn<PropertyOwner, Integer> colOwnerBuildingCount;
    
    @FXML
    private TextField txtOwnerFirstName;
    
    @FXML
    private TextField txtOwnerLastName;
    
    @FXML
    private TextField txtOwnerPhone;
    
    @FXML
    private Button btnAddOwner;
    
    @FXML
    private Button btnUpdateOwner;
    
    @FXML
    private Button btnDeleteOwner;
    
    private ObservableList<PropertyOwner> ownerList = FXCollections.observableArrayList();

    // ========== MÜTEAHHİTLER TAB ==========
    @FXML
    private TableView<Contractor> contractorsTable;
    
    @FXML
    private TableColumn<Contractor, String> colContractorFirstName;
    
    @FXML
    private TableColumn<Contractor, String> colContractorLastName;
    
    @FXML
    private TableColumn<Contractor, String> colContractorPhone;
    
    @FXML
    private TableColumn<Contractor, String> colContractorCompany;
    
    @FXML
    private TextField txtContractorFirstName;
    
    @FXML
    private TextField txtContractorLastName;
    
    @FXML
    private TextField txtContractorPhone;
    
    @FXML
    private TextField txtContractorCompany;
    
    @FXML
    private Button btnAddContractor;
    
    @FXML
    private Button btnUpdateContractor;
    
    @FXML
    private Button btnDeleteContractor;
    
    private ObservableList<Contractor> contractorList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupBuildingsTable();
        setupOwnersTable();
        setupContractorsTable();
        setupComboBoxes();
        loadDataFromDatabase();
    }
    
    private void loadDataFromDatabase() {
        // Önce sahipleri yükle
        List<PropertyOwner> owners = db.getAllOwners();
        ownerList.addAll(owners);
        
        // Müteahhitleri yükle
        List<Contractor> contractors = db.getAllContractors();
        contractorList.addAll(contractors);
        
        // Sahip map'i oluştur
        Map<Integer, PropertyOwner> ownerMap = new HashMap<>();
        for (PropertyOwner owner : owners) {
            ownerMap.put(owner.getId(), owner);
        }
        
        // Müteahhit map'i oluştur
        Map<Integer, Contractor> contractorMap = new HashMap<>();
        for (Contractor contractor : contractors) {
            contractorMap.put(contractor.getId(), contractor);
        }
        
        // Binaları yükle
        List<Building> buildings = db.getAllBuildings(ownerMap, contractorMap);
        buildingList.addAll(buildings);
    }
    
    private void setupBuildingsTable() {
        colType.setCellValueFactory(cellData -> {
            Building b = cellData.getValue();
            String type = (b instanceof ApartmentBuilding) ? "Apartman" : "Müstakil";
            return new SimpleStringProperty(type);
        });
        
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        
        colOwner.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getOwner().getFullName())
        );
        
        // Müteahhit sütunu
        colContractor.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getContractorName())
        );
        
        colStatus.setCellValueFactory(new PropertyValueFactory<>("riskStatus"));
        
        colCost.setCellValueFactory(cellData -> {
            double cost = cellData.getValue().calculateCost();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
            return new SimpleStringProperty(nf.format(cost));
        });
        
        buildingsTable.setItems(buildingList);
        
        buildingsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    fillBuildingForm(newValue);
                }
            }
        );
    }
    
    private void setupOwnersTable() {
        colOwnerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colOwnerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colOwnerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        
        colOwnerBuildingCount.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getOwnedBuildings().size()).asObject()
        );
        
        ownersTable.setItems(ownerList);
        
        ownersTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    fillOwnerForm(newValue);
                }
            }
        );
    }
    
    private void setupContractorsTable() {
        colContractorFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colContractorLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colContractorPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        colContractorCompany.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        
        contractorsTable.setItems(contractorList);
        
        contractorsTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    fillContractorForm(newValue);
                }
            }
        );
    }
    
    private void setupComboBoxes() {
        cmbBuildingType.setItems(FXCollections.observableArrayList("Apartman", "Müstakil"));
        
        cmbBuildingType.setOnAction(e -> {
            String selected = cmbBuildingType.getValue();
            boolean isApartment = "Apartman".equals(selected);
            
            lblFloors.setVisible(isApartment);
            lblFloors.setManaged(isApartment);
            txtFloors.setVisible(isApartment);
            txtFloors.setManaged(isApartment);
            lblUnits.setVisible(isApartment);
            lblUnits.setManaged(isApartment);
            txtUnits.setVisible(isApartment);
            txtUnits.setManaged(isApartment);
            
            lblGardenArea.setVisible(!isApartment && selected != null);
            lblGardenArea.setManaged(!isApartment && selected != null);
            txtGardenArea.setVisible(!isApartment && selected != null);
            txtGardenArea.setManaged(!isApartment && selected != null);
        });
        
        cmbRiskStatus.setItems(FXCollections.observableArrayList("RISKLI", "GUVENLI", "BELIRSIZ"));
        cmbBuildingOwner.setItems(ownerList);
        cmbBuildingContractor.setItems(contractorList);
    }
    
    // ========== BİNA CRUD İŞLEMLERİ ==========
    
    @FXML
    private void handleAddBuilding() {
        String type = cmbBuildingType.getValue();
        String address = txtBuildingAddress.getText().trim();
        PropertyOwner owner = cmbBuildingOwner.getValue();
        Contractor contractor = cmbBuildingContractor.getValue();
        String riskStatus = cmbRiskStatus.getValue();
        
        if (type == null || address.isEmpty() || owner == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen tür, adres ve sahip alanlarını doldurunuz!");
            return;
        }
        
        Building newBuilding;
        
        if ("Apartman".equals(type)) {
            if (txtFloors.getText().trim().isEmpty() || txtUnits.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen kat ve daire sayısını giriniz!");
                return;
            }
            
            try {
                int floors = Integer.parseInt(txtFloors.getText().trim());
                int units = Integer.parseInt(txtUnits.getText().trim());
                newBuilding = new ApartmentBuilding(address, owner, floors, units);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Kat ve daire sayısı sayı olmalıdır!");
                return;
            }
        } else {
            if (txtGardenArea.getText().trim().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen bahçe alanını giriniz!");
                return;
            }
            
            try {
                double gardenArea = Double.parseDouble(txtGardenArea.getText().trim());
                newBuilding = new DetachedBuilding(address, owner, gardenArea);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Hata", "Bahçe alanı sayı olmalıdır!");
                return;
            }
        }
        
        if (riskStatus != null) {
            newBuilding.setRiskStatus(riskStatus);
        }
        
        // Müteahhit ata (opsiyonel)
        if (contractor != null) {
            newBuilding.setContractor(contractor);
        }
        
        int id = db.insertBuilding(newBuilding);
        if (id > 0) {
            buildingList.add(newBuilding);
            ownersTable.refresh();
            clearBuildingForm();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Bina eklendi!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Bina eklenirken hata oluştu!");
        }
    }
    
    @FXML
    private void handleUpdateBuilding() {
        Building selected = buildingsTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen güncellenecek binayı seçiniz!");
            return;
        }
        
        String address = txtBuildingAddress.getText().trim();
        PropertyOwner owner = cmbBuildingOwner.getValue();
        Contractor contractor = cmbBuildingContractor.getValue();
        String riskStatus = cmbRiskStatus.getValue();
        
        if (address.isEmpty() || owner == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen adres ve sahip alanlarını doldurunuz!");
            return;
        }
        
        selected.setAddress(address);
        selected.setOwner(owner);
        selected.setContractor(contractor); // null olabilir
        
        if (riskStatus != null) {
            selected.setRiskStatus(riskStatus);
        }
        
        try {
            if (selected instanceof ApartmentBuilding) {
                ApartmentBuilding apt = (ApartmentBuilding) selected;
                apt.setFloorCount(Integer.parseInt(txtFloors.getText().trim()));
                apt.setTotalUnits(Integer.parseInt(txtUnits.getText().trim()));
            } else if (selected instanceof DetachedBuilding) {
                DetachedBuilding det = (DetachedBuilding) selected;
                det.setGardenArea(Double.parseDouble(txtGardenArea.getText().trim()));
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Hata", "Sayısal değerleri kontrol ediniz!");
            return;
        }
        
        db.updateBuilding(selected);
        
        buildingsTable.refresh();
        ownersTable.refresh();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Bina güncellendi!");
    }
    
    @FXML
    private void handleDeleteBuilding() {
        Building selected = buildingsTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silinecek binayı seçiniz!");
            return;
        }
        
        db.deleteBuilding(selected.getId());
        
        selected.getOwner().getOwnedBuildings().remove(selected);
        buildingList.remove(selected);
        ownersTable.refresh();
        clearBuildingForm();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Bina silindi!");
    }
    
    @FXML
    private void handleClearBuildingForm() {
        clearBuildingForm();
    }
    
    private void fillBuildingForm(Building building) {
        txtBuildingAddress.setText(building.getAddress());
        cmbBuildingOwner.setValue(building.getOwner());
        cmbBuildingContractor.setValue(building.getContractor());
        cmbRiskStatus.setValue(building.getRiskStatus());
        
        if (building instanceof ApartmentBuilding) {
            ApartmentBuilding apt = (ApartmentBuilding) building;
            cmbBuildingType.setValue("Apartman");
            txtFloors.setText(String.valueOf(apt.getFloorCount()));
            txtUnits.setText(String.valueOf(apt.getTotalUnits()));
            txtGardenArea.clear();
        } else if (building instanceof DetachedBuilding) {
            DetachedBuilding det = (DetachedBuilding) building;
            cmbBuildingType.setValue("Müstakil");
            txtGardenArea.setText(String.valueOf(det.getGardenArea()));
            txtFloors.clear();
            txtUnits.clear();
        }
    }
    
    private void clearBuildingForm() {
        cmbBuildingType.setValue(null);
        txtBuildingAddress.clear();
        cmbBuildingOwner.setValue(null);
        cmbBuildingContractor.setValue(null);
        cmbRiskStatus.setValue(null);
        txtFloors.clear();
        txtUnits.clear();
        txtGardenArea.clear();
        buildingsTable.getSelectionModel().clearSelection();
        
        lblFloors.setVisible(false);
        lblFloors.setManaged(false);
        txtFloors.setVisible(false);
        txtFloors.setManaged(false);
        lblUnits.setVisible(false);
        lblUnits.setManaged(false);
        txtUnits.setVisible(false);
        txtUnits.setManaged(false);
        lblGardenArea.setVisible(false);
        lblGardenArea.setManaged(false);
        txtGardenArea.setVisible(false);
        txtGardenArea.setManaged(false);
    }
    
    // ========== MÜLK SAHİBİ CRUD İŞLEMLERİ ==========
    
    @FXML
    private void handleAddOwner() {
        String firstName = txtOwnerFirstName.getText().trim();
        String lastName = txtOwnerLastName.getText().trim();
        String phone = txtOwnerPhone.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen tüm alanları doldurunuz!");
            return;
        }
        
        PropertyOwner newOwner = new PropertyOwner(firstName, lastName, phone);
        
        int id = db.insertOwner(newOwner);
        if (id > 0) {
            ownerList.add(newOwner);
            clearOwnerForm();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Mülk sahibi eklendi!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Mülk sahibi eklenirken hata oluştu!");
        }
    }
    
    @FXML
    private void handleUpdateOwner() {
        PropertyOwner selected = ownersTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen güncellenecek kişiyi seçiniz!");
            return;
        }
        
        String firstName = txtOwnerFirstName.getText().trim();
        String lastName = txtOwnerLastName.getText().trim();
        String phone = txtOwnerPhone.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen tüm alanları doldurunuz!");
            return;
        }
        
        selected.setFirstName(firstName);
        selected.setLastName(lastName);
        selected.setPhoneNumber(phone);
        
        db.updateOwner(selected);
        
        ownersTable.refresh();
        buildingsTable.refresh();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Mülk sahibi güncellendi!");
    }
    
    @FXML
    private void handleDeleteOwner() {
        PropertyOwner selected = ownersTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silinecek kişiyi seçiniz!");
            return;
        }
        
        db.deleteOwner(selected.getId());
        
        buildingList.removeAll(selected.getOwnedBuildings());
        ownerList.remove(selected);
        clearOwnerForm();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Mülk sahibi ve binaları silindi!");
    }
    
    // ========== MÜTEAHHİT CRUD İŞLEMLERİ ==========
    
    @FXML
    private void handleAddContractor() {
        String firstName = txtContractorFirstName.getText().trim();
        String lastName = txtContractorLastName.getText().trim();
        String phone = txtContractorPhone.getText().trim();
        String company = txtContractorCompany.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || company.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen tüm alanları doldurunuz!");
            return;
        }
        
        Contractor newContractor = new Contractor(firstName, lastName, phone, company);
        
        int id = db.insertContractor(newContractor);
        if (id > 0) {
            contractorList.add(newContractor);
            clearContractorForm();
            showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müteahhit eklendi!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Hata", "Müteahhit eklenirken hata oluştu!");
        }
    }
    
    @FXML
    private void handleUpdateContractor() {
        Contractor selected = contractorsTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen güncellenecek müteahhiti seçiniz!");
            return;
        }
        
        String firstName = txtContractorFirstName.getText().trim();
        String lastName = txtContractorLastName.getText().trim();
        String phone = txtContractorPhone.getText().trim();
        String company = txtContractorCompany.getText().trim();
        
        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || company.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen tüm alanları doldurunuz!");
            return;
        }
        
        selected.setFirstName(firstName);
        selected.setLastName(lastName);
        selected.setPhoneNumber(phone);
        selected.setCompanyName(company);
        
        db.updateContractor(selected);
        
        contractorsTable.refresh();
        buildingsTable.refresh();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müteahhit güncellendi!");
    }
    
    @FXML
    private void handleDeleteContractor() {
        Contractor selected = contractorsTable.getSelectionModel().getSelectedItem();
        
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Uyarı", "Lütfen silinecek müteahhiti seçiniz!");
            return;
        }
        
        db.deleteContractor(selected.getId());
        
        // Binalardaki müteahhit referanslarını temizle
        for (Building building : buildingList) {
            if (building.getContractor() != null && building.getContractor().getId() == selected.getId()) {
                building.setContractor(null);
            }
        }
        
        contractorList.remove(selected);
        buildingsTable.refresh();
        clearContractorForm();
        
        showAlert(Alert.AlertType.INFORMATION, "Başarılı", "Müteahhit silindi!");
    }
    
    // ========== YARDIMCI METOTLAR ==========
    
    private void fillOwnerForm(PropertyOwner owner) {
        txtOwnerFirstName.setText(owner.getFirstName());
        txtOwnerLastName.setText(owner.getLastName());
        txtOwnerPhone.setText(owner.getPhoneNumber());
    }
    
    private void clearOwnerForm() {
        txtOwnerFirstName.clear();
        txtOwnerLastName.clear();
        txtOwnerPhone.clear();
        ownersTable.getSelectionModel().clearSelection();
    }
    
    private void fillContractorForm(Contractor contractor) {
        txtContractorFirstName.setText(contractor.getFirstName());
        txtContractorLastName.setText(contractor.getLastName());
        txtContractorPhone.setText(contractor.getPhoneNumber());
        txtContractorCompany.setText(contractor.getCompanyName());
    }
    
    private void clearContractorForm() {
        txtContractorFirstName.clear();
        txtContractorLastName.clear();
        txtContractorPhone.clear();
        txtContractorCompany.clear();
        contractorsTable.getSelectionModel().clearSelection();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
