package model;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import model.MedicalHistory;

public class Patient{

private String name;
private int id;

private String gender;

private int addressID;
private String phoneNumber;
private String emailId;


final private LocalDate dateOfBirth;

private String bloodGroup;
public List<MedicalHistory> medicalHistory ;

public Patient(String name,int id,String gender,String phoneNumber,String emailId,LocalDate date,String bloodGroup,int addressID)throws IllegalArgumentException
{

   if (date == null || date.isAfter(LocalDate.now())) {
    throw new IllegalArgumentException("Invalid Date of Birth");
}
    if(name==null|| !name.matches("^[a-zA-Z]+$"))
    {
        throw new IllegalArgumentException("Invalid Name");
    }
if(phoneNumber.length()!=10||!phoneNumber.matches("\\d{10}"))
{
    throw new IllegalArgumentException("Invalid Phone Number");
}
if(!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
{
    throw new IllegalArgumentException("Invalid Email ID");
}
if(addressID<=0)
{
    throw new IllegalArgumentException("Invalid Address ID");
}

    this.name = name;
    this.id = id;
    
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
    this.dateOfBirth = date ;
    this.bloodGroup = bloodGroup;
   /*doubt */ this.addressID =  addressID;

this.medicalHistory = new ArrayList<MedicalHistory>();




}

public int getAge()
{return Period.between(this.dateOfBirth, LocalDate.now()).getYears();


}

public void setName(String name) throws IllegalArgumentException
{

    if(name==null|| !name.matches("^[a-zA-Z]+$"))
    {
        throw new IllegalArgumentException("Invalid Name");

    }

    this.name = name;


}


public String getName()
{
    return this.name;
}
public void setId(int id) throws IllegalArgumentException
{

   if(id<0)
   {
    throw new IllegalArgumentException("ID cannot be negative");

   }

   this.id = id;



}
public void addMedicalHistory(MedicalHistory history)
{
    if(history== null)
    {
        throw new IllegalArgumentException("Medical History cannot be empty");

    }
    this.medicalHistory.add(history);
}



public List<String> getMedicalHistory() {
    List<String> history = new ArrayList<>();
    for(MedicalHistory mh : this.medicalHistory) {
        history.add(mh.toString());
    }
    return history;
}




public int getId()
{
    return this.id;
}
public void setGender(String gender)
{
this.gender = gender;


}

public String getGender()
{
    return this.gender;
}

public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException
{
    if(phoneNumber.length()!=10||!phoneNumber.matches("\\d{10}"))
    {
        throw new IllegalArgumentException("Invalid Phone Number");
    }
    this.phoneNumber = phoneNumber;
}

public String getPhoneNumber()
{
    return this.phoneNumber;
}
public void setEmailId(String emailId) throws IllegalArgumentException
{
    if(!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    {
        throw new IllegalArgumentException("Invalid Email ID");
    }
    this.emailId = emailId; 





}

public String getEmail()
{
    return this.emailId;
}

public void setBloodGroup(String bloodGroup)
{
    this.bloodGroup = bloodGroup;
}
public String getBloodGroup()
{
    return this.bloodGroup;
}

public LocalDate getDateOfBirth()
{
    return this.dateOfBirth;
}


public void updateAddressID(int addID) throws IllegalArgumentException {

if(addID<=0)
{
    throw new IllegalArgumentException("Invalid Address ID");
}

this.addressID = addID;




}
public int getAddressID()
{

    return this.addressID;

}


public void updateEmailId(String emailId)
{
if(!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
{
    throw new IllegalArgumentException("Invalid Email ID");
}
this.emailId = emailId;



}
public void updateContactInfo(String phoneNumber,String emailId)
{
     if(phoneNumber.length()!=10||!phoneNumber.matches("\\d{10}"))
    {
        throw new IllegalArgumentException("Invalid Phone Number");
    }
if(!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
{
    throw new IllegalArgumentException("Invalid Email ID");
}
this.phoneNumber = phoneNumber;
this.emailId = emailId;




}

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Patient ID: ").append(id)
      .append("\nName: ").append(name)
      .append("\nGender: ").append(gender)
      .append("\nDate of Birth: ").append(dateOfBirth)
      .append("\nAge: ").append(getAge())
      .append("\nBlood Group: ").append(bloodGroup)
      .append("\nPhone: ").append(phoneNumber)
      .append("\nEmail: ").append(emailId)
      .append("\nAddress: ").append(addressID)
      .append("\nMedical History:\n");

    if (medicalHistory.isEmpty()) {
        sb.append("No records\n");
    } else {
        for (MedicalHistory mh : medicalHistory) {
            sb.append(mh).append("\n");
        }
    }

    return sb.toString();
}







}