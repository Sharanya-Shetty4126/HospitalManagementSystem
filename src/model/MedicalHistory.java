package model;
import java.time.LocalDate;
import java.time.LocalTime;



// this class purpose is to store medical history of the Patient
// not everytime a patient will have a medical history with a diagnosis so its upto how the authrities who will take and use the data
// this medical history class can ne used for patient class with a clean and modular design with the effective setters and getters method and also constructor
// we didnt use no arg constructor like we have used in other classes because if there is no medical histiry there is no need to take any data for the patient or store so thats the reason


public class MedicalHistory {
    
    private int patient_id;
    private int history_id;

private String description;
private LocalDate recordDate;
private String category;
private String status;

public MedicalHistory(int pat_id,String description , LocalDate recordDate,String category,String status) throws IllegalArgumentException
{
    if(description==null||description.trim().isEmpty())
    {
throw new IllegalArgumentException("Invalid Description");


    }
    if(recordDate==null|| recordDate.isAfter(LocalDate.now()))
{
    throw new IllegalArgumentException("Invalid Record Date");

}
if(category==null||category.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Category");

}
if(status==null||status.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Status");
}


this.patient_id = pat_id;

this.description = description;
this.recordDate = recordDate;
this.category = category;
this.status = status;





}



public MedicalHistory(int hist_id,int pat_id,String description , LocalDate recordDate,String category,String status) throws IllegalArgumentException
{
    if(description==null||description.trim().isEmpty())
    {
throw new IllegalArgumentException("Invalid Description");


    }
    if(recordDate==null|| recordDate.isAfter(LocalDate.now()))
{
    throw new IllegalArgumentException("Invalid Record Date");

}
if(category==null||category.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Category");

}
if(status==null||status.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Status");
}

this.history_id =hist_id;

this.patient_id = pat_id;

this.description = description;
this.recordDate = recordDate;
this.category = category;
this.status = status;


}


public void setStatus(String status) throws IllegalArgumentException
{
    if(status==null||status.trim().isEmpty())
    {
        throw new IllegalArgumentException("Invalid Status");
    }
this.status = status;

}

public void setCategory(String category) throws IllegalArgumentException{

if(category==null||category.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Category");
}

this.category = category;


}

public void setDescription(String description) throws IllegalArgumentException{

if(description==null||description.trim().isEmpty())
{
    throw new IllegalArgumentException("Invalid Description");
}
this.description = description;


}


public void setRecordDate(LocalDate recordDate)throws IllegalArgumentException
{

if(recordDate==null||recordDate.isAfter(LocalDate.now()))
{
throw new IllegalArgumentException("Invalid Record Date");

}
this.recordDate = recordDate;




}

public String getDescription()
{
    return this.description;
}

public LocalDate getRecordDate(){
    return this.recordDate;
}
public String getCategory()
{
    return this.category;
}
public String getStatus()
{
    return this.status;
}
public void setMedicalHistoryID(int id)throws Exception
{
    if(id<=0)throw new Exception("Invalid History id");

    this.history_id = id;
}
public int getHistoryID()
{
    return this.history_id;
}
public int getPatientID()
{
    return this.patient_id;
}
@Override

public String toString()
{

return  "| "+this.description+" | "+this.recordDate+" | "+this.category+" | "+this.status+" |";



}









}
