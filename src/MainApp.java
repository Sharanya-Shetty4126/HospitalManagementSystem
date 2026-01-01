import DAO.*;
import model.*;
import java.time.LocalDate;

import java.util.List;
import java.util.Scanner;



public class MainApp {

    public static int viewPhone(Scanner sc,PatientDAO p) { String Name; String phone; System.out.print("Enter Name of the patient = "); Name = sc.nextLine(); System.out.print("Enter phone of patient = "); phone = sc.nextLine(); int patid = p.getPatientIDByNameAndPhone(Name,phone); return patid;}
public static int viewEmail(Scanner sc,PatientDAO p) { String Name; String email; System.out.print("Enter Name of the patient = "); Name = sc.nextLine(); System.out.print("Enter Email of patient = "); email = sc.nextLine(); int patid = p.getPatientIDByNameAndEmail(Name,email); return patid; }


    public static Patient fetchPatientByChoice(Scanner sc, PatientDAO pdao) {
    System.out.println("Select method to fetch patient details: email(1) / phone(2) = ");
    int select = sc.nextInt();
    sc.nextLine(); 

    int patid = -1;
    if (select == 1) {
        patid = viewEmail(sc, pdao);
    } else if (select == 2) {
        patid = viewPhone(sc, pdao);
    } else {
        System.out.println("Invalid choice!");
        return null;
    }

    if (patid == -1) {
        System.out.println("Patient not found!");
        return null;
    }

    Patient pat = pdao.getPatientById(patid);
    if (pat == null) {
        System.out.println("Patient not found in the list!");
        return null;
    }

    return pat;
}



    public static MedicalHistory inputMedicalHistory(Scanner sc,int pat_id)
    {
        System.out.println("==================Enter Medical History==================");
        MedicalHistory mh = null;

        System.out.print("Enter Description about the disease = ");
        String des = sc.nextLine();
        System.out.print("Enter Record Date for the Disease = ");
    LocalDate record_date = LocalDate.parse(sc.nextLine());
    System.out.print("Enter The category (ongoing/Completed) :");
    String cat = sc.nextLine();
    System.out.println("Enter The Status Of The disease =  ");
    String status = sc.nextLine();
    mh = new MedicalHistory(pat_id,des,record_date,cat,status);
    return mh;


    }

public static Address takeAddressInput(Scanner sc)
{
    System.out.println("============Enter Address Details============");
    Address address=null;
System.out.println("Enter House Name : ");
String houseName = sc.nextLine();
System.out.println("Enter Street Name : ");
String street = sc.nextLine();  
System.out.println("Enter City : ");
String city = sc.nextLine();        
System.out.println("Enter State : ");
String state = sc.nextLine();
System.out.println("Enter Country : ");
String country = sc.nextLine();
System.out.println("Enter Pin Code : ");
String pinCode = sc.nextLine();
try 
{
    address = new Address(country,state,city,street,houseName,pinCode);
    
}

catch(IllegalArgumentException e)
{
    System.out.println(e.getMessage());
    return null;
}

return address;




}


