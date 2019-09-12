package de.skrrt.stacy.gui.mainScreen;

import de.skrrt.stacy.core.Bot;
import de.skrrt.stacy.core.Position;
import de.skrrt.stacy.gui.changeValue.EditPositionController;
import de.skrrt.stacy.gui.changeValue.EditPositionLoader;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sun.management.counter.Units;

import java.util.concurrent.TimeUnit;

public class Main extends Application {

    private MainController mainController;
    private Stage primaryStage;
    private ObservableList<Position> positions;
    private Bot bot;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainWindow.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("S.T.A.C.Y. - You Can Actually Trade the System");
        mainController = loader.getController();
        mainController.setMain(this);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.setScene(new Scene(root, 760, 570));

        //Loading loadingScene = new Loading(this, (AnchorPane)root);
        //loadingScene.execute();
        primaryStage.show();
        initSTACY();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initSTACY(){
        bot = new Bot(this);

        //Table init
        positions = FXCollections.observableArrayList();
        mainController.getTablePositions().setItems(positions);
        TableColumn tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(0);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("asset"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(1);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(2);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(3);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("liquidation"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(4);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("leverage"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(6);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("stopLoss"));
        tableColumn = (TableColumn) mainController.getTablePositions().getColumns().get(7);
        tableColumn.setCellValueFactory(new PropertyValueFactory<>("takeProfit"));


        //Start Animation
        MediaPlayer mediaPlayer = new MediaPlayer( new Media(getClass().getResource("anihq.mov").toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setMute(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), ev -> {
            mainController.getMediaView().setVisible(false);
            try {
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        mediaPlayer.setOnError(new Runnable() {
            @Override
            public void run() {
                String message = mediaPlayer.errorProperty().get().getMessage();
                System.out.println(message);
            }
        });
        mainController.getMediaView().setMediaPlayer(mediaPlayer);
        timeline.play();

        //Update UI
        Timeline updater = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            mainController.setlActualPrice(bot.getTicker().getActualPrice());

            positions.clear();
            if(bot.getUpdatePositions().getpXBT().getQuantity() != 0){
                positions.add(bot.getUpdatePositions().getpXBT());
            }
            if(bot.getUpdatePositions().getpETH().getQuantity() != 0){
                positions.add(bot.getUpdatePositions().getpETH());
            }
            if(bot.getUpdatePositions().getpXRP().getQuantity() != 0){
                positions.add(bot.getUpdatePositions().getpXRP());
            }
            for(int i = 0; i < bot.getUpdatePositions().getInternalPositions().size(); i++){
                positions.add(bot.getUpdatePositions().getInternalPositions().get(i));
            }
        }));
        updater.setCycleCount(Animation.INDEFINITE);
        updater.play();

        //set buttons
        mainController.setBTC();
        mainController.setLev1();
        mainController.setRisk1();
        mainController.checkManualSL();
        mainController.setMarket();
    }

    public void editPosition(Position position){
        EditPositionLoader loader = new EditPositionLoader();
        AnchorPane pane = loader.getPane();
        EditPositionController editPositionController = loader.getEditPositionController();
        editPositionController.setMain(this);
        editPositionController.setPosition(position);
        Stage stage = new Stage();
        stage.setTitle("S.T.A.C.Y. - Edit Position");
        editPositionController.setStage(stage);
        stage.setScene(new Scene(pane, 350, 170));
        stage.show();
    }

    //----------Getter & Setter----------------------
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Bot getBot() {
        return bot;
    }

    public MainController getMainController() {
        return mainController;
    }
}
