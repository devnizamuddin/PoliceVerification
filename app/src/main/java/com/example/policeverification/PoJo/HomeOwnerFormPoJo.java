package com.example.policeverification.PoJo;

public class HomeOwnerFormPoJo {

    private String userId;
    private String name;
    private String fatherName;
    private String motherName;
    private String nidNo;
    private String occupation;
    private String permanentAddress;
    private String presentAddress;
    private String emergencyNo;
    private String phoneNo;
    private String regHouseAdd;
    private String verificationStatus;

    public HomeOwnerFormPoJo(String userId, String name, String fatherName, String motherName, String nidNo, String occupation, String permanentAddress, String presentAddress, String emergencyNo, String phoneNo, String regHouseAdd, String verificationStatus) {
        this.userId = userId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.nidNo = nidNo;
        this.occupation = occupation;
        this.permanentAddress = permanentAddress;
        this.presentAddress = presentAddress;
        this.emergencyNo = emergencyNo;
        this.phoneNo = phoneNo;
        this.regHouseAdd = regHouseAdd;
        this.verificationStatus = verificationStatus;
    }

    public HomeOwnerFormPoJo(String name, String fatherName, String motherName, String nidNo, String occupation, String permanentAddress, String presentAddress, String emergencyNo, String phoneNo, String regHouseAdd, String verificationStatus) {
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.nidNo = nidNo;
        this.occupation = occupation;
        this.permanentAddress = permanentAddress;
        this.presentAddress = presentAddress;
        this.emergencyNo = emergencyNo;
        this.phoneNo = phoneNo;
        this.regHouseAdd = regHouseAdd;
        this.verificationStatus = verificationStatus;
    }

    public HomeOwnerFormPoJo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getEmergencyNo() {
        return emergencyNo;
    }

    public void setEmergencyNo(String emergencyNo) {
        this.emergencyNo = emergencyNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRegHouseAdd() {
        return regHouseAdd;
    }

    public void setRegHouseAdd(String regHouseAdd) {
        this.regHouseAdd = regHouseAdd;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
