package de.skrrt.stacy.gui.changeValue;

import de.skrrt.stacy.core.Position;
import de.skrrt.stacy.enums.OrderSide;
import de.skrrt.stacy.enums.OrderType;
import de.skrrt.stacy.gui.mainScreen.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class EditPositionController {

    private Position position;
    private Main main;
    private Stage stage;
    @FXML
    private ToggleButton tbStopLoss, tbTakeProfit;
    @FXML
    private TextField tfStopLoss, tfTakeProfit;

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setMain(Main main) {
        this.main = main;
        tbStopLoss.setSelected(true);
        tfTakeProfit.setDisable(true);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void toggleSL(){
        tbStopLoss.setSelected(true);
        tbTakeProfit.setSelected(false);
        tfTakeProfit.setDisable(true);
        tfStopLoss.setDisable(false);
    }

    public void toggleTP(){
        tbStopLoss.setSelected(false);
        tbTakeProfit.setSelected(true);
        tfTakeProfit.setDisable(false);
        tfStopLoss.setDisable(true);
    }

    public void setChanges(){
        if(tbStopLoss.isSelected()){
            if(!tfStopLoss.getText().equals("")){
                main.getBot().cancelOrder(position.getOrderIDStopLoss());
                OrderSide orderSide;
                if(position.getQuantity() > 0){
                    orderSide = OrderSide.LONG;
                }else {
                    orderSide = OrderSide.SHORT;
                }
                main.getBot().createSLORTPOrder(OrderType.STOP, orderSide, position.getAsset(), Float.parseFloat(tfStopLoss.getText()));
                closeStage();
            }else {
                tfStopLoss.setStyle("-fx-background-color: red");
            }
        }else {
            if(!tbTakeProfit.getText().equals("")){
                if(position.getOrderIDTakeProfit() != null){
                    main.getBot().cancelOrder(position.getOrderIDTakeProfit());
                }
                OrderSide orderSide;
                if(position.getQuantity() > 0){
                    orderSide = OrderSide.LONG;
                }else {
                    orderSide = OrderSide.SHORT;
                }
                main.getBot().createSLORTPOrder(OrderType.TAKEPROFIT, orderSide, position.getAsset(), Float.parseFloat(tfTakeProfit.getText()));
                closeStage();
            }else {
                tfTakeProfit.setStyle("-fx-background-color: red");
            }
        }
    }

    public void closePosition(){
        if (position.getPrice() != -1) {
            main.getBot().getBitmexAPI().closePosition(position.getAsset());
            main.getBot().cancelOrder(position.getOrderIDStopLoss());
            if(position.getOrderIDTakeProfit() != null){
                main.getBot().cancelOrder(position.getOrderIDTakeProfit());
            }
        }else {
            main.getBot().getInternalOrderSystem().removeOrder(position.getAsset());
        }

        closeStage();
    }

    public void closeStage(){
        stage.close();
    }
}
