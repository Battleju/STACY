package de.skrrt.stacy.core.runPattern;

import de.skrrt.stacy.BitmexAPI.BitmexAPI;
import de.skrrt.stacy.core.Bot;
import de.skrrt.stacy.core.Order;
import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.enums.OrderType;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class InternalOrderSystem extends Pattern {

    ArrayList<Order> orders;

    public InternalOrderSystem(Bot bot, BitmexAPI bitmexAPI) {
        super(bot, bitmexAPI);
        orders = new ArrayList<>();
    }

    @Override
    public void execute() {
        try {
            sleep(500);
            for (int i = 0; i < orders.size(); i++){
                switch (orders.get(i).getOrderType()){
                    case LIMIT:
                        switch (orders.get(i).getOrderSide()){
                            case LONG:
                                if(orders.get(i).getLimitPrice() > Float.parseFloat(bot.getTicker().getPrice(orders.get(i).getAsset()))){
                                    bitmexAPI.setOrder(OrderType.LIMIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getLeverage(), 0, orders.get(i).getQuantity(), orders.get(i).getLimitPrice());
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    bot.createSLORTPOrder(OrderType.STOP, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getStopLoss());
                                    if(orders.get(i).getTakeProfit() != 0){
                                        bot.createSLORTPOrder(OrderType.TAKEPROFIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getTakeProfit());
                                    }
                                    orders.remove(i);
                                }
                                break;
                            case SHORT:
                                if(orders.get(i).getLimitPrice() < Float.parseFloat(bot.getTicker().getPrice(orders.get(i).getAsset()))){
                                    bitmexAPI.setOrder(OrderType.LIMIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getLeverage(), 0, orders.get(i).getQuantity(), orders.get(i).getLimitPrice());
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    bot.createSLORTPOrder(OrderType.STOP, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getStopLoss());
                                    if(orders.get(i).getTakeProfit() != 0){
                                        bot.createSLORTPOrder(OrderType.TAKEPROFIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getTakeProfit());
                                    }
                                    orders.remove(i);
                                }
                                break;
                        }
                        break;
                    case CONDITIONAL:
                        switch (orders.get(i).getOrderSide()){
                            case LONG:
                                if(orders.get(i).getTriggerPrice() < Float.parseFloat(bot.getTicker().getPrice(orders.get(i).getAsset()))){
                                    bitmexAPI.setOrder(OrderType.CONDITIONAL, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getLeverage(), 0, orders.get(i).getQuantity(), orders.get(i).getLimitPrice(), orders.get(i).getTriggerPrice());
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    bot.createSLORTPOrder(OrderType.STOP, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getStopLoss());
                                    if(orders.get(i).getTakeProfit() != 0){
                                        bot.createSLORTPOrder(OrderType.TAKEPROFIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getTakeProfit());
                                    }
                                    orders.remove(i);
                                }
                                break;
                            case SHORT:
                                if(orders.get(i).getTriggerPrice() > Float.parseFloat(bot.getTicker().getPrice(orders.get(i).getAsset()))){
                                    bitmexAPI.setOrder(OrderType.CONDITIONAL, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getLeverage(), 0, orders.get(i).getQuantity(), orders.get(i).getLimitPrice(), orders.get(i).getTriggerPrice());
                                    try {
                                        TimeUnit.SECONDS.sleep(1);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    bot.createSLORTPOrder(OrderType.STOP, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getStopLoss());
                                    if(orders.get(i).getTakeProfit() != 0){
                                        bot.createSLORTPOrder(OrderType.TAKEPROFIT, orders.get(i).getOrderSide(), orders.get(i).getAsset(), orders.get(i).getTakeProfit());
                                    }
                                    orders.remove(i);
                                }
                                break;
                        }
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void removeOrder(Asset asset){
        for (int i = 0; i < orders.size(); i++){
            if(orders.get(i).getAsset() == asset){
                orders.remove(orders.get(i));
            }
        }
    }
}
