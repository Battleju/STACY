package de.skrrt.stacy.gui.loadingScreen;

import de.skrrt.stacy.gui.mainScreen.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;

public class Loading extends SwingWorker {

    private LoadingController loadingController;
    private Stage stage;
    private Main main;
    private AnchorPane origin;

    public Loading(Main main, AnchorPane origin) {
        this.main = main;
        this.origin = origin;
        FXMLLoader loader = new FXMLLoader(Loading.class.getResource("LoadingWindow.fxml"));
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        loadingController = loader.getController();
        stage.setScene(new Scene(pane, 600, 100));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }

    private void closeWindow(){
        Platform.runLater(() -> {
            stage.close();
            main.getPrimaryStage().show();
        });
    }

    @Override
    protected Object doInBackground() throws Exception {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        main.getPrimaryStage().getScene().setRoot(origin);
        closeWindow();
        return null;
    }
}
