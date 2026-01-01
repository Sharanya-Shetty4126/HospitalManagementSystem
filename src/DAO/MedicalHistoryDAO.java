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
int rows = ps.executeUpdate();

if (rows > 0) {
    ResultSet rs = ps.getGeneratedKeys();
    if (rs.next()) {
        mh.setMedicalHistoryID(rs.getInt(1));
    }
}


return rows>0;



}
catch(SQLException e)
{
    e.printStackTrace();
}
return false;


}

public List<MedicalHistory> getMedicalHistoryByPatientId(int patientId) {
    List<MedicalHistory> historyList = new ArrayList<>();

    String sql = "SELECT * FROM MEDICAL_HISTORY WHERE PATIENT_ID = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, patientId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            MedicalHistory mh = new MedicalHistory(
                rs.getInt("history_id"),
                rs.getInt("patient_id"),
                rs.getString("pat_description"),
                rs.getDate("record_date").toLocalDate(),
                rs.getString("category"),
                rs.getString("dis_status")
            );

            historyList.add(mh);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return historyList;
}






} 
