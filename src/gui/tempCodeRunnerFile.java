package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hospital Management System");

        // Buttons mirroring your main menu
        Button patientBtn = new Button("Patient");
        Button doctorBtn = new Button("Doctor");
        Button appointmentBtn = new Button("Appointments");
        Button billingBtn = new Button("Billing");
        Button exitBtn = new Button("Exit");

        // Event handlers
        patientBtn.setOnAction(e -> {
            PatientView patientView = new PatientView();
            patientView.setVisible(true); // Assuming PatientView is a JFrame
        });
        doctorBtn.setOnAction(e -> {
            DoctorView doctorView = new DoctorView();
            doctorView.setVisible(true); // Launch DoctorView (Swing JFrame)
        });
        appointmentBtn.setOnAction(e -> {
            AppointmentView appointmentView = new AppointmentView();
            appointmentView.setVisible(true); // Launch AppointmentView (Swing JFrame)
        });
        billingBtn.setOnAction(e -> {
            BillingView billingView = new BillingView();
            billingView.setVisible(true); // Launch BillingView (Swing JFrame)
        });
        exitBtn.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(patientBtn, doctorBtn, appointmentBtn, billingBtn, exitBtn);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}