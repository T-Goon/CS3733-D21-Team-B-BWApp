package edu.wpi.teamB.views.loginPages;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamB.util.SceneSwitcher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminLoginController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnEmergency;

    @FXML
    private JFXButton btnExit;

    @FXML
    public void handleButtonAction(ActionEvent actionEvent) {
        JFXButton btn = (JFXButton) actionEvent.getSource();

        switch (btn.getId()) {
            case "btnLogin":
                SceneSwitcher.switchToTemp(getClass(), "/edu/wpi/teamB/views/menus/adminDirectoryMenu.fxml");
                break;
            case "btnBack":
                SceneSwitcher.goBack(getClass(), 1);
                break;
            case "btnExit":
                Platform.exit();
                break;
            case "btnEmergency":
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/loginPages/adminLogin.fxml", "/edu/wpi/teamB/views/requestForms/emergencyForm.fxml");
                break;
        }
    }

    @FXML
    private void validateButton() {
        btnLogin.setDisable(username.getText().isEmpty() || password.getText().isEmpty());
    }
}
