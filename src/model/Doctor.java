package model;

import java.util.*;
import java.time.LocalDate;
import java.time.Period;

public class Doctor {

    private int doctorID;
    private String name;
    private String specialty;
    private String phoneNumber;
    private String emailId;
    private String licenseNumber;
    private int yearsOfExperience;
    private int addressID = -1; // Optional, similar to Patient

    // Constructor for creating a new Doctor (without ID, as it might be auto-generated)
    public Doctor(String name, String specialty, String phoneNumber, String emailId, String licenseNumber, int yearsOfExperience, int addressID) throws IllegalArgumentException {
        if (name == null || !name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) {
            throw new IllegalArgumentException("Invalid Name");
        }
        if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
        if (!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid Email ID");
        }
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid License Number");
        }
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of Experience cannot be negative");
        }
        // Specialty can be any string, no strict validation here
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty cannot be empty");
        }

        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.addressID = addressID;
    }

    // Constructor for loading from database (with ID)
    public Doctor(int doctorID, String name, String specialty, String phoneNumber, String emailId, String licenseNumber, int yearsOfExperience, int addressID) {
        this.doctorID = doctorID;
        this.name = name;
        this.specialty = specialty;
        this.phoneNumber = phoneNumber;
        this.emailId = emailId;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.addressID = addressID;
    }

    // Getters and Setters
    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || !name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$")) {
            throw new IllegalArgumentException("Invalid Name");
        }
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) throws IllegalArgumentException {
        if (specialty == null || specialty.trim().isEmpty()) {
            throw new IllegalArgumentException("Specialty cannot be empty");
        }
        this.specialty = specialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        if (phoneNumber.length() != 10 || !phoneNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid Phone Number");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) throws IllegalArgumentException {
        if (!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid Email ID");
        }
        this.emailId = emailId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) throws IllegalArgumentException {
        if (licenseNumber == null || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid License Number");
        }
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) throws IllegalArgumentException {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of Experience cannot be negative");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) throws IllegalArgumentException {
        if (addressID <= 0) {
            throw new IllegalArgumentException("Invalid Address ID");
        }
        this.addressID = addressID;
    }

    public boolean hasAddress() {
        return addressID != -1;
    }

    // Update contact info method, similar to Patient
    public void updateContactInfo(String phoneNumber, String emailId) throws IllegalArgumentException {
        setPhoneNumber(phoneNumber);
        setEmailId(emailId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Doctor ID: ").append(doctorID)
          .append("\nName: ").append(name)
          .append("\nSpecialty: ").append(specialty)
          .append("\nLicense Number: ").append(licenseNumber)
          .append("\nYears of Experience: ").append(yearsOfExperience)
          .append("\nPhone: ").append(phoneNumber)
          .append("\nEmail: ").append(emailId)
          .append("\nAddress ID: ").append(addressID);
        return sb.toString();
    }
}