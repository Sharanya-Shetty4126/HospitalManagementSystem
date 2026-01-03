package DAO;

import connectivity.DBConnection;
import model.Appointment;
import model.Billing;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    // Add Billing
    public boolean addBilling(Billing billing) {
        String sql = "INSERT INTO billing (appointment_id, amount, billing_date, status) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, billing.getAppointmentID());
            ps.setDouble(2, billing.getAmount());
            ps.setDate(3, Date.valueOf(billing.getBillingDate()));
            ps.setString(4, billing.getStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        billing.setBillingID(rs.getInt(1));
                    }
                }
            }
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Generate and Add Billing for a Completed Appointment
    public boolean generateBillForAppointment(int appointmentID) {
        AppointmentDAO appointmentDAO = new AppointmentDAO();
        Appointment appointment = appointmentDAO.getAppointmentById(appointmentID);
        if (appointment == null || !"Completed".equalsIgnoreCase(appointment.getStatus())) {
            return false; // Only bill completed appointments
        }

        double amount = Billing.calculateAmount(appointment);
        if (amount <= 0) return false;

        Billing billing = new Billing(appointmentID, amount, LocalDate.now(), "Pending");
        return addBilling(billing);
    }

    // Get All Billings
    public List<Billing> getAllBillings() {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Billing bill = new Billing(
                    rs.getInt("billing_id"),
                    rs.getInt("appointment_id"),
                    rs.getDouble("amount"),
                    rs.getDate("billing_date").toLocalDate(),
                    rs.getString("status")
                );
                billings.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }

    // Get Billing by ID
    public Billing getBillingById(int billingID) {
        String sql = "SELECT * FROM billing WHERE billing_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, billingID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Billing(
                    rs.getInt("billing_id"),
                    rs.getInt("appointment_id"),
                    rs.getDouble("amount"),
                    rs.getDate("billing_date").toLocalDate(),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Billings by Appointment ID
    public List<Billing> getBillingsByAppointmentId(int appointmentID) {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE appointment_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, appointmentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                billings.add(new Billing(
                    rs.getInt("billing_id"),
                    rs.getInt("appointment_id"),
                    rs.getDouble("amount"),
                    rs.getDate("billing_date").toLocalDate(),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }

    // Update Billing Status
    public boolean updateBillingStatus(int billingID, String status) {
        String sql = "UPDATE billing SET status = ? WHERE billing_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, billingID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get Pending Billings (for reminders or reports)
    public List<Billing> getPendingBillings() {
        List<Billing> billings = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE status = 'Pending'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                billings.add(new Billing(
                    rs.getInt("billing_id"),
                    rs.getInt("appointment_id"),
                    rs.getDouble("amount"),
                    rs.getDate("billing_date").toLocalDate(),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billings;
    }
}