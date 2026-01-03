package gui;

import DAO.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AppointmentView extends JFrame {
    private AppointmentDAO adao = new AppointmentDAO();
    private PatientDAO pdao = new PatientDAO();
    private DoctorDAO ddao = new DoctorDAO();
    private JTextArea outputArea;

    public AppointmentView() {
        setTitle("Appointment Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output Area (same style as PatientView)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Button Panel (same layout philosophy as PatientView)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));

        JButton addApptBtn = new JButton("Add Appointment");
        JButton viewAllBtn = new JButton("View All Appointments");
        JButton viewByPatientBtn = new JButton("View Appointments by Patient");
        JButton viewByDoctorBtn = new JButton("View Appointments by Doctor");
        JButton updateStatusBtn = new JButton("Update Appointment Status");
        JButton backBtn = new JButton("Back to Main Menu");

        addApptBtn.addActionListener(e -> handleAddAppointment());
        viewAllBtn.addActionListener(e -> handleViewAllAppointments());
        viewByPatientBtn.addActionListener(e -> handleViewByPatient());
        viewByDoctorBtn.addActionListener(e -> handleViewByDoctor());
        updateStatusBtn.addActionListener(e -> handleUpdateStatus());
        backBtn.addActionListener(e -> dispose());

        buttonPanel.add(addApptBtn);
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(viewByPatientBtn);
        buttonPanel.add(viewByDoctorBtn);
        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleAddAppointment() {
        try {
            String patientIdStr = JOptionPane.showInputDialog(this, "Enter Patient ID:");
            if (patientIdStr == null) return;
            int patientID = Integer.parseInt(patientIdStr);

            String doctorIdStr = JOptionPane.showInputDialog(this, "Enter Doctor ID:");
            if (doctorIdStr == null) return;
            int doctorID = Integer.parseInt(doctorIdStr);

            String dateStr = JOptionPane.showInputDialog(this, "Enter Appointment Date (YYYY-MM-DD):");
            if (dateStr == null) return;
            LocalDate date = LocalDate.parse(dateStr);

            String timeStr = JOptionPane.showInputDialog(this, "Enter Appointment Time (HH:MM):");
            if (timeStr == null) return;
            LocalTime time = LocalTime.parse(timeStr);

            String status = JOptionPane.showInputDialog(this, "Enter Status (Scheduled/Completed/Cancelled):");
            if (status == null) return;

            Appointment appt = new Appointment(patientID, doctorID, date, time, status);
            boolean success = adao.addAppointment(appt);

            outputArea.setText(
                success ? "Appointment Added Successfully!\nAppointment ID: " + appt.getAppointmentID()
                        : "Failed to add appointment."
            );
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

   private void handleViewAllAppointments() {
    List<Appointment> appointments = adao.getAllAppointments();
    if (appointments == null || appointments.isEmpty()) {
        outputArea.setText("No appointments found.");
        return;
    }

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);

    for (Appointment a : appointments) {
        JPanel apptPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        apptPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Appointment ID: " + a.getAppointmentID()
        ));
        apptPanel.setBackground(Color.LIGHT_GRAY);

        apptPanel.add(new JLabel("Patient ID:"));
        apptPanel.add(new JLabel(String.valueOf(a.getPatientID())));

        apptPanel.add(new JLabel("Doctor ID:"));
        apptPanel.add(new JLabel(String.valueOf(a.getDoctorID())));

        apptPanel.add(new JLabel("Date:"));
        apptPanel.add(new JLabel(a.getAppointmentDate().toString()));

        apptPanel.add(new JLabel("Time:"));
        apptPanel.add(new JLabel(a.getAppointmentTime().toString()));

        apptPanel.add(new JLabel("Status:"));
        apptPanel.add(new JLabel(a.getStatus()));

        panel.add(apptPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    JFrame frame = new JFrame("All Appointments");
    frame.setSize(600, 500);
    frame.add(scrollPane);
    frame.setVisible(true);
}


    private void handleViewByPatient() {
        String patientIdStr = JOptionPane.showInputDialog(this, "Enter Patient ID:");
        if (patientIdStr == null) return;
        int patientID = Integer.parseInt(patientIdStr);

        List<Appointment> appointments = adao.getAppointmentsByPatientId(patientID);
        if (appointments.isEmpty()) {
            outputArea.setText("No appointments for this patient.");
        } else {
            StringBuilder sb = new StringBuilder("Appointments for Patient ID " + patientID + ":\n");
            for (Appointment a : appointments) {
                sb.append(a).append("\n\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void handleViewByDoctor() {
        String doctorIdStr = JOptionPane.showInputDialog(this, "Enter Doctor ID:");
        if (doctorIdStr == null) return;
        int doctorID = Integer.parseInt(doctorIdStr);

        List<Appointment> appointments = adao.getAppointmentsByDoctorId(doctorID);
        if (appointments.isEmpty()) {
            outputArea.setText("No appointments for this doctor.");
        } else {
            StringBuilder sb = new StringBuilder("Appointments for Doctor ID " + doctorID + ":\n");
            for (Appointment a : appointments) {
                sb.append(a).append("\n\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void handleUpdateStatus() {
        String apptIdStr = JOptionPane.showInputDialog(this, "Enter Appointment ID:");
        if (apptIdStr == null) return;
        int appointmentID = Integer.parseInt(apptIdStr);

        String status = JOptionPane.showInputDialog(this, "Enter New Status (Scheduled/Completed/Cancelled):");
        if (status == null) return;

        boolean success = adao.updateAppointmentStatus(appointmentID, status);
        outputArea.setText(success ? "Status updated successfully!" : "Failed to update status.");
    }
}
