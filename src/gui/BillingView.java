package gui;

import DAO.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BillingView extends JFrame {
    private BillingDAO bdao = new BillingDAO();
    private AppointmentDAO adao = new AppointmentDAO(); // For generating bills tied to appointments
    private JTextArea outputArea;

    public BillingView() {
        setTitle("Billing Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Output Area (same style as AppointmentView)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Button Panel (same layout philosophy as AppointmentView)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1)); // Added one more button

        JButton addBillBtn = new JButton("Add Billing");
        JButton generateBillBtn = new JButton("Generate Bill for Appointment");
        JButton viewAllBtn = new JButton("View All Billings");
        JButton viewByApptBtn = new JButton("View Billings by Appointment ID");
        JButton updateStatusBtn = new JButton("Update Billing Status");
        JButton viewPendingBtn = new JButton("View Pending Billings");
        JButton backBtn = new JButton("Back to Main Menu");

        addBillBtn.addActionListener(e -> handleAddBilling());
        generateBillBtn.addActionListener(e -> handleGenerateBillForAppointment());
        viewAllBtn.addActionListener(e -> handleViewAllBillings());
        viewByApptBtn.addActionListener(e -> handleViewByAppointment());
        updateStatusBtn.addActionListener(e -> handleUpdateStatus());
        viewPendingBtn.addActionListener(e -> handleViewPendingBillings());
        backBtn.addActionListener(e -> dispose());

        buttonPanel.add(addBillBtn);
        buttonPanel.add(generateBillBtn);
        buttonPanel.add(viewAllBtn);
        buttonPanel.add(viewByApptBtn);
        buttonPanel.add(updateStatusBtn);
        buttonPanel.add(viewPendingBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void handleAddBilling() {
        try {
            String apptIdStr = JOptionPane.showInputDialog(this, "Enter Appointment ID:");
            if (apptIdStr == null) return;
            int appointmentID = Integer.parseInt(apptIdStr);

            String amountStr = JOptionPane.showInputDialog(this, "Enter Amount:");
            if (amountStr == null) return;
            double amount = Double.parseDouble(amountStr);

            String dateStr = JOptionPane.showInputDialog(this, "Enter Billing Date (YYYY-MM-DD):");
            if (dateStr == null) return;
            LocalDate date = LocalDate.parse(dateStr);

            String status = JOptionPane.showInputDialog(this, "Enter Status (Pending/Paid):");
            if (status == null) return;

            Billing bill = new Billing(appointmentID, amount, date, status);
            boolean success = bdao.addBilling(bill);

            outputArea.setText(
                success ? "Billing Added Successfully!\nBilling ID: " + bill.getBillingID()
                        : "Failed to add billing."
            );
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

    private void handleGenerateBillForAppointment() {
        try {
            String apptIdStr = JOptionPane.showInputDialog(this, "Enter Appointment ID to Generate Bill:");
            if (apptIdStr == null) return;
            int appointmentID = Integer.parseInt(apptIdStr);

            boolean success = bdao.generateBillForAppointment(appointmentID);
            outputArea.setText(
                success ? "Bill Generated Successfully for Appointment ID: " + appointmentID
                        : "Failed to generate bill (appointment not completed or invalid)."
            );
        } catch (Exception e) {
            outputArea.setText("ERROR: " + e.getMessage());
        }
    }

    private void handleViewAllBillings() {
        List<Billing> billings = bdao.getAllBillings();
        if (billings == null || billings.isEmpty()) {
            outputArea.setText("No billings found.");
            return;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        for (Billing b : billings) {
            JPanel billPanel = new JPanel(new GridLayout(0, 2, 5, 5));
            billPanel.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(Color.BLACK),
                    "Billing ID: " + b.getBillingID()
            ));
            billPanel.setBackground(Color.LIGHT_GRAY);

            billPanel.add(new JLabel("Appointment ID:"));
            billPanel.add(new JLabel(String.valueOf(b.getAppointmentID())));

            billPanel.add(new JLabel("Amount:"));
            billPanel.add(new JLabel("$" + String.format("%.2f", b.getAmount())));

            billPanel.add(new JLabel("Billing Date:"));
            billPanel.add(new JLabel(b.getBillingDate().toString()));

            billPanel.add(new JLabel("Status:"));
            billPanel.add(new JLabel(b.getStatus()));

            panel.add(billPanel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JFrame frame = new JFrame("All Billings");
        frame.setSize(600, 500);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void handleViewByAppointment() {
        String apptIdStr = JOptionPane.showInputDialog(this, "Enter Appointment ID:");
        if (apptIdStr == null) return;
        int appointmentID = Integer.parseInt(apptIdStr);

        List<Billing> billings = bdao.getBillingsByAppointmentId(appointmentID);
        if (billings.isEmpty()) {
            outputArea.setText("No billings for this appointment.");
        } else {
            StringBuilder sb = new StringBuilder("Billings for Appointment ID " + appointmentID + ":\n");
            for (Billing b : billings) {
                sb.append(b).append("\n\n");
            }
            outputArea.setText(sb.toString());
        }
    }

    private void handleUpdateStatus() {
        String billIdStr = JOptionPane.showInputDialog(this, "Enter Billing ID:");
        if (billIdStr == null) return;
        int billingID = Integer.parseInt(billIdStr);

        String status = JOptionPane.showInputDialog(this, "Enter New Status (Pending/Paid):");
        if (status == null) return;

        boolean success = bdao.updateBillingStatus(billingID, status);
        outputArea.setText(success ? "Status updated successfully!" : "Failed to update status.");
    }

    private void handleViewPendingBillings() {
        List<Billing> billings = bdao.getPendingBillings();
        if (billings.isEmpty()) {
            outputArea.setText("No pending billings.");
        } else {
            StringBuilder sb = new StringBuilder("Pending Billings:\n");
            for (Billing b : billings) {
                sb.append(b).append("\n\n");
            }
            outputArea.setText(sb.toString());
        }
    }
}