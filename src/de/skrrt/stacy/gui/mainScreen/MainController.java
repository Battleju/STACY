package de.skrrt.stacy.gui.mainScreen;

import de.skrrt.stacy.InternalException.*;
import de.skrrt.stacy.core.Bot;
import de.skrrt.stacy.core.Position;
import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.enums.OrderSide;
import de.skrrt.stacy.enums.OrderType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

import java.util.function.UnaryOperator;

public class MainController {

    private Main main;

    @FXML
    private Label lActualPrice;
    @FXML
    private Label lAsset;
    @FXML
    private Label lLWV, lLWP, lLLV, lLLP, lLRR, lSWV, lSWP, lSLV, lSLP, lSRR;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button bBTC;
    @FXML
    private Button bEth;
    @FXML
    private Button bRip;
    @FXML
    private Button bLev1, bLev2, bLev3, bLev4, bLev5;
    @FXML
    private Button bRisk1, bRisk2, bRrisk3;
    @FXML
    private Button bMarket, bLimit, bConditional;
    @FXML
    private CheckBox cbSL;
    @FXML
    private TextField tfStopLoss, tfQty, tfLimit, tfCon, tfTakeProfit, tfLTarget, tfSTarget;
    @FXML
    private TableView tablePositions;

    public void setMain(Main main) {
        this.main = main;
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();

            if (text.matches("[0-9.]*")) {
                return change;
            }

            return null;
        };
        tfQty.setTextFormatter(new TextFormatter<>(filter));
        tfStopLoss.setTextFormatter(new TextFormatter<>(filter));
        tfLimit.setTextFormatter(new TextFormatter<>(filter));
        tfCon.setTextFormatter(new TextFormatter<>(filter));
        tfTakeProfit.setTextFormatter(new TextFormatter<>(filter));
        tfSTarget.setTextFormatter(new TextFormatter<>(filter));
        tfLTarget.setTextFormatter(new TextFormatter<>(filter));
    }

    public void setlActualPrice(String s){
        float oldActualPrice = 0;
        if(!lActualPrice.equals("error") && !lActualPrice.equals(000000)){
            try{
                oldActualPrice = Float.parseFloat(lActualPrice.getText());
            }catch (NumberFormatException e){

            }
        }
        if(s.equals("error")){
            lActualPrice.setText("" + s);
        }else {

            float f = Float.parseFloat(s);

            //f = Math.round(f);
            if(oldActualPrice != 0){
                if(oldActualPrice > f){
                    lActualPrice.setTextFill(Color.RED);
                }else {
                    lActualPrice.setTextFill(Color.GREEN);
                }
            }
            lActualPrice.setText("" + s);
        }
    }

    public void setlAsset(String s){
        lAsset.setText(s);
    }

    public void setBTC(){
        main.getBot().setAsset(Asset.BTC);
        bBTC.setStyle("-fx-background-color: #00a1ff");
        bEth.setStyle("-fx-background-color: transparent");
        bRip.setStyle("-fx-background-color: transparent");
        bEth.setEffect(new Glow(0));
        bBTC.setEffect(new Glow(1));
        bRip.setEffect(new Glow(0));
        bBTC.setStyle("-fx-text-fill: white");
        bEth.setStyle("-fx-text-fill: #00a1ff");
        bRip.setStyle("-fx-text-fill: #00a1ff");
        lAsset.setText("BITCOIN");
    }

    public void setEth(){
        main.getBot().setAsset(Asset.ETH);
        bEth.setStyle("-fx-background-color: #00a1ff");
        bBTC.setStyle("-fx-background-color: transparent");
        bRip.setStyle("-fx-background-color: transparent");
        bEth.setEffect(new Glow(1));
        bBTC.setEffect(new Glow(0));
        bRip.setEffect(new Glow(0));
        bBTC.setStyle("-fx-text-fill: #00a1ff");
        bEth.setStyle("-fx-text-fill: white");
        bRip.setStyle("-fx-text-fill: #00a1ff");
        lAsset.setText("ETHERIUM");
    }

    public void setRip(){
        main.getBot().setAsset(Asset.XRP);
        bRip.setStyle("-fx-background-color: #00a1ff");
        bEth.setStyle("-fx-background-color: transparent");
        bBTC.setStyle("-fx-background-color: transparent");
        bEth.setEffect(new Glow(0));
        bBTC.setEffect(new Glow(0));
        bRip.setEffect(new Glow(1));
        bBTC.setStyle("-fx-text-fill: #00a1ff");
        bEth.setStyle("-fx-text-fill: #00a1ff");
        bRip.setStyle("-fx-text-fill: white");
        lAsset.setText("RIPPLE");
    }

    public void setLev1(){
        main.getBot().setLev(1);
        bLev1.setStyle("-fx-background-color: #00a1ff");
        bLev2.setStyle("-fx-background-color: transparent");
        bLev3.setStyle("-fx-background-color: transparent");
        bLev4.setStyle("-fx-background-color: transparent");
        bLev5.setStyle("-fx-background-color: transparent");
    }
    public void setLev2(){
        main.getBot().setLev(5);
        bLev1.setStyle("-fx-background-color: transparent");
        bLev2.setStyle("-fx-background-color: #00a1ff");
        bLev3.setStyle("-fx-background-color: transparent");
        bLev4.setStyle("-fx-background-color: transparent");
        bLev5.setStyle("-fx-background-color: transparent");
    }
    public void setLev3(){
        main.getBot().setLev(10);
        bLev1.setStyle("-fx-background-color: transparent");
        bLev2.setStyle("-fx-background-color: transparent");
        bLev3.setStyle("-fx-background-color: #00a1ff");
        bLev4.setStyle("-fx-background-color: transparent");
        bLev5.setStyle("-fx-background-color: transparent");
    }
    public void setLev4(){
        main.getBot().setLev(25);
        bLev1.setStyle("-fx-background-color: transparent");
        bLev2.setStyle("-fx-background-color: transparent");
        bLev3.setStyle("-fx-background-color: transparent");
        bLev4.setStyle("-fx-background-color: #00a1ff");
        bLev5.setStyle("-fx-background-color: transparent");
    }
    public void setLev5(){
        main.getBot().setLev(50);
        bLev1.setStyle("-fx-background-color: transparent");
        bLev2.setStyle("-fx-background-color: transparent");
        bLev3.setStyle("-fx-background-color: transparent");
        bLev4.setStyle("-fx-background-color: transparent");
        bLev5.setStyle("-fx-background-color: #00a1ff");
    }

    public void setRisk1(){
        main.getBot().setSlRisk(1);
        bRisk1.setStyle("-fx-background-color: #00a1ff");
        bRisk2.setStyle("-fx-background-color: transparent");
        bRrisk3.setStyle("-fx-background-color: transparent");
    }
    public void setRisk2(){
        main.getBot().setSlRisk(2);
        bRisk1.setStyle("-fx-background-color: transparent");
        bRisk2.setStyle("-fx-background-color: #00a1ff");
        bRrisk3.setStyle("-fx-background-color: transparent");
    }
    public void setRisk3(){
        main.getBot().setSlRisk(3);
        bRisk1.setStyle("-fx-background-color: transparent");
        bRisk2.setStyle("-fx-background-color: transparent");
        bRrisk3.setStyle("-fx-background-color: #00a1ff");
    }

    public void setMarket(){
        main.getBot().setOrderType(OrderType.MARKET);
        tfLimit.setDisable(true);
        tfCon.setDisable(true);
        bMarket.setEffect(new Glow(1));
        bLimit.setEffect(null);
        bConditional.setEffect(null);
        bMarket.setStyle("-fx-underline: true");
        bLimit.setStyle("-fx-underline: false");
        bConditional.setStyle("-fx-underline: false");
        bMarket.setStyle("-fx-underline: true");
        bLimit.setStyle("-fx-underline: false");
        bConditional.setStyle("-fx-underline: false");
    }

    public void setLimit(){
        main.getBot().setOrderType(OrderType.LIMIT);
        tfLimit.setDisable(false);
        tfCon.setDisable(true);
        bMarket.setEffect(null);
        bLimit.setEffect(new Glow(1));
        bConditional.setEffect(null);
        bMarket.setStyle("-fx-underline: false");
        bLimit.setStyle("-fx-underline: true");
        bConditional.setStyle("-fx-underline: false");
    }

    public void setbConditional(){
        main.getBot().setOrderType(OrderType.CONDITIONAL);
        tfLimit.setDisable(true);
        tfCon.setDisable(false);
        bMarket.setEffect(null);
        bLimit.setEffect(null);
        bConditional.setEffect(new Glow(1));
        bMarket.setStyle("-fx-underline: false");
        bLimit.setStyle("-fx-underline: false");
        bConditional.setStyle("-fx-underline: true");
    }

    public void checkManualSL(){
        if(cbSL.isSelected()){
            bRisk1.setDisable(true);
            bRisk2.setDisable(true);
            bRrisk3.setDisable(true);
            tfStopLoss.setDisable(false);
        }else {
            bRisk1.setDisable(false);
            bRisk2.setDisable(false);
            bRrisk3.setDisable(false);
            tfStopLoss.setDisable(true);
            tfStopLoss.setText("0");
        }
    }

    public void updateValues(){
        if(!tfLimit.getText().isEmpty()){
            main.getBot().setLimitPrice(Float.parseFloat(tfLimit.getText()));
        }
        if(!tfCon.getText().isEmpty()){
            main.getBot().setTriggerPrice(Float.parseFloat(tfCon.getText()));
        }
        if(!tfQty.getText().isEmpty()){
            main.getBot().setQty(Float.parseFloat(tfQty.getText()));
        }
        if(!tfStopLoss.getText().isEmpty()){
            main.getBot().setStoploss(Float.parseFloat(tfStopLoss.getText()));
        }
        if(!tfTakeProfit.getText().isEmpty()){
            main.getBot().setTakeProfit(Float.parseFloat(tfTakeProfit.getText()));
        }
        updateRiskManagement();
    }

    public void longOrder(){
        resetTFStyle();
        try {
            main.getBot().createOrder(OrderSide.LONG);
            resetTFText();
        } catch (QtyInternalException e) {
            tfQty.setStyle("-fx-background-color: red");
            //e.printStackTrace();
        } catch (StopLossInternalException e) {
            //e.printStackTrace();
            tfStopLoss.setStyle("-fx-background-color: red");
        } catch (LimitInternalException e) {
            //e.printStackTrace();
            tfLimit.setStyle("-fx-background-color: red");
        } catch (TriggerPriceInternalException e) {
            //e.printStackTrace();
            tfCon.setStyle("-fx-background-color: red");
        }
    }

    public void shortOrder(){
        resetTFStyle();
        try {
            main.getBot().createOrder(OrderSide.SHORT);
            resetTFText();
        } catch (QtyInternalException e) {
            //e.printStackTrace();
            tfQty.setStyle("-fx-background-color: red");
        } catch (StopLossInternalException e) {
            //e.printStackTrace();
            tfStopLoss.setStyle("-fx-background-color: red");
        } catch (LimitInternalException e) {
            //e.printStackTrace();
            tfLimit.setStyle("-fx-background-color: red");
        } catch (TriggerPriceInternalException e) {
            //e.printStackTrace();
            tfCon.setStyle("-fx-background-color: red");
        }
    }

    public void changeTrigger(){
        main.editPosition((Position) tablePositions.getSelectionModel().getSelectedItem());
    }

    private void resetTFStyle(){
        tfQty.setStyle("-fx-background-color: transparent");
        tfStopLoss.setStyle("-fx-background-color: transparent");
        tfLimit.setStyle("-fx-background-color: transparent");
        tfCon.setStyle("-fx-background-color: transparent");
    }

    private void resetTFText(){
        tfQty.setText("0");
        tfStopLoss.setText("0");
        tfLimit.setText("0");
        tfCon.setText("0");
        tfTakeProfit.setText("0");
    }

    private void updateRiskManagement(){
        if(!tfLTarget.getText().equals(0) || tfLTarget.getText().isEmpty()){
            float winPercent = 0;
            float lossPercent = 0;
            if(tfStopLoss.getText().trim().equals("0")){
                 winPercent = (1 - (Float.parseFloat(tfLTarget.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice()))) * -1;
                 lossPercent = 1 - (main.getBot().calculateSL(OrderSide.LONG) / Float.parseFloat(main.getBot().getTicker().getActualPrice()));
            }else {
                 winPercent = (1 - (Float.parseFloat(tfLTarget.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice()))) * -1;
                 lossPercent = 1 - (Float.parseFloat(tfStopLoss.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice()));
            }
            lLWP.setText((Math.round(winPercent * 100 * Math.pow(10, 2))/ Math.pow(10, 2)) + "%");
            lLLP.setText(Math.round(lossPercent * 100 * Math.pow(10, 2))/ Math.pow(10, 2) + "%");
            float riskReward = winPercent / lossPercent;
            lLRR.setText("" + (Math.round(riskReward * Math.pow(10, 2))/ Math.pow(10, 2)));
            if(!tfQty.getText().equals("")){
                lLWV.setText(Math.round(Float.parseFloat(tfQty.getText()) * winPercent) + "¥");
                lLLV.setText(Math.round(Float.parseFloat(tfQty.getText()) * lossPercent * -1) + "¥");
            }
        }
        if(!tfSTarget.getText().equals(0) || tfSTarget.getText().isEmpty()){
            float winPercent = 0;
            float lossPercent = 0;
            if(tfStopLoss.getText().trim().equals("0")){
                winPercent = (1 - (Float.parseFloat(tfSTarget.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice())));
                lossPercent = (1 - (main.getBot().calculateSL(OrderSide.SHORT) / Float.parseFloat(main.getBot().getTicker().getActualPrice()))) * -1;
            }else {
                winPercent = (1 - (Float.parseFloat(tfSTarget.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice())));
                lossPercent = (1 - (Float.parseFloat(tfStopLoss.getText()) / Float.parseFloat(main.getBot().getTicker().getActualPrice()))) * -1;
            }
            lSWP.setText((Math.round(winPercent * 100 * Math.pow(10, 2))/ Math.pow(10, 2)) + "%");
            lSLP.setText(Math.round(lossPercent * 100 * Math.pow(10, 2))/ Math.pow(10, 2) + "%");
            float riskReward = winPercent / lossPercent;
            lSRR.setText("" + (Math.round(riskReward * Math.pow(10, 2))/ Math.pow(10, 2)));
            if(!tfQty.getText().equals("")){
                lSWV.setText(Math.round(Float.parseFloat(tfQty.getText()) * winPercent) + "¥");
                lSLV.setText(Math.round(Float.parseFloat(tfQty.getText()) * lossPercent * -1) + "¥");
            }
        }
    }

    public void showStacyAnalytics(){
        main.getStacyAnalyics().showStage();
    }

    public TableView getTablePositions() {
        return tablePositions;
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public CheckBox getCbSL() {
        return cbSL;
    }
}