    public static void patientMenu(Scanner sc)
    {
int choice;

PatientDAO pdao = new PatientDAO();

        System.out.println("================Patient Menu================");

        do{


System.out.println("1.Add Patient");
System.out.println("2.View All Patients");
System.out.println("3.View Address of a patient");
System.out.println("4.Add Medical History for a patient");
System.out.println("5.View medical history for the patient");
System.out.println("6.View patient Details");
System.out.println("7.Update Contact Info of patient ");
System.out.println("8.Back To Main Menu");


System.out.println("\nEnter Your Choice : ");
choice = sc.nextInt();
sc.nextLine();

switch(choice)
{
case 1:
{
    System.out.println("Enter Patient Name: ");
    String name = sc.nextLine();

    System.out.println("Enter Gender: ");
    String gender = sc.nextLine();

    System.out.println("Enter Phone Number: ");
    String phoneNumber = sc.nextLine();

    System.out.println("Enter Email ID: ");
    String emailId = sc.nextLine();

    System.out.println("Enter Date of Birth (YYYY-MM-DD): ");
    LocalDate dob = LocalDate.parse(sc.nextLine());

    System.out.println("Enter Blood Group: ");
    String bloodGroup = sc.nextLine();

    // Take Address Input
    Address address = takeAddressInput(sc);
    if (address == null) {
        System.out.println("\nAddress Not Valid! Patient Cannot be added!");
        break;
    }

    try {
        Patient p = new Patient(
            name, gender, phoneNumber, emailId, dob, bloodGroup, -1 // addressID will be set by DAO
        );

        PatientDAO patientDAO = new PatientDAO();
        boolean success = patientDAO.addPatientWithAddress(p, address);

        if (success) {
            System.out.println("Patient Added Successfully!");
            System.out.println("Generated Patient ID: " + p.getPatientID());
            System.out.println("Generated Address ID: " + address.getAddressID());
        } else {
            System.out.println("Failed to add patient and address.");
        }

    } catch (IllegalArgumentException e) {
        System.out.println("ERROR!!! " + e.getMessage());
    }

    break;
}


    


    case 2:

    {

PatientDAO patientDAO = new PatientDAO();
List<Patient>patients = patientDAO.getAllPatients();

if(patients==null||patients.isEmpty())
{
    System.out.println("No patients in the list");
    break;
}

System.out.println("============PATIENT LIST============");


        for(Patient p : patients) {
            System.out.println("--------------------------------------------");
            System.out.println(p); 
                        System.out.println("--------------------------------------------");



    }
break;


}
case 3:
{
    AddressDAO addao= new AddressDAO();
Patient pat = fetchPatientByChoice(sc,pdao);
if(pat==null)
{
    System.out.println("Patient Not Found!!");
    break;
}

Address pat_address = addao.getAddressById(pat.getAddressID());

System.out.println("Patient stay details are below = ");
System.out.println(pat_address);




    break;
}

case 4:
    {


Patient pat = fetchPatientByChoice(sc,pdao);
if(pat==null)
{
    System.out.println("Patient Not Found!!");
    break;
}
int stop =1;
while(stop!=0){

    
MedicalHistory mh = inputMedicalHistory(sc,pat.getPatientID());


MedicalHistoryDAO mhdao = new MedicalHistoryDAO();
try{
boolean success = mhdao.addMedicalHistory(pat.getPatientID(),mh);
}
catch(Exception e)
{
    System.out.println("Error "+e.getMessage());
}
System.out.println("Enter 0 to stop adding medocal History files = ");
stop = sc.nextInt();
sc.nextLine();

}


        break;
    }

  
        case 5:
            {
                MedicalHistoryDAO mdao = new MedicalHistoryDAO();
Patient pat = fetchPatientByChoice(sc,pdao);
if(pat==null)
{
    System.out.println("Patient Not Found!!");
    break;
}
List<MedicalHistory>records = mdao.getMedicalHistoryByPatientId(pat.getPatientID());

if(records.isEmpty())
{
    System.out.println("No Medical History Records For this patient "+pat.getName());
    break;
}
System.out.println("Medical records for the patient "+pat.getName()+" Are Listed Below");
for(MedicalHistory m:records)
{
    System.out.println(m);
}

                break;
            }
            case 6:{
  
int patid;


Patient pat = fetchPatientByChoice(sc,pdao);
if(pat==null)
{
    System.out.println("Patient Not Found in the list!!");
}

  else  System.out.println(pat);
                 
break;



                
}
                case 7:
                    {


                

System.out.println("select which way (email(1)/phone(2)) to fetch patient details = ");


Patient pat = fetchPatientByChoice(sc,pdao);
if(pat==null)
{
    System.out.println("Patient Not Found in the list!!");
break;
}

System.out.println("Write new phone and email below = ");
String new_phone = sc.nextLine();
String new_email = sc.nextLine();

try{
    boolean success= pdao.updateContactInfo(pat,new_email,new_phone);
    if(success)
    {
        System.out.println("Contact Details Updated Sucessfullyyy");
        break;
    }
    else{
        System.out.println("Failed To Update Contact Details");
        break;
    }

}
catch(IllegalArgumentException e)
{
    System.out.println("Error!!! "+e.getMessage());
}



break;




                    }

        case 8:
            {
                System.out.println("Patient menu is closed thank you!!!");
                break;
            }


default:
    {
        System.out.println("\nYou have entered wrong choice try again!!!!");
        break;
    }}
}while(choice !=8);



    }










    

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int choice;

    do {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Patient");
        System.out.println("2. Doctor");
        System.out.println("3. Appointments");
        System.out.println("4. Billing");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
        choice = sc.nextInt();
        sc.nextLine(); 

        switch(choice) {
            case 1:
                patientMenu(sc);
                break;
            case 2:
                // doctorMenu(sc);
                break;
            case 3:
                // appointmentMenu(sc);
                break;
            case 4:
                // billingMenu(sc);
                break;
            case 5:
                System.out.println("Thanks For Uisng The Hospital Managemnt System App !!Have A Lovely Day☺️");
                break;
            default:
                System.out.println("Invalid choice. Try again.");
        }
    } while(choice != 5);

    sc.close();
}

}
