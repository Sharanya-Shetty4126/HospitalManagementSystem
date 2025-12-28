
import DAO.PatientDAO;

import model.Patient;


import java.time.LocalDate;

public class DemoMain {
    public static void main(String[] args) {

        try {
            // // Insert Address
            // AddressDAO addressDAO = new AddressDAO();
            // Address address = new Address(0, "India", "Karnataka", "Bangalore", "MG Road", "Green Villa", "560001");
            // int addressId = addressDAO.addAddress(address);
            // System.out.println("Inserted Address ID: " + addressId);

            // Insert Patient linked to Address
            PatientDAO patientDAO = new PatientDAO();
            Patient patient = new Patient(
                    "Sharanya",
                    0, // placeholder, DB generates patient_id
                    "Female",
                    "9876543210",
                    "sharanya@example.com",
                    LocalDate.of(2000, 5, 15),
                    "O+",
                    1
            );

            // int patientId = patientDAO.addPatient(patient);
            // System.out.println("Inserted Patient ID: " + patientId);

            // Fetch all patients
            for (Patient p : patientDAO.getAllPatients()) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
