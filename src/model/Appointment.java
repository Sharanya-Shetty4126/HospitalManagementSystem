package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private int appointmentID;
    private int patientID;
    private int doctorID;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String status; // e.g., "Scheduled", "Completed", "Cancelled"

    // Constructor for creating a new appointment
    public Appointment(int patientID, int doctorID, LocalDate appointmentDate, LocalTime appointmentTime, String status) throws IllegalArgumentException {
        if (patientID <= 0) throw new IllegalArgumentException("Invalid Patient ID");
        if (doctorID <= 0) throw new IllegalArgumentException("Invalid Doctor ID");
        if (appointmentDate == null || appointmentDate.isBefore(LocalDate.now())) throw new IllegalArgumentException("Invalid Appointment Date");
        if (appointmentTime == null) throw new IllegalArgumentException("Invalid Appointment Time");
        if (status == null || (!status.equalsIgnoreCase("Scheduled") && !status.equalsIgnoreCase("Completed") && !status.equalsIgnoreCase("Cancelled"))) {
            throw new IllegalArgumentException("Invalid Status (must be Scheduled, Completed, or Cancelled)");
        }

        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Constructor for loading from database
    public Appointment(int appointmentID, int patientID, int doctorID, LocalDate appointmentDate, LocalTime appointmentTime, String status) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getters and Setters
    public int getAppointmentID() { return appointmentID; }
    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }

    public int getPatientID() { return patientID; }
    public void setPatientID(int patientID) { this.patientID = patientID; }

    public int getDoctorID() { return doctorID; }
    public void setDoctorID(int doctorID) { this.doctorID = doctorID; }

    public LocalDate getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDate appointmentDate) { this.appointmentDate = appointmentDate; }

    public LocalTime getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(LocalTime appointmentTime) { this.appointmentTime = appointmentTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentID +
               "\nPatient ID: " + patientID +
               "\nDoctor ID: " + doctorID +
               "\nDate: " + appointmentDate +
               "\nTime: " + appointmentTime +
               "\nStatus: " + status;
    }
}