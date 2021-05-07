package edu.wpi.cs3733.D21.teamB.views.misc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.D21.teamB.util.PageCache;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatBoxController implements Initializable {

    @FXML
    public VBox base;

    @FXML
    public ScrollPane scrollPane;

    @FXML
    public JFXTextField input;

    @FXML
    public VBox messageHolder;

    @FXML
    public JFXButton btnClose;

    List<HBox> messages = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Add all the messages in the cache
        for (Message m : PageCache.getMessages())
            addMessage(m);

        // Scroll pane goes down to the bottom when a new message is sent
        scrollPane.vvalueProperty().bind(messageHolder.heightProperty());

        // When closed, wipe the cache and remove itself
        btnClose.setOnAction(e -> {
            ((AnchorPane) base.getParent()).getChildren().remove(base);
            PageCache.getMessages().clear();
        });
    }

    @FXML
    public void handleSendMessage(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            Message message = new Message(input.getText(), true);
            addMessage(message);
            input.clear();
            PageCache.getMessages().add(message);
        }
    }

    /**
     * Given a message and whether it's from the user, add it to the chatbot
     *
     * @param message the message to show
     */
    public void addMessage(Message message) {
        if (message == null || message.message.isEmpty()) return;

        // Adds HBox with text
        HBox messageBox = new HBox();
        Label text = new Label(message.message);
        text.setFont(new Font("MS Reference Sans Serif", 13));
        text.setStyle("-fx-text-fill: white");
        text.setWrapText(true);
        text.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, new CornerRadii(7), Insets.EMPTY)));
        text.setPadding(new Insets(5, 10, 5, 10));
        messageBox.getChildren().add(text);

        // Determines alignment if sent from user or not
        if (message.fromUser) {
            messageBox.setPadding(new Insets(0, 0, 0, 75));
            messageBox.setAlignment(Pos.CENTER_RIGHT);
        } else {
            messageBox.setPadding(new Insets(0, 75, 0, 0));
            messageBox.setAlignment(Pos.CENTER_LEFT);
        }

        // Hey, it exists!
        messageHolder.getChildren().add(messageBox);
        messages.add(messageBox);
    }

    @Getter
    @AllArgsConstructor
    public static class Message {
        private final String message;
        private final boolean fromUser;

        @Override
        public String toString() {
            return "[" + message + (fromUser ? " from User" : " from Chatbot") + "]";
        }
    }
}