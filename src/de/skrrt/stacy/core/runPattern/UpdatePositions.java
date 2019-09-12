package de.skrrt.stacy.core.runPattern;

import de.skrrt.stacy.BitmexAPI.BitmexAPI;
import de.skrrt.stacy.core.Bot;
import de.skrrt.stacy.core.Position;
import de.skrrt.stacy.enums.Asset;
import javafx.application.Platform;

import java.util.ArrayList;

public class UpdatePositions extends Pattern {

    private Position pXBT, pXRP, pETH;
    private ArrayList<String> positions;
    private ArrayList<String> openOrdersString;
    private ArrayList<Position> internalPositions;

    public UpdatePositions(Bot bot, BitmexAPI bitmexAPI) {
        super(bot, bitmexAPI);
        pXBT = new Position();
        pETH = new Position();
        pXRP = new Position();
        internalPositions = new ArrayList<>();
    }

    @Override
    public void execute() {
        try {
            sleep(1000);
            positions = bitmexAPI.getPositions();
            pXBT.setQuantity(0f);
            pETH.setQuantity(0f);
            pXRP.setQuantity(0f);
            pXBT.setStopLoss(0f);
            pETH.setStopLoss(0f);
            pXRP.setStopLoss(0f);
            pXBT.setTakeProfit(0f);
            pETH.setTakeProfit(0f);
            pXRP.setTakeProfit(0f);
            internalPositions.clear();

            if(positions.size() <= 6 && positions.size() != 0){
                try {
                    switch (positions.get(0).trim()){
                        case "XBTUSD":
                            pXBT.setAsset(Asset.BTC);
                            pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                            pXBT.setQuantity(Float.parseFloat(positions.get(2)));
                            pXBT.setPrice(Float.parseFloat(positions.get(3)));
                            pXBT.setLiquidation(Float.parseFloat(positions.get(4)));
                            break;
                        case "ETHUSD":
                            pETH.setAsset(Asset.ETH);
                            pETH.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                            pETH.setQuantity(Float.parseFloat(positions.get(2)));
                            pETH.setPrice(Float.parseFloat(positions.get(3)));
                            pETH.setLiquidation(Float.parseFloat(positions.get(4)));
                            break;
                        case "XRPU19":
                            pXRP.setAsset(Asset.XRP);
                            pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                            pXRP.setQuantity(Float.parseFloat(positions.get(2)));
                            pXRP.setPrice(Float.parseFloat(positions.get(3)));
                            pXRP.setLiquidation(Float.parseFloat(positions.get(4)));
                            break;
                    }
                }catch (Exception e){
                    Platform.runLater(() -> {
                        bot.getMain().getMainController().setlAsset("ERROR DONT USE THE PROGRAMM");
                    });
                }
            }else if(positions.size() <= 11){
                switch (positions.get(0).trim()) {
                    case "XBTUSD":
                        pXBT.setAsset(Asset.BTC);
                        pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pXBT.setQuantity(Float.parseFloat(positions.get(2)));
                        pXBT.setPrice(Float.parseFloat(positions.get(3)));
                        pXBT.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                    case "ETHUSD":
                        pETH.setAsset(Asset.ETH);
                        pETH.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pETH.setQuantity(Float.parseFloat(positions.get(2)));
                        pETH.setPrice(Float.parseFloat(positions.get(3)));
                        pETH.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                    case "XRPU19":
                        pXRP.setAsset(Asset.XRP);
                        pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pXRP.setQuantity(Float.parseFloat(positions.get(2)));
                        pXRP.setPrice(Float.parseFloat(positions.get(3)));
                        pXRP.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                }
                switch (positions.get(5).trim()) {
                    case "XBTUSD":
                        pXBT.setAsset(Asset.BTC);
                        pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pXBT.setQuantity(Float.parseFloat(positions.get(7)));
                        pXBT.setPrice(Float.parseFloat(positions.get(8)));
                        pXBT.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                    case "ETHUSD":
                        pETH.setAsset(Asset.ETH);
                        pETH.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pETH.setQuantity(Float.parseFloat(positions.get(7)));
                        pETH.setPrice(Float.parseFloat(positions.get(8)));
                        pETH.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                    case "XRPU19":
                        pXRP.setAsset(Asset.XRP);
                        pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pXRP.setQuantity(Float.parseFloat(positions.get(7)));
                        pXRP.setPrice(Float.parseFloat(positions.get(8)));
                        pXRP.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                }
            }else if(positions.size() <= 16){
                switch (positions.get(0).trim()) {
                    case "XBTUSD":
                        pXBT.setAsset(Asset.BTC);
                        pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pXBT.setQuantity(Float.parseFloat(positions.get(2)));
                        pXBT.setPrice(Float.parseFloat(positions.get(3)));
                        pXBT.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                    case "ETHUSD":
                        pETH.setAsset(Asset.ETH);
                        pETH.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pETH.setQuantity(Float.parseFloat(positions.get(2)));
                        pETH.setPrice(Float.parseFloat(positions.get(3)));
                        pETH.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                    case "XRPU19":
                        pXRP.setAsset(Asset.XRP);
                        pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(1))));
                        pXRP.setQuantity(Float.parseFloat(positions.get(2)));
                        pXRP.setPrice(Float.parseFloat(positions.get(3)));
                        pXRP.setLiquidation(Float.parseFloat(positions.get(4)));
                        break;
                }
                switch (positions.get(5).trim()) {
                    case "XBTUSD":
                        pXBT.setAsset(Asset.BTC);
                        pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pXBT.setQuantity(Float.parseFloat(positions.get(7)));
                        pXBT.setPrice(Float.parseFloat(positions.get(8)));
                        pXBT.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                    case "ETHUSD":
                        pETH.setAsset(Asset.ETH);
                        pETH.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pETH.setQuantity(Float.parseFloat(positions.get(7)));
                        pETH.setPrice(Float.parseFloat(positions.get(8)));
                        pETH.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                    case "XRPU19":
                        pXRP.setAsset(Asset.XRP);
                        pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(6))));
                        pXRP.setQuantity(Float.parseFloat(positions.get(7)));
                        pXRP.setPrice(Float.parseFloat(positions.get(8)));
                        pXRP.setLiquidation(Float.parseFloat(positions.get(9)));
                        break;
                }
                switch (positions.get(10).trim()) {
                    case "XBTUSD":
                        pXBT.setAsset(Asset.BTC);
                        pXBT.setLeverage(Math.round(Float.parseFloat(positions.get(11))));
                        pXBT.setQuantity(Float.parseFloat(positions.get(12)));
                        pXBT.setPrice(Float.parseFloat(positions.get(13)));
                        pXBT.setLiquidation(Float.parseFloat(positions.get(14)));
                        break;
                    case "ETHUSD":
                        pETH.setAsset(Asset.ETH);
                        pETH.setLeverage(Math.round(Float.parseFloat(positions.get(11))));
                        pETH.setQuantity(Float.parseFloat(positions.get(12)));
                        pETH.setPrice(Float.parseFloat(positions.get(13)));
                        pETH.setLiquidation(Float.parseFloat(positions.get(14)));
                        break;
                    case "XRPU19":
                        pXRP.setAsset(Asset.XRP);
                        pXRP.setLeverage(Math.round(Float.parseFloat(positions.get(11))));
                        pXRP.setQuantity(Float.parseFloat(positions.get(12)));
                        pXRP.setPrice(Float.parseFloat(positions.get(13)));
                        pXRP.setLiquidation(Float.parseFloat(positions.get(14)));
                        break;
                }
            }

            openOrdersString = bitmexAPI.getOpenOrders();
            ArrayList<ArrayList<String>> openOrders = new ArrayList<>();
            ArrayList<String> openOrder = new ArrayList<>();
            if(openOrdersString.size() >= 2){
                for (int i = 0; i < openOrdersString.size() -1; i++){

                    if(openOrdersString.get(i).trim().equals("-")){
                        openOrders.add(openOrder);
                        openOrder = new ArrayList<>();
                    }else {
                        openOrder.add(openOrdersString.get(i));
                    }
                }
                for (int i = 0; i < openOrders.size(); i++){
                    switch (openOrders.get(i).get(0).trim()){
                        case "XBTUSD":
                            if(openOrders.get(i).get(1).trim().equals("Stop")){
                                pXBT.setStopLoss(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pXBT.setOrderIDStopLoss(openOrders.get(i).get(2).trim());
                            }else if(openOrders.get(i).get(1).trim().equals("MarketIfTouched")){
                                pXBT.setTakeProfit(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pXBT.setOrderIDTakeProfit(openOrders.get(i).get(2).trim());
                            }
                            break;
                        case "ETHUSD":
                            if(openOrders.get(i).get(1).trim().equals("Stop")){
                                pETH.setStopLoss(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pETH.setOrderIDStopLoss(openOrders.get(i).get(2).trim());
                            }else if(openOrders.get(i).get(1).trim().equals("MarketIfTouched")){
                                pETH.setTakeProfit(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pETH.setOrderIDTakeProfit(openOrders.get(i).get(2).trim());
                            }
                            break;
                        case "XRPU19":
                            if(openOrders.get(i).get(1).trim().equals("Stop")){
                                pXRP.setStopLoss(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pXRP.setOrderIDStopLoss(openOrders.get(i).get(2).trim());
                            }else if(openOrders.get(i).get(1).trim().equals("MarketIfTouched")){
                                pXRP.setTakeProfit(Float.parseFloat(openOrders.get(i).get(3).trim()));
                                pXRP.setOrderIDTakeProfit(openOrders.get(i).get(2).trim());
                            }
                            break;
                    }
                }
            }
            for (int i = 0; i < bot.getInternalOrderSystem().getOrders().size(); i++){
                internalPositions.add(new Position(bot.getInternalOrderSystem().getOrders().get(i).getAsset(), bot.getInternalOrderSystem().getOrders().get(i).getLeverage(), bot.getInternalOrderSystem().getOrders().get(i).getQuantity(), -1, -1));
            }

            if(pXBT.getQuantity() != 0){
                float f = 1 - (pXBT.getPrice() / Float.parseFloat(bot.getTicker().getPrice(Asset.BTC)));
                if(pXBT.getStopLoss() < pXBT.getPrice()){
                    pXBT.setApproxPnL(Math.round(pXBT.getQuantity() * f));
                }else {
                    pXBT.setApproxPnL(Math.round(-1 * pXBT.getQuantity() * f));
                }
            }

            if(pETH.getQuantity() != 0){
                float f = 1 - (pETH.getPrice() / Float.parseFloat(bot.getTicker().getPrice(Asset.ETH)));
                if(pETH.getStopLoss() < pETH.getPrice()){
                    pETH.setApproxPnL(Math.round(pETH.getQuantity() * f));
                }else {
                    pETH.setApproxPnL(Math.round(-1 * pETH.getQuantity() * f));
                }
            }

            if(pXRP.getQuantity() != 0){
                float f = 1 - (pXRP.getPrice() / Float.parseFloat(bot.getTicker().getPrice(Asset.XRP)));
                if(pXRP.getStopLoss() < pXRP.getPrice()){
                    pXRP.setApproxPnL(Math.round(pXRP.getQuantity() * f));
                }else {
                    pXRP.setApproxPnL(Math.round(-1 * pXRP.getQuantity() * f));
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Position getpXBT() {
        return pXBT;
    }

    public Position getpXRP() {
        return pXRP;
    }

    public Position getpETH() {
        return pETH;
    }

    public ArrayList<Position> getInternalPositions() {
        return internalPositions;
    }
}
