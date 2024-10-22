package aydin.firebasedemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;

public class DemoApp extends Application {
    public static Scene scene;
    public static Firestore fstore;
    public static FirebaseAuth fauth;

    @Override
    public void start(Stage stage) throws IOException {
        initializeFirebase();

        scene = new Scene(loadFXML("welcome"), 640, 480); // Start with welcome screen
        stage.setScene(scene);
        stage.setTitle("Firebase JavaFX Demo");
        stage.show();
    }

    private void initializeFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/aydin/firebasedemo/key.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

            fstore = FirestoreClient.getFirestore();
            fauth = FirebaseAuth.getInstance();

            System.out.println("Firebase initialized successfully!");

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DemoApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load(); // This ensures it returns a Parent node
    }

    public static void main(String[] args) {
        launch();
    }
}
