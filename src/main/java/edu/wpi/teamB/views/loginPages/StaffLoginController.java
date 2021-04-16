package edu.wpi.teamB.views.loginPages;

import com.jfoenix.controls.JFXButton;
import edu.wpi.teamB.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StaffLoginController {
    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnEmergency;

    @FXML
    public void handleButtonAction(ActionEvent actionEvent) throws IOException {
        JFXButton btn = (JFXButton) actionEvent.getSource();

        switch (btn.getId()) {
            case "btnLogin":
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/menus/staffDirectoryMenu.fxml");
                break;
            case "btnBack":
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/loginPages/loginOptions.fxml");
                break;
            case "btnEmergency":
                // Not implemented
                break;
        }
    }
}
