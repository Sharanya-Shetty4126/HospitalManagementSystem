package DAO;

import model.Patient;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


import connectivity.DBConnection;
import model.Patient;


public class PatientDAO {




public boolean addPatient(Patient patient)
{

String sql ="Insert into Patient(patient_name,gender,phone_no,email,date_of_birth,blood_group,address_id) values(?,?,?,?,?,?,?)";



try(Connection con = DBConnection.getConnection();
PreparedStatement ps = con.prepareStatement(sql))
{
    ps.setString(1,patient.getName());
    ps.setString(2,patient.getGender());
    ps.setString(3,patient.getPhoneNumber());
    ps.setString(4,patient.getEmail());
   ps.setDate(5, java.sql.Date.valueOf(patient.getDateOfBirth()));
ps.setString(6,patient.getBloodGroup());;
ps.setInt(7,patient.getAddressID());
int rowsAffected = ps.executeUpdate();
return rowsAffected>0;

}
catch(Exception e)
{
    e.printStackTrace();

}


return false;




}




public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";
        patients=null;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getString("name"),
                        rs.getInt("patient_id"),
                        rs.getString("gender"),
                        rs.getString("phone_no"),
                        rs.getString("email_id"),
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



// function for update patient details
















    
}
