package de.skrrt.stacy.gui.changeValue;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditPositionLoader {

    private EditPositionController editPositionController;
    private AnchorPane pane;

    public EditPositionLoader() {
        FXMLLoader loader = new FXMLLoader(EditPositionController.class.getResource("EditPositionWindow.fxml"));
        try{
            pane = loader.load();
            editPositionController = loader.getController();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public EditPositionController getEditPositionController() {
        return editPositionController;
    }

    public AnchorPane getPane() {
        return pane;
    }
}
