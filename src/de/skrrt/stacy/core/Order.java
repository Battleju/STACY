package de.skrrt.stacy.core;

import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.enums.OrderSide;
import de.skrrt.stacy.enums.OrderType;

public class Order {

    private OrderType orderType;
    private OrderSide orderSide;
    private Asset asset;
    private float stopLoss;
    private float takeProfit;
    private float quantity;
    private float limitPrice;
    private float triggerPrice;
    private int leverage;

    public Order(OrderType orderType, OrderSide orderSide, Asset asset, float stopLoss, float takeProfit, float quantity, float limitPrice, float triggerPrice, int leverage) {
        this.orderType = orderType;
        this.orderSide = orderSide;
        this.asset = asset;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.triggerPrice = triggerPrice;
        this.leverage = leverage;
    }

    public Order() {

    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderSide getOrderSide() {
        return orderSide;
    }

    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(float limitPrice) {
        this.limitPrice = limitPrice;
    }

    public float getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(float triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public int getLeverage() {
        return leverage;
    }

    public void setLeverage(int leverage) {
        this.leverage = leverage;
    }
}
