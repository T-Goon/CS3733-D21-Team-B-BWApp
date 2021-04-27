package edu.wpi.cs3733.D21.teamB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import edu.wpi.cs3733.D21.teamB.database.DatabaseHandler;
import edu.wpi.cs3733.D21.teamB.entities.User;
import edu.wpi.cs3733.D21.teamB.util.CSVHandler;
import edu.wpi.cs3733.D21.teamB.util.SceneSwitcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class App extends Application {

    private static Stage primaryStage;
    private DatabaseHandler db;
    private Thread dbThread;

    @Override
    public void init() {
        System.out.println("Starting Up");
        db = DatabaseHandler.getDatabaseHandler("main.db");
    }

    @Override
    public void start(Stage primaryStage) {
        App.primaryStage = primaryStage;

        // Open first view
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/login/loginOptions.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            //Press F11 to escape fullscreen. Allows users to use the ESC key to go back to the previous scene
            primaryStage.setFullScreenExitKeyCombination(new KeyCombination() {
                @Override
                public boolean match(KeyEvent event) {
                    return event.getCode().equals(KeyCode.F11);
                }
            });

            primaryStage.setFullScreen(true);

            // If the database is uninitialized, fill it with the csv files
            try {
                db.resetDatabase(new ArrayList<>(Collections.singleton("Users")));
                db.executeSchema();
                if (!db.isInitialized()) {
                    SceneSwitcher.switchFromTemp(getClass(), "/edu/wpi/cs3733/D21/teamB/views/login/databaseInit.fxml");
                    primaryStage.show();

                    dbThread = new Thread(() -> {
                        try {
                            db.loadNodesEdges(CSVHandler.loadCSVNodes("/edu/wpi/cs3733/D21/teamB/csvFiles/bwBnodes.csv"), CSVHandler.loadCSVEdges("/edu/wpi/cs3733/D21/teamB/csvFiles/bwBedges.csv"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> SceneSwitcher.switchFromTemp(getClass(), "/edu/wpi/cs3733/D21/teamB/views/login/loginOptions.fxml"));
                    });
                    dbThread.start();
                } else primaryStage.show();
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            try {

                //Required users
                db.addUser(new User("admin", "Professor", "X", User.AuthenticationLevel.ADMIN, null), "admin");
                db.addUser(new User("staff", "Mike", "Bedard", User.AuthenticationLevel.STAFF, null), "staff");
                db.addUser(new User("guest", "T", "Goon", User.AuthenticationLevel.PATIENT, null), "guest");

                //Additional test users
                db.addUser(new User("d", "Dan", "Druff", User.AuthenticationLevel.ADMIN, null), "d");
                db.addUser(new User("j", "Joe", "Mama", User.AuthenticationLevel.STAFF, null), "j");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void stop() {
        if (dbThread != null)
            dbThread.stop();
        DatabaseHandler.getDatabaseHandler("main.db").shutdown();
        System.out.println("Shutting Down");
    }
}
