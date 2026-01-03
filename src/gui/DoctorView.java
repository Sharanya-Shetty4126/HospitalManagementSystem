package gui;

import DAO.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorView extends JFrame {
    private DoctorDAO ddao = new DoctorDAO();
    private AddressDAO addao = new AddressDAO();
    private JTextArea outputArea;

    public DoctorView() {
        setTitle("Doctor Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1)); // Adjusted for fewer options compared to PatientView

        // Buttons for each menu option
        JButton addDoctorBtn = new JButton("Add Doctor");
        JButton viewAllBtn = new JButton("View All Doctors");
        JButton viewAddressBtn = new JButton("View Address of a Doctor");
        JButton viewDetailsBtn = new JButton("View Doctor Details");
        JButton updateContactBtn = new JButton("Update Contact Info");
        JButton backBtn = new JButton("Back to Main Menu");

        // Event handlers
        addDoctorBtn.addActionListener(e -> handleAddDoctor());
        viewAllBtn.addActionListener(e -> handleViewAllDoctors());
        viewAddressBtn.addActionListener(e -> handleViewAddress());
        viewDetailsBtn.addActionListener(e -> handleViewDoctorDetails());
        updateContactBtn.addActionListener(e -> handleUpdateContact());
        backBtn.addActionListener(e -> dispose()); // Close this window

        buttonPanel.add(addDoctorBtn);
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(viewAddressBtn);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(updateContactBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Helper to fetch doctor by choice (adapted from fetchPatientByChoice)
    private Doctor fetchDoctorByChoice() {
        String[] options = {"Email", "Phone"};
        int choice = JOptionPane.showOptionDialog(this, "Select method to fetch doctor:", "Fetch Doctor",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == -1) return null;

        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        if (name == null) return null;

        if (choice == 0) { // Email
            String email = JOptionPane.showInputDialog(this, "Enter Email:");
            if (email == null) return null;
            int docid = ddao.getDoctorIDByNameAndEmail(name, email);
            return (docid != -1) ? ddao.getDoctorById(docid) : null;
        } else { // Phone
            String phone = JOptionPane.showInputDialog(this, "Enter Phone:");
            if (phone == null) return null;
            int docid = ddao.getDoctorIDByNameAndPhone(name, phone);
            return (docid != -1) ? ddao.getDoctorById(docid) : null;
        }
    }

    private void handleAddDoctor() {
        try {
            // Collect basic doctor details
            String name = JOptionPane.showInputDialog(this, "Enter Doctor Name:");
            if (name == null) return;
            
            String specialty = JOptionPane.showInputDialog(this, "Enter Specialty:");
            if (specialty == null) return;
            
            String phoneNumber = JOptionPane.showInputDialog(this, "Enter Phone Number:");
            if (phoneNumber == null) return;
            
            String emailId = JOptionPane.showInputDialog(this, "Enter Email ID:");
            if (emailId == null) return;
            
            String licenseNumber = JOptionPane.showInputDialog(this, "Enter License Number:");
            if (licenseNumber == null) return;
            
            String yearsStr = JOptionPane.showInputDialog(this, "Enter Years of Experience:");
            if (yearsStr == null) return;
            int yearsOfExperience = Integer.parseInt(yearsStr);
            
            // Collect address details
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
            
            // Create Doctor and add with address
            Doctor d = new Doctor(name, specialty, phoneNumber, emailId, licenseNumber, yearsOfExperience, -1); // addressID set by DAO
            boolean success = ddao.addDoctorWithAddress(d, address);
            
            if (success) {
                outputArea.setText("Doctor Added Successfully!\nGenerated Doctor ID: " + d.getDoctorID() + "\nGenerated Address ID: " + address.getAddressID());
            } else {
                outputArea.setText("Failed to add doctor and address.");
            }
            
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

    private void handleViewAllDoctors() {
        List<Doctor> doctors = ddao.getAllDoctors();
        if (doctors == null || doctors.isEmpty()) {
            outputArea.setText("No doctors in the list.");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        for (Doctor d : doctors) {
            JPanel doctorPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // 2 columns, labels + values
            doctorPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Doctor ID: " + d.getDoctorID()
            ));
            doctorPanel.setBackground(Color.LIGHT_GRAY);

            doctorPanel.add(new JLabel("Name:"));
            doctorPanel.add(new JLabel(d.getName()));

            doctorPanel.add(new JLabel("Specialty:"));
            doctorPanel.add(new JLabel(d.getSpecialty()));

            doctorPanel.add(new JLabel("Phone:"));
            doctorPanel.add(new JLabel(d.getPhoneNumber()));

            doctorPanel.add(new JLabel("Email:"));
            doctorPanel.add(new JLabel(d.getEmailId()));

            doctorPanel.add(new JLabel("License Number:"));
            doctorPanel.add(new JLabel(d.getLicenseNumber()));

            doctorPanel.add(new JLabel("Years of Experience:"));
            doctorPanel.add(new JLabel(String.valueOf(d.getYearsOfExperience())));

            panel.add(doctorPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // spacing between doctors
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("All Doctors");
        frame.setSize(600, 500);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void handleViewAddress() {
        Doctor doc = fetchDoctorByChoice();
        if (doc == null) {
            outputArea.setText("Doctor not found!");
            return;
        }
        Address address = addao.getAddressById(doc.getAddressID());
        outputArea.setText("Doctor Address:\n" + address.toString());
    }

    private void handleViewDoctorDetails() {
        Doctor doc = fetchDoctorByChoice();
        if (doc == null) {
            outputArea.setText("Doctor not found!");
        } else {
            outputArea.setText("Doctor Details:\n" + doc.toString());
        }
    }

    private void handleUpdateContact() {
        Doctor doc = fetchDoctorByChoice();
        if (doc == null) {
            outputArea.setText("Doctor not found!");
            return;
        }
        String newPhone = JOptionPane.showInputDialog(this, "Enter New Phone:");
        if (newPhone == null) return;
        String newEmail = JOptionPane.showInputDialog(this, "Enter New Email:");
        if (newEmail == null) return;

        boolean success = ddao.updateContactInfo(doc, newEmail, newPhone);
        outputArea.setText(success ? "Contact updated successfully!" : "Failed to update.");
    }
}