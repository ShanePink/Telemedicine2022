package com.example.iheartproject;

public class EquipmentItem {
    private String Uid;
    private Equipment Equipment;
    private String EquipmentType;
    private String Brand;
    private String Model;
    private String SerialNo;
    private Integer Qty;
    private String ExpiryDate;
    private String DonorEmail;
    private boolean DonateStatus = false;
    public EquipmentItem(){

    }
    public EquipmentItem(String uid, Equipment equipment, String equipmentType, String brand, String model, String serialNo, Integer qty, String expiryDate, String donorEmail, boolean donateStatus )
    {
        this.Uid = uid;
        this.Equipment = equipment;
        this.EquipmentType = equipmentType;
        this.Brand = brand;
        this.Model = model;
        this.SerialNo = serialNo;
        this.Qty = qty;
        this.ExpiryDate = expiryDate;
        this.DonorEmail = donorEmail;
        this.DonateStatus = donateStatus;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String newUid) {
        this.Uid = newUid;
    }

    public Equipment getEquipment() {
        return Equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.Equipment = equipment;
    }

    public String getEquipmentType() {
        return EquipmentType;
    }

    public void setEquipmentType(String newEquipmentType) {
        this.EquipmentType = newEquipmentType;
    }

    public String getBrand() {
        return Brand;
    }
    public void setBrand(String newBrand) {
        this.Brand = newBrand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String newModel) {
        this.Model = newModel;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String newSerialNo) {
        this.SerialNo = newSerialNo;
    }

    public Integer getQty() {
        return Qty;
    }

    public void setQty(Integer newQty) {
        this.Qty = newQty;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String newExpiryDate) {
        this.ExpiryDate = newExpiryDate;
    }

    public String getDonorEmail() {
        return DonorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.DonorEmail = donorEmail;
    }

    public boolean getDonateStatus() {
        return DonateStatus;
    }

    public void setDonateStatus(boolean donateStatus) {
        this.DonateStatus = donateStatus;
    }
}
