package aydin.firebasedemo;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML
    private TextField ageTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;  // New phone number field

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Button readButton;

    @FXML
    private Button writeButton;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() {
        return listOfUsers;
    }

    @FXML
    void readButtonClicked(ActionEvent event) {
        readFirebase();
    }

    @FXML
    void writeButtonClicked(ActionEvent event) {
        addData();
    }

    public boolean readFirebase() {
        key = false;

        // Asynchronously retrieve all documents
        ApiFuture<QuerySnapshot> future = DemoApp.fstore.collection("Persons").get();
        List<QueryDocumentSnapshot> documents;
        try {
            documents = future.get().getDocuments();
            if (documents.size() > 0) {
                System.out.println("Reading data from Firebase database...");
                listOfUsers.clear();
                outputTextArea.clear();

                for (QueryDocumentSnapshot document : documents) {
                    String name = String.valueOf(document.getData().get("Name"));
                    int age = Integer.parseInt(document.getData().get("Age").toString());
                    String phone = document.getData().get("Phone") != null ? document.getData().get("Phone").toString() : "N/A";

                    outputTextArea.appendText(name + " , Age: " + age + " , Phone: " + phone + "\n");

                    person = new Person(name, age, phone);
                    listOfUsers.add(person);
                }
            } else {
                System.out.println("No data found.");
            }
            key = true;

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        return key;
    }

    public void addData() {
        DocumentReference docRef = DemoApp.fstore.collection("Persons").document(UUID.randomUUID().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameTextField.getText());
        data.put("Age", Integer.parseInt(ageTextField.getText()));
        data.put("Phone", phoneTextField.getText());  // Saving phone number

        // Asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }
}
