package model;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

public class Patient{

private String name;
private int id;

private String gender;

private Address address;
private String phoneNumber;
private String emailId;


private LocalDate dateOfBirth;

private String bloodGroup;

public Patient(String name,int id,String gender,String phoneNumber,String emailId,LocalDate date,String bloodGroup,Address address)throws IllegalArgumentException
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

    this.name = name;
    this.id = id;
    
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.emailId = emailId;
    this.dateOfBirth = date ;
    this.bloodGroup = bloodGroup;
   /*doubt */ this.address =  address;






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


public void updateAddress(Address newAddress) {
    if(newAddress == null) throw new IllegalArgumentException("Address cannot be null");
    this.address = newAddress;
}
public Address getAddress()
{

    return this.address;

}



}