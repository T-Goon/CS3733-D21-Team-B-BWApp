package edu.wpi.teamB.views;

import com.jfoenix.controls.JFXComboBox;
import edu.wpi.teamB.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class InternalTransportationRequestController implements Initializable {

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSubmit;
    @FXML
    private Button btnHelp;
    @FXML
    private TextField NAME;
    @FXML
    private TextField ROOMNUM;
    @FXML
    private JFXComboBox<Label> comboTransportType = new JFXComboBox<>();
    @FXML
    private TextArea REASON;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboTransportType.getItems().add(new Label("Wheelchair"));
        comboTransportType.getItems().add(new Label("Stretcher"));
        comboTransportType.getItems().add(new Label("Gurney"));
    }

    @FXML
    public void handleButtonAction(ActionEvent e) {
        Button bnt = (Button) e.getSource();

        if (bnt.getId().equals("btnCancel")) {
            // Go back to the service request menu
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/edu/wpi/teamB/views/serviceRequestMenu.fxml"));
                App.getPrimaryStage().getScene().setRoot(root);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if (bnt.getId().equals("SubmitB")) {
            //Show the confirmation page
        }

        if (bnt.getId().equals("HelpB")) {
            //Show the help page
        }

    }


}
