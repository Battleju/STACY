package de.skrrt.stacy.core;

import de.skrrt.stacy.BitmexAPI.BitmexAPI;
import de.skrrt.stacy.InternalException.*;
import de.skrrt.stacy.core.runPattern.InternalOrderSystem;
import de.skrrt.stacy.core.runPattern.Ticker;
import de.skrrt.stacy.core.runPattern.UpdatePositions;
import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.enums.OrderSide;
import de.skrrt.stacy.enums.OrderType;
import de.skrrt.stacy.gui.mainScreen.Main;

import java.util.concurrent.TimeUnit;

public class Bot {

    private BitmexAPI bitmexAPI;
    private Ticker ticker;
    private UpdatePositions updatePositions;
    private InternalOrderSystem internalOrderSystem;
    private Main main;

    private OrderType orderType;
    private Asset asset;
    private int lev;
    private int slRisk;
    private float stoploss;
    private float qty;
    private float limitPrice;
    private float triggerPrice;
    private float takeProfit;

    public Bot(Main main) {
        this.main = main;
        bitmexAPI = new BitmexAPI();
        ticker = new Ticker(this, bitmexAPI);
        updatePositions = new UpdatePositions(this, bitmexAPI);
        internalOrderSystem = new InternalOrderSystem(this, bitmexAPI);
        internalOrderSystem.start();
        ticker.start();
        updatePositions.start();
    }

    public void createOrder(OrderSide orderSide) throws QtyInternalException, StopLossInternalException, LimitInternalException, TriggerPriceInternalException {
        switch (orderType) {
            case MARKET:
                if(takeProfit != 0){
                    bitmexAPI.setTakeProfit(takeProfit);
                }
                if(main.getMainController().getCbSL().isSelected()){
                    if(stoploss != 0){
                        if(qty != 0){
                            //----------------------------Code 4 Order--------------------------------------------------

                            bitmexAPI.setOrder(OrderType.MARKET, orderSide, asset, lev, stoploss, qty);
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            createSLORTPOrder(OrderType.STOP, orderSide, asset, stoploss);

                            //------------------------------------------------------------------------------------------
                        }else {
                            throw new QtyInternalException();
                        }
                    }else{
                        throw new StopLossInternalException();
                    }
                }else{
                    if(qty != 0){
                        //--------------------------------Code 4 Order--------------------------------------------------

                        bitmexAPI.setOrder(OrderType.MARKET, orderSide, asset, lev, calculateSL(orderSide), qty);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        createSLORTPOrder(OrderType.STOP, orderSide, asset, calculateSL(orderSide));

                        //----------------------------------------------------------------------------------------------
                    }else{
                        throw new QtyInternalException();
                    }
                }
                break;
            case LIMIT:
                if(main.getMainController().getCbSL().isSelected()){
                    if(stoploss != 0){
                        if(qty != 0){
                            if(limitPrice != 0){
                                //------------------------Code 4 Order--------------------------------------------------

                                Order order = new Order(OrderType.LIMIT, orderSide, asset, stoploss, 0f, qty, limitPrice, 0, lev);
                                if(takeProfit != 0){
                                    order.setTakeProfit(takeProfit);
                                }
                                internalOrderSystem.addOrder(order);

                                //--------------------------------------------------------------------------------------
                            }else{
                                throw new LimitInternalException();
                            }
                        }else{
                            throw new QtyInternalException();
                        }
                    }else{
                        throw new StopLossInternalException();
                    }
                }else{
                    if(qty != 0){
                        if(limitPrice != 0){
                            //----------------------------Code 4 Order--------------------------------------------------

                            Order order = new Order(OrderType.LIMIT, orderSide, asset, calculateSL(orderSide), 0f, qty, limitPrice, 0, lev);
                            if(takeProfit != 0){
                                order.setTakeProfit(takeProfit);
                            }
                            internalOrderSystem.addOrder(order);

                            //------------------------------------------------------------------------------------------
                        }else{
                            throw new LimitInternalException();
                        }
                    }else{
                        throw new QtyInternalException();
                    }
                }
                break;
            case CONDITIONAL:
                if(main.getMainController().getCbSL().isSelected()){
                    if(stoploss != 0){
                        if(qty != 0){
                                if(triggerPrice != 0){
                                    //--------------------Code 4 Order--------------------------------------------------

                                    Order order = new Order(OrderType.CONDITIONAL, orderSide, asset, stoploss, 0f, qty, 0, triggerPrice, lev);
                                    if(takeProfit != 0){
                                        order.setTakeProfit(takeProfit);
                                    }
                                    internalOrderSystem.addOrder(order);

                                    //----------------------------------------------------------------------------------
                                }else{
                                    throw new TriggerPriceInternalException();
                                }
                        }else{
                            throw new QtyInternalException();
                        }
                    }else{
                        throw new StopLossInternalException();
                    }
                }else{
                    if(qty != 0){
                            if(triggerPrice != 0){
                                //------------------------Code 4 Order--------------------------------------------------

                                Order order = new Order(OrderType.CONDITIONAL, orderSide, asset, calculateSL(orderSide), 0f, qty, 0, triggerPrice, lev);
                                if(takeProfit != 0){
                                    order.setTakeProfit(takeProfit);
                                }
                                internalOrderSystem.addOrder(order);

                                //----------------------------------------------------------------------------------
                            }else{
                                throw new TriggerPriceInternalException();
                            }
                    }else{
                        throw new QtyInternalException();
                    }
                }
                break;
        }
    }

