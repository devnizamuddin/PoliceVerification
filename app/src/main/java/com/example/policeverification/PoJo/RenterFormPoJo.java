package com.example.policeverification.PoJo;

import android.widget.ScrollView;

public class RenterFormPoJo {

    private String userId;
    private String name;
    private String fatherName;
    private String motherName;
    private String nidNo;
    private String occupation;
    private String permanentAddress;
    private String emergencyNo;
    private String phoneNo;
    private String previousNo;
    private String presentNo;
    private String verificationStatus;

    public RenterFormPoJo(String userId, String name, String fatherName, String motherName, String nidNo, String occupation, String permanentAddress, String emergencyNo, String phoneNo, String previousNo, String presentNo, String verificationStatus) {
        this.userId = userId;
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.nidNo = nidNo;
        this.occupation = occupation;
        this.permanentAddress = permanentAddress;
        this.emergencyNo = emergencyNo;
        this.phoneNo = phoneNo;
        this.previousNo = previousNo;
        this.presentNo = presentNo;
        this.verificationStatus = verificationStatus;
    }

    public RenterFormPoJo(String name, String fatherName, String motherName, String nidNo, String occupation, String permanentAddress, String emergencyNo, String phoneNo, String previousNo, String presentNo, String verificationStatus) {
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.nidNo = nidNo;
        this.occupation = occupation;
        this.permanentAddress = permanentAddress;
        this.emergencyNo = emergencyNo;
        this.phoneNo = phoneNo;
        this.previousNo = previousNo;
        this.presentNo = presentNo;
        this.verificationStatus = verificationStatus;
    }

    public RenterFormPoJo() {
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

    public String getPreviousNo() {
        return previousNo;
    }

    public void setPreviousNo(String previousNo) {
        this.previousNo = previousNo;
    }

    public String getPresentNo() {
        return presentNo;
    }

    public void setPresentNo(String presentNo) {
        this.presentNo = presentNo;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}
