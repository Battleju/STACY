package de.skrrt.stacy.core;

import de.skrrt.stacy.enums.Asset;

public class Position {

    private Asset asset;
    private int leverage;
    private float quantity;
    private float price;
    private float liquidation;
    private float stopLoss;
    private float takeProfit;
    private String OrderIDStopLoss;
    private String OrderIDTakeProfit;
    private float approxPnL;

    public Position() {

    }

    public Position(Asset asset, int leverage, float quantity, float price, float liquidation){
        this.asset = asset;
        this.leverage = leverage;
        this.quantity = quantity;
        this.price = price;
        this.liquidation = liquidation;
        stopLoss = -1;
        takeProfit = -1;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(float liquidation) {
        this.liquidation = liquidation;
    }

    public float getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(float stopLoss) {
        this.stopLoss = stopLoss;
    }

    public float getTakeProfit() {
        return takeProfit;
    }

    public void setTakeProfit(float takeProfit) {
        this.takeProfit = takeProfit;
    }

    public String getOrderIDStopLoss() {
        return OrderIDStopLoss;
    }

    public void setOrderIDStopLoss(String orderIDStopLoss) {
        OrderIDStopLoss = orderIDStopLoss;
    }

    public String getOrderIDTakeProfit() {
        return OrderIDTakeProfit;
    }

    public void setOrderIDTakeProfit(String orderIDTakeProfit) {
        OrderIDTakeProfit = orderIDTakeProfit;
    }

    public float getApproxPnL() {
        return approxPnL;
    }

    public void setApproxPnL(float approxPnL) {
        this.approxPnL = approxPnL;
    }
}
