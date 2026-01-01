package model;

public class Address {
    
private int addID;

private String street;
private String pinCode;
private String city;
private String state;
private String Country;

private String houseName;


// constructor for valuse;
public Address(String Country,String state , String city, String street,String houseName,String pinCode) throws IllegalArgumentException
{
if(pinCode.length()!=6|| !pinCode.matches("\\d{6}"))
{
    throw new IllegalArgumentException("Invalid Pin Code");
}


    this.Country = Country;
     this.state = state;
     this.city = city;
     this.street = street;
     this.houseName = houseName;
     
     this.pinCode = pinCode;


}

// constructor for valuse;
public Address(int add_id,String Country,String state , String city, String street,String houseName,String pinCode) throws IllegalArgumentException
{
if(pinCode.length()!=6|| !pinCode.matches("\\d{6}"))
{
    throw new IllegalArgumentException("Invalid Pin Code");
}
this.addID =add_id;

    this.Country = Country;
     this.state = state;
     this.city = city;
     this.street = street;
     this.houseName = houseName;
     
     this.pinCode = pinCode;


}
// constructor for no arg 
public Address()
{
this.Country = "Country Name";
     this.state = "State Name";
     this.city = "City Name";
     this.street = "Street Name";
     this.houseName = "House Name";
     
     this.pinCode = "Pin Code";


}

public void setAddressID(int id)
{
    this.addID = id;

}
public int getAddressID()
{
    return this.addID;
}

public void setCity(String City)
{

    this.city = City;
}
public void setCountry(String country)
{
    this.Country = country;
}

public void setStreet(String street)
{
    this.street = street;
}
public void setPinCode(String pin)
{
if(pin.length()==6&& pin.matches("\\d{6}"))
{
    this.pinCode =pin;
}
else{
    System.out.println("Invalid Pin Code");

}



}
public void setState(String state)
{
    this.state = state;
}
public void setHouseName(String houseName)
{
    this.houseName = houseName;
}


public String getCity()
{
    return this.city;
}
public String getCountry()
{
    return this.Country;
}
public String getStreet()
{
    return this.street;
}
public String getPinCode()
{
    return this.pinCode;
}
public String getState()
{
    return this.state;
}
public String getHouseName()
{
    return this.houseName;
}

public String toString()
{

return this.houseName+" "+this.street+" "+this.city+" "+this.state+" "+this.Country+" - "+this.pinCode;





}




}
