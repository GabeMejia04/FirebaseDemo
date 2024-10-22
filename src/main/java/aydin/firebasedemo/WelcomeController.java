package aydin.firebasedemo;

import javafx.fxml.FXML;
import java.io.IOException;

public class WelcomeController {

    // Handle Register Button click
    @FXML
    private void handleRegister() throws IOException {
        // Switch to register view (or handle register logic here)
        System.out.println("Register button clicked");
        // For now, let's just print to the console.
        // You can link this to a separate register screen as per your requirement.
        DemoApp.setRoot("register");
    }

    // Handle Sign In Button click
    @FXML
    private void handleSignIn() throws IOException {
        // Switch to the Sign In screen where the user can provide their email and password
        System.out.println("Sign In button clicked");
        DemoApp.setRoot("signin");  // Go to the Sign In FXML screen
    }
}
