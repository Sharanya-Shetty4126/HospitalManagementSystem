package DAO;

import connectivity.DBConnection;
import model.Patient;
import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // ================= ADD PATIENT WITH ADDRESS =================
    public boolean addPatientWithAddress(Patient patient, Address address) {
    Connection con = null;
            AddressDAO addressDAO = new AddressDAO();

    try {
        con = DBConnection.getConnection();
        con.setAutoCommit(false); // start transaction

        // Insert Address using AddressDAO
       int  addressId = addressDAO.findAddressId(address,con);


// if(addressId == -1) {
//    addressId = insertAddress(address);
// }
// insertPatient(addressId);





if(addressId==-1){
        addressId = addressDAO.addAddress(address, con);
 } // transaction-safe insert
        patient.setAddressID(addressId);
        address.setAddressID(addressId);


        // Insert Patient
        String sqlPatient = "INSERT INTO patient (PATIENT_NAME, GENDER, PHONE_NO, EMAIL, DATE_OF_BIRTH, BLOOD_GROUP, ADDRESS_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psPat = con.prepareStatement(sqlPatient, Statement.RETURN_GENERATED_KEYS)) {
            psPat.setString(1, patient.getName());
            psPat.setString(2, patient.getGender());
            psPat.setString(3, patient.getPhoneNumber());
            psPat.setString(4, patient.getEmail());
            psPat.setDate(5, java.sql.Date.valueOf(patient.getDateOfBirth()));
            psPat.setString(6, patient.getBloodGroup());
if (addressId == -1) {
    throw new SQLException("Address creation failed");
}
psPat.setInt(7, addressId);


            int rows = psPat.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = psPat.getGeneratedKeys()) {
                    if (rs.next()) {
                        int patientId = rs.getInt(1);
                        patient.setPatientID(patientId);
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


    // ================= GET ALL PATIENTS =================
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) 
        {
            while (rs.next()) {
                Patient p = new Patient(
                    rs.getInt("patient_id"),        // Include ID
                    rs.getString("patient_name"),
                    rs.getString("gender"),
                    rs.getString("phone_no"),
                    rs.getString("email"),
                    rs.getDate("date_of_birth").toLocalDate(),
                    rs.getString("blood_group"),
                    rs.getInt("address_id")
                );
                patients.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return patients;
    }


    public Patient getPatientById(int patientId)
    {
        Patient p = null;

        String sql ="SELECT * FROM PATIENT WHERE PATIENT_ID=?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql))
        {

ps.setInt(1,patientId);
ResultSet rs = ps.executeQuery();

if(rs.next())
{

    p =new Patient(
        rs.getInt("patient_id"),
        rs.getString("patient_name"),
rs.getString("gender"),
rs.getString("phone_no"),
rs.getString("email"),
rs.getDate("date_of_birth").toLocalDate(),
rs.getString("blood_group"),
rs.getInt("address_Id")
    );





}

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }




return p;






    }

    public int getPatientIDByNameAndPhone(String name, String phone) {
    int patientID = -1; // default if not found
    String sql = "SELECT patient_id FROM PATIENT WHERE patient_name = ? AND phone_no = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, phone);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            patientID = rs.getInt("patient_id");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return patientID;
}
public int getPatientIDByNameAndEmail(String name, String email) {
    int patientID = -1; // default if not found
    String sql = "SELECT patient_id FROM PATIENT WHERE patient_name = ? AND email = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, name);
        ps.setString(2, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            patientID = rs.getInt("patient_id");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return patientID;
}



public boolean updateContactInfo(Patient patient,String email,String phone) throws IllegalArgumentException
{

    patient.updateContactInfo(phone,email);
    String sql ="UPDATE PATIENT SET PHONE_NO=? , EMAIL =? WHERE PATIENT_ID =?";





try(Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement(sql))

{
    ps.setString(1,phone);
    ps.setString(2,email);
    ps.setInt(3,patient.getPatientID());

    int rows= ps.executeUpdate();

    return rows>0;






}


catch(Exception e)
{
    e.printStackTrace();
}


return false;




}

    



}
