package model;

import java.time.LocalDate;

public class Billing {
    private int billingID;
    private int appointmentID;
    private double amount;
    private LocalDate billingDate;
    private String status; // e.g., "Pending", "Paid"

    // Constructor for creating a new billing record
    public Billing(int appointmentID, double amount, LocalDate billingDate, String status) throws IllegalArgumentException {
        if (appointmentID <= 0) throw new IllegalArgumentException("Invalid Appointment ID");
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
        if (billingDate == null || billingDate.isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid Billing Date (cannot be in the future)");
        if (status == null || (!status.equalsIgnoreCase("Pending") && !status.equalsIgnoreCase("Paid"))) {
            throw new IllegalArgumentException("Invalid Status (must be Pending or Paid)");
        }

        this.appointmentID = appointmentID;
        this.amount = amount;
        this.billingDate = billingDate;
        this.status = status;
    }

    // Constructor for loading from database
    public Billing(int billingID, int appointmentID, double amount, LocalDate billingDate, String status) {
        this.billingID = billingID;
        this.appointmentID = appointmentID;
        this.amount = amount;
        this.billingDate = billingDate;
        this.status = status;
    }

    // Static method to calculate bill amount based on an Appointment
    // Assumes a fixed $100 fee for completed appointments; customize as needed
    public static double calculateAmount(Appointment appointment) {
        if (appointment == null || !"Completed".equalsIgnoreCase(appointment.getStatus())) {
            return 0.0; // No bill for non-completed appointments
        }
        // Example: Fixed fee. You could query a Doctor model for variable rates here.
        return 100.0;
    }

    // Getters and Setters
    public int getBillingID() { return billingID; }
    public void setBillingID(int billingID) { this.billingID = billingID; }

    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public LocalDate getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDate billingDate) { this.billingDate = billingDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Billing ID: " + billingID +
               "\nAppointment ID: " + appointmentID +
               "\nAmount: $" + String.format("%.2f", amount) +
               "\nBilling Date: " + billingDate +
               "\nStatus: " + status;
    }
}