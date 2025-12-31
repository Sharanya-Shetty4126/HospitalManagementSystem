package model;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import model.MedicalHistory;

public class Patient{

private String name;
// private int id;

private int patientID;
private String gender;

private int addressID=-1;
private String phoneNumber;
private String emailId;


final private LocalDate dateOfBirth;

private String bloodGroup;
// public List<MedicalHistory> medicalHistory ;

public Patient(String name,String gender,String phoneNumber,String emailId,LocalDate date,String bloodGroup,int addressID)throws IllegalArgumentException
{

   if (date == null || date.isAfter(LocalDate.now())) {
    throw new IllegalArgumentException("Invalid Date of Birth");
}
    // if(name==null|| !name.matches("^[a-zA-Z]+( [a-zA-Z]+)*$\r\n" + //
    //             ""))
    // {
    //     throw new IllegalArgumentException("Invalid Name");
    // }

if(phoneNumber.length()!=10||!phoneNumber.matches("\\d{10}"))
{
    throw new IllegalArgumentException("Invalid Phone Number");
}
if(!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
{
    throw new IllegalArgumentException("Invalid Email ID");
}
// if(addressID<=0)
// {
//     throw new IllegalArgumentException("Invalid Address ID");
// }
  if (!gender.equalsIgnoreCase("male") &&
        !gender.equalsIgnoreCase("female") &&
        !gender.equalsIgnoreCase("other")) {
        throw new IllegalArgumentException("Invalid Gender");
    }
      if (!bloodGroup.matches("^(A|B|AB|O)[+-]$")) {
        throw new IllegalArgumentException("Invalid Blood Group");
    }

    this.name = name;
    
    
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
    this.dateOfBirth = date ;
    this.bloodGroup = bloodGroup;
   /*doubt */ this.addressID =  addressID;

// this.medicalHistory = new ArrayList<MedicalHistory>();




}
public Patient(int patientID, String name, String gender, String phoneNumber, String emailId,
               LocalDate dateOfBirth, String bloodGroup, int addressID) {
    this.patientID = patientID;
    this.name = name;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
    this.dateOfBirth = dateOfBirth;
    this.bloodGroup = bloodGroup;
    this.addressID = addressID;
    // this.medicalHistory = new ArrayList<>();
}


public void setPatientID(int id)
{
    this.patientID =id;


}
public int getPatientID()
{
    return this.patientID;
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



public boolean hasAddress()
{
    return addressID !=-1;
}
public String getName()
{
    return this.name;
}
// public void setId(int id) throws IllegalArgumentException
// {

//    if(id<0)
//    {
//     throw new IllegalArgumentException("ID cannot be negative");

//    }

//    this.patientID =id;



// }
// public void addMedicalHistory(MedicalHistory history)
// {
//     if(history== null)
//     {
//         throw new IllegalArgumentException("Medical History cannot be empty");

//     }
//     this.medicalHistory.add(history);
// }



// public List<MedicalHistory> getMedicalHistory() {
//     return this.medicalHistory;
// }





// public int getId()
// {
//     return this.i;
// }
public void setGender(String gender) {
    if (!gender.equalsIgnoreCase("male") &&
        !gender.equalsIgnoreCase("female") &&
        !gender.equalsIgnoreCase("other")) {
        throw new IllegalArgumentException("Invalid Gender");
    }
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

public void setBloodGroup(String bloodGroup) {
    if (!bloodGroup.matches("^(A|B|AB|O)[+-]$")) {
        throw new IllegalArgumentException("Invalid Blood Group");
    }
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


public void setAddressID(int addID) throws IllegalArgumentException {

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
    sb.append("Patient ID: ").append(patientID)
      .append("\nName: ").append(name)
      .append("\nGender: ").append(gender)
      .append("\nDate of Birth: ").append(dateOfBirth)
      .append("\nAge: ").append(getAge())
      .append("\nBlood Group: ").append(bloodGroup)
      .append("\nPhone: ").append(phoneNumber)
      .append("\nEmail: ").append(emailId)
      .append("\nAddress: ").append(addressID);

    // if (medicalHistory.isEmpty()) {
    //     sb.append("No records\n");
    // }
    
    // els
    //     for (MedicalHistory mh : medicalHistory) {
    //         sb.append(mh).append("\n");
    //     }
    // }

    return sb.toString();
}







}