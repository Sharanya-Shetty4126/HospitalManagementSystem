package gui;

import DAO.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class PatientView extends JFrame {
    private PatientDAO pdao = new PatientDAO();
    private AddressDAO addao = new AddressDAO();
    private MedicalHistoryDAO mhdao = new MedicalHistoryDAO();
    private JTextArea outputArea;

    public PatientView() {
        setTitle("Patient Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();


        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
outputArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1));

        // Buttons for each menu option
        JButton addPatientBtn = new JButton("Add Patient");
        JButton viewAllBtn = new JButton("View All Patients");
        JButton viewAddressBtn = new JButton("View Address of a Patient");
        JButton addMedHistoryBtn = new JButton("Add Medical History");
        JButton viewMedHistoryBtn = new JButton("View Medical History");
        JButton viewDetailsBtn = new JButton("View Patient Details");
        JButton updateContactBtn = new JButton("Update Contact Info");
        JButton backBtn = new JButton("Back to Main Menu");

        // Event handlers (mirroring your switch cases)
        addPatientBtn.addActionListener(e -> handleAddPatient());
        viewAllBtn.addActionListener(e -> handleViewAllPatients());
        viewAddressBtn.addActionListener(e -> handleViewAddress());
        addMedHistoryBtn.addActionListener(e -> handleAddMedicalHistory());
        viewMedHistoryBtn.addActionListener(e -> handleViewMedicalHistory());
        viewDetailsBtn.addActionListener(e -> handleViewPatientDetails());
        updateContactBtn.addActionListener(e -> handleUpdateContact());
        backBtn.addActionListener(e -> dispose()); // Close this window

        buttonPanel.add(addPatientBtn);
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(viewAddressBtn);
        buttonPanel.add(addMedHistoryBtn);
        buttonPanel.add(viewMedHistoryBtn);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(updateContactBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Helper to fetch patient by choice (adapted from your fetchPatientByChoice)
    private Patient fetchPatientByChoice() {
        String[] options = {"Email", "Phone"};
        int choice = JOptionPane.showOptionDialog(this, "Select method to fetch patient:", "Fetch Patient",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == -1) return null;

        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        if (name == null) return null;

        if (choice == 0) { // Email
            String email = JOptionPane.showInputDialog(this, "Enter Email:");
            if (email == null) return null;
            int patid = pdao.getPatientIDByNameAndEmail(name, email);
            return (patid != -1) ? pdao.getPatientById(patid) : null;
        } else { // Phone
            String phone = JOptionPane.showInputDialog(this, "Enter Phone:");
            if (phone == null) return null;
            int patid = pdao.getPatientIDByNameAndPhone(name, phone);
            return (patid != -1) ? pdao.getPatientById(patid) : null;
        }
    }

    private void handleAddPatient() {
        try {
            // Collect basic patient details (mirroring your console input)
            String name = JOptionPane.showInputDialog(this, "Enter Patient Name:");
            if (name == null) return;
            
            String gender = JOptionPane.showInputDialog(this, "Enter Gender:");
            if (gender == null) return;
            
            String phoneNumber = JOptionPane.showInputDialog(this, "Enter Phone Number:");
            if (phoneNumber == null) return;
            
            String emailId = JOptionPane.showInputDialog(this, "Enter Email ID:");
            if (emailId == null) return;
            
            String dobStr = JOptionPane.showInputDialog(this, "Enter Date of Birth (YYYY-MM-DD):");
            if (dobStr == null) return;
            LocalDate dob = LocalDate.parse(dobStr);
            
            String bloodGroup = JOptionPane.showInputDialog(this, "Enter Blood Group:");
            if (bloodGroup == null) return;
            
            // Collect address details (mirroring takeAddressInput)
            String houseName = JOptionPane.showInputDialog(this, "Enter House Name:");
            if (houseName == null) return;
            
            String street = JOptionPane.showInputDialog(this, "Enter Street Name:");
            if (street == null) return;
            
            String city = JOptionPane.showInputDialog(this, "Enter City:");
            if (city == null) return;
            
            String state = JOptionPane.showInputDialog(this, "Enter State:");
            if (state == null) return;
            
            String country = JOptionPane.showInputDialog(this, "Enter Country:");
            if (country == null) return;
            
            String pinCode = JOptionPane.showInputDialog(this, "Enter Pin Code:");
            if (pinCode == null) return;
            
            Address address = new Address(country, state, city, street, houseName, pinCode);
            
            // Create Patient and add with address
            Patient p = new Patient(name, gender, phoneNumber, emailId, dob, bloodGroup, -1); // addressID set by DAO
            boolean success = pdao.addPatientWithAddress(p, address);
            
            if (success) {
                outputArea.setText("Patient Added Successfully!\nGenerated Patient ID: " + p.getPatientID() + "\nGenerated Address ID: " + address.getAddressID());
            } else {
                outputArea.setText("Failed to add patient and address.");
            }
            
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

    private void handleViewAllPatients() {
       
    List<Patient> patients = pdao.getAllPatients();
    if (patients == null || patients.isEmpty()) {
        outputArea.setText("No patients in the list.");
        return;
    }

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);

    for (Patient p : patients) {
        JPanel patientPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns, labels + values
        patientPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Patient ID: " + p.getPatientID()
        ));
        patientPanel.setBackground(Color.LIGHT_GRAY);

        patientPanel.add(new JLabel("Name:"));
        patientPanel.add(new JLabel(p.getName()));

        patientPanel.add(new JLabel("Gender:"));
        patientPanel.add(new JLabel(p.getGender()));

        patientPanel.add(new JLabel("Phone:"));
        patientPanel.add(new JLabel(p.getPhoneNumber()));

        patientPanel.add(new JLabel("Email:"));
        patientPanel.add(new JLabel(p.getEmail()));

        patientPanel.add(new JLabel("DOB:"));
        patientPanel.add(new JLabel(p.getDateOfBirth().toString()));

        patientPanel.add(new JLabel("Blood Group:"));
        patientPanel.add(new JLabel(p.getBloodGroup()));

        panel.add(patientPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing between patients
    }

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    JFrame frame = new JFrame("All Patients");
    frame.setSize(600, 500);
    frame.add(scrollPane);
    frame.setVisible(true);

    }

    private void handleViewAddress() {
        Patient pat = fetchPatientByChoice();
        if (pat == null) {
            outputArea.setText("Patient not found!");
            return;
        }
        Address address = addao.getAddressById(pat.getAddressID());
        outputArea.setText("Patient Address:\n" + address.toString());
    }

    private void handleAddMedicalHistory() {
        Patient pat = fetchPatientByChoice();
        if (pat == null) {
            outputArea.setText("Patient not found!");
            return;
        }
        
        try {
            // Collect medical history details (mirroring inputMedicalHistory)
            String desc = JOptionPane.showInputDialog(this, "Enter Description about the disease:");
            if (desc == null) return;
            
            String recordDateStr = JOptionPane.showInputDialog(this, "Enter Record Date for the Disease (YYYY-MM-DD):");
            if (recordDateStr == null) return;
            LocalDate recordDate = LocalDate.parse(recordDateStr);
            
            String category = JOptionPane.showInputDialog(this, "Enter The category (ongoing/Completed):");
            if (category == null) return;
            
            String status = JOptionPane.showInputDialog(this, "Enter The Status Of The disease:");
            if (status == null) return;
            
            MedicalHistory mh = new MedicalHistory(pat.getPatientID(), desc, recordDate, category, status);
            boolean success = mhdao.addMedicalHistory(pat.getPatientID(), mh);
            
            if (success) {
                outputArea.setText("Medical History Added Successfully!");
            } else {
                outputArea.setText("Failed to add medical history.");
            }
            
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

    private void handleViewMedicalHistory() {
        Patient pat = fetchPatientByChoice();
        if (pat == null) {
            outputArea.setText("Patient not found!");
            return;
        }
        List<MedicalHistory> records = mhdao.getMedicalHistoryByPatientId(pat.getPatientID());
        if (records.isEmpty()) {
            outputArea.setText("No medical history for " + pat.getName());
        } else {
            StringBuilder sb = new StringBuilder("Medical History for " + pat.getName() + ":\n");
            for (MedicalHistory m : records) {
                sb.append(m.toString()).append("\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void handleViewPatientDetails() {
        Patient pat = fetchPatientByChoice();
        if (pat == null) {
            outputArea.setText("Patient not found!");
        } else {

            outputArea.setText("Patient Details:\n" + pat.toString());
        }
    }

    private void handleUpdateContact() {
        Patient pat = fetchPatientByChoice();
        if (pat == null) {
            outputArea.setText("Patient not found!");
            return;
        }
        String newPhone = JOptionPane.showInputDialog(this, "Enter New Phone:");
        if (newPhone == null) return;
        String newEmail = JOptionPane.showInputDialog(this, "Enter New Email:");
        if (newEmail == null) return;

        boolean success = pdao.updateContactInfo(pat, newEmail, newPhone);
        outputArea.setText(success ? "Contact updated successfully!" : "Failed to update.");
    }
}