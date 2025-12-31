package DAO;



import connectivity.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class MedicalHistoryDAO
 

{



public boolean addMedicalHistory(int patientId,MedicalHistory mh) throws Exception
{


String sql ="INSERT INTO MEDICAL_HISTORY (PATIENT_ID,PAT_DESCRIPTION,RECORD_DATE,CATEGORY,DIS_STATUS) VALUES(?,?,?,?,?)";

try(Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS) )
{

ps.setInt(1,mh.getPatientID());
ps.setString(2,mh.getDescription());
ps.setDate(3,java.sql.Date.valueOf(mh.getRecordDate()));
ps.setString(4,mh.getCategory());
ps.setString(5,mh.getStatus());

ResultSet rs = ps.getGeneratedKeys();
if (rs.next()) {
    
    mh.setMedicalHistoryID(rs.getInt(1));


}

int rows = ps.executeUpdate();

return rows>0;



}
catch(SQLException e)
{
    e.printStackTrace();
}
return false;


}







} 
