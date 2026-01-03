package DAO;

import connectivity.DBConnection;
import model.Doctor;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    // ================= ADD DOCTOR WITH ADDRESS =================
    public boolean addDoctorWithAddress(Doctor doctor, Address address) {
        Connection con = null;
        AddressDAO addressDAO = new AddressDAO();

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // start transaction

            // Insert or find Address using AddressDAO
            int addressId = addressDAO.findAddressId(address, con);
            if (addressId == -1) {
                addressId = addressDAO.addAddress(address, con); // transaction-safe insert
            }
            doctor.setAddressID(addressId);
            address.setAddressID(addressId);

            // Insert Doctor
            String sqlDoctor = "INSERT INTO doctor (DOCTOR_NAME, SPECIALTY, PHONE_NO, EMAIL, LICENSE_NUMBER, YEARS_OF_EXPERIENCE, ADDRESS_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psDoc = con.prepareStatement(sqlDoctor, Statement.RETURN_GENERATED_KEYS)) {
                psDoc.setString(1, doctor.getName());
                psDoc.setString(2, doctor.getSpecialty());
                psDoc.setString(3, doctor.getPhoneNumber());
                psDoc.setString(4, doctor.getEmailId());
                psDoc.setString(5, doctor.getLicenseNumber());
                psDoc.setInt(6, doctor.getYearsOfExperience());
                if (addressId == -1) {
                    throw new SQLException("Address creation failed");
                }
                psDoc.setInt(7, addressId);

                int rows = psDoc.executeUpdate();
                if (rows > 0) {
                    try (ResultSet rs = psDoc.getGeneratedKeys()) {
                        if (rs.next()) {
                            int doctorId = rs.getInt(1);
                            doctor.setDoctorID(doctorId);
                        }
                    }
                }
            }

            con.commit(); // commit transaction
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback(); // rollback if anything fails
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // reset autocommit
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ================= GET ALL DOCTORS =================
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Doctor d = new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("doctor_name"),
                    rs.getString("specialty"),
                    rs.getString("phone_no"),
                    rs.getString("email"),
                    rs.getString("license_number"),
                    rs.getInt("years_of_experience"),
                    rs.getInt("address_id")
                );
                doctors.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doctors;
    }

    // ================= GET DOCTOR BY ID =================
    public Doctor getDoctorById(int doctorId) {
        Doctor d = null;
        String sql = "SELECT * FROM doctor WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                d = new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("doctor_name"),
                    rs.getString("specialty"),
                    rs.getString("phone_no"),
                    rs.getString("email"),
                    rs.getString("license_number"),
                    rs.getInt("years_of_experience"),
                    rs.getInt("address_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return d;
    }

    // ================= GET DOCTOR ID BY NAME AND PHONE =================
    public int getDoctorIDByNameAndPhone(String name, String phone) {
        int doctorID = -1; // default if not found
        String sql = "SELECT doctor_id FROM doctor WHERE doctor_name = ? AND phone_no = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, phone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                doctorID = rs.getInt("doctor_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorID;
    }

    // ================= GET DOCTOR ID BY NAME AND EMAIL =================
    public int getDoctorIDByNameAndEmail(String name, String email) {
        int doctorID = -1; // default if not found
        String sql = "SELECT doctor_id FROM doctor WHERE doctor_name = ? AND email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                doctorID = rs.getInt("doctor_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorID;
    }

    // ================= UPDATE CONTACT INFO =================
    public boolean updateContactInfo(Doctor doctor, String email, String phone) throws IllegalArgumentException {
        doctor.updateContactInfo(phone, email);
        String sql = "UPDATE doctor SET phone_no = ?, email = ? WHERE doctor_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, email);
            ps.setInt(3, doctor.getDoctorID());

            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}