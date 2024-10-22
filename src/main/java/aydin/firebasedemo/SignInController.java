package aydin.firebasedemo;

import com.google.firebase.auth.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignInController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private void handleSignIn() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showErrorAlert("Input Error", "Please enter both email and password.");
            return;
        }

        try {
            // Sign in with Firebase Authentication
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);

            if (userRecord != null) {
                System.out.println("Successfully signed in with UID: " + userRecord.getUid());

                // Switch to the primary view upon successful sign-in
                DemoApp.setRoot("primary");
            } else {
                showErrorAlert("Sign-in Failed", "Invalid email or password.");
            }
        } catch (FirebaseAuthException e) {
            showErrorAlert("Sign-in Error", "Authentication failed: " + e.getMessage());
        } catch (IOException e) {
            showErrorAlert("Error", "Failed to switch to the primary view.");
        }
    }


    // Helper method to show error alerts
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
