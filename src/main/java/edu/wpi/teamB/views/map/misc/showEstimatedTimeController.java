package edu.wpi.teamB.views.map.misc;

import edu.wpi.teamB.App;
import edu.wpi.teamB.entities.map.ETAPopup;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class showEstimatedTimeController implements Initializable {

    @FXML
    private Text time;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ETAPopup etaPopup = (ETAPopup) App.getPrimaryStage().getUserData();

        time.setText(etaPopup.getData().getTime());
    }
}