    public void createSLORTPOrder(OrderType orderType, OrderSide orderSide, Asset asset, float trigger){
        switch (asset){
            case BTC:
                if(orderType == OrderType.STOP){
                    if(updatePositions.getpXBT().getStopLoss() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpXBT().getOrderIDStopLoss());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }else {
                    if(updatePositions.getpXRP().getTakeProfit() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpXBT().getOrderIDTakeProfit());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }
                break;
            case ETH:
                if(orderType == OrderType.STOP){
                    if(updatePositions.getpETH().getStopLoss() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpETH().getOrderIDStopLoss());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }else {
                    if(updatePositions.getpETH().getTakeProfit() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpETH().getOrderIDTakeProfit());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }
                break;
            case XRP:
                if(orderType == OrderType.STOP){
                    if(updatePositions.getpXRP().getStopLoss() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpXRP().getOrderIDStopLoss());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }else {
                    if(updatePositions.getpXBT().getTakeProfit() == 0f){
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }else {
                        cancelOrder(updatePositions.getpXRP().getOrderIDTakeProfit());
                        bitmexAPI.setOrder(orderType, orderSide, asset, 1, trigger, 0, 0, 0);
                    }
                }
                break;
        }
    }

    public void cancelOrder(String orderID){
        bitmexAPI.cancelOrder(orderID);
    }

    //Getter & Setter --------------------------------------------------------------------------------------------------

    public Ticker getTicker() {
        return ticker;
    }

    public BitmexAPI getBitmexAPI() {
        return bitmexAPI;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Asset getAsset() {
        return asset;
    }

    public int getLev() {
        return lev;
    }

    public void setLev(int lev) {
        this.lev = lev;
    }

    public int getSlRisk() {
        return slRisk;
    }

    public void setSlRisk(int slRisk) {
        this.slRisk = slRisk;
    }

    public void setStoploss(float stoploss) {
        this.stoploss = stoploss;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public void setLimitPrice(float limitPrice) {
        this.limitPrice = limitPrice;
    }

    public void setTriggerPrice(float triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void setTakeProfit(float takeProfit) {
        this.takeProfit = takeProfit;
    }

    public UpdatePositions getUpdatePositions() {
        return updatePositions;
    }

    public InternalOrderSystem getInternalOrderSystem() {
        return internalOrderSystem;
    }

    public Main getMain() {
        return main;
    }

    //------------------------------------------------------------------------------------------------------------------

    public float calculateSL(OrderSide orderSide){
        float price = 0;
        switch (asset){
            case BTC:
                price = Float.parseFloat(bitmexAPI.getXbtPrice());
                break;
            case ETH:
                price = Float.parseFloat(bitmexAPI.getEthPrice());
                break;
            case XRP:
                price = Float.parseFloat(bitmexAPI.getXrpPrice());
                break;
        }
        if(orderSide == OrderSide.LONG){
            price = price - price * (slRisk * 0.01f);
        }else {
            price = price + price * (slRisk * 0.01f);
        }
        return price;
    }
}
