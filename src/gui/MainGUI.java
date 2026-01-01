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
            patientView.show();
        });
        doctorBtn.setOnAction(e -> {
            // TODO: Launch DoctorView (your teammate can implement)
            System.out.println("Doctor menu not implemented yet.");
        });
        appointmentBtn.setOnAction(e -> {
            // TODO: Launch AppointmentView
            System.out.println("Appointments menu not implemented yet.");
        });
        billingBtn.setOnAction(e -> {
            // TODO: Launch BillingView
            System.out.println("Billing menu not implemented yet.");
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