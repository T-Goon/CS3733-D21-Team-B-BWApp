package edu.wpi.teamB.entities.map.node;

import edu.wpi.teamB.entities.map.data.NodeMenuPopupData;
import edu.wpi.teamB.util.Popup.Poppable;
import edu.wpi.teamB.util.Popup.Popup;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.Objects;

public class NodeMenuPopup extends Popup<VBox, NodeMenuPopupData> implements Poppable {

    private final GesturePane gpane;

    public NodeMenuPopup(Pane parent, NodeMenuPopupData data, GesturePane gpane) {
        super(parent, data);
        this.gpane = gpane;
    }

    public void show() {
        VBox nmPopup = null;
        try {
            nmPopup = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("edu/wpi/teamB/views/map/nodePopup/nodePopupWindow.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.show(nmPopup);
        gpane.setGestureEnabled(false);
    }

    public void setStartEdge() {
        data.getMc().setStartNode(data.getCircle());
        data.getMc().setNewEdgeStart(data.getNodeID());
        data.getCircle().setStroke(Color.RED);
        data.getMd().removeAllPopups();
    }

    @Override
    public void hide() {
        super.hide();
    }
}
