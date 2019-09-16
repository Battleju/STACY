package de.skrrt.stacy.BitmexAPI;

import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.enums.OrderSide;
import de.skrrt.stacy.enums.OrderType;
import de.skrrt.stacy.utils.SystemCommandExecutor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BitmexAPI {

    private String xbtPrice;
    private String xrpPrice;
    private String ethPrice;

    private float takeProfit;

    public BitmexAPI() {
        try {
            System.out.println("starting");
            Runtime.getRuntime().exec("cmd /c start \"\" src\\de\\skrrt\\stacy\\BitmexAPI\\launch.bat");
        } catch (IOException e) {
            e.printStackTrace();
        }
        takeProfit = 0;
    }

    //---------------------------------------------set Orders-----------------------------------------------------------

    public void setOrder(OrderType orderType, OrderSide orderSide, Asset asset, int lev, float stoploss, float qty, float limitPrice, float triggerPrice){
        try {
            File file = new File("src/de/skrrt/stacy/BitmexAPI/order.txt");
            FileWriter writer = new FileWriter(file, false);
            writer.write(orderType.toString());
            writer.write(System.getProperty("line.separator"));
            writer.write(orderSide.toString());
            writer.write(System.getProperty("line.separator"));
            writer.write(asset.toString());
            writer.write(System.getProperty("line.separator"));
            writer.write(lev + "");
            writer.write(System.getProperty("line.separator"));
            writer.write("" + Math.round(qty));
            writer.write(System.getProperty("line.separator"));
            writer.write("" + Math.round(stoploss));
            writer.write(System.getProperty("line.separator"));
            writer.write("" + Math.round(limitPrice));
            writer.write(System.getProperty("line.separator"));
            writer.write("" + Math.round(triggerPrice));
            writer.write(System.getProperty("line.separator"));
            writer.write("" + Math.round(takeProfit));
            writer.flush();
            writer.close();
            takeProfit = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        takeProfit = 0;
    }

    public void setOrder(OrderType orderType, OrderSide orderSide, Asset asset, int lev, float stoploss, float qty, float limitPrice){
        setOrder(orderType, orderSide, asset, lev, stoploss, qty, limitPrice, 0);
    }

    public void setOrder(OrderType orderType, OrderSide orderSide, Asset asset, int lev, float stoploss, float qty){
        setOrder(orderType, orderSide, asset, lev, stoploss, qty, 0);
    }

    public void cancelOrder(String orderID){
        try {
            File file = new File("STACY/src/de/skrrt/stacy/BitmexAPI/cancelOrder.txt");
            FileWriter writer = new FileWriter(file, false);
            writer.write(orderID);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closePosition(Asset asset){
        try {
            File file = new File("src/de/skrrt/stacy/BitmexAPI/closePosition.txt");
            FileWriter writer = new FileWriter(file, false);
            switch (asset){
                case BTC:
                    writer.write("XBTUSD");
                    break;
                case ETH:
                    writer.write("ETHUSD");
                    break;
                case XRP:
                    writer.write("XRPU19");
                    break;
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------show Prices--------------------------------------------------------
    public String getXbtPrice() {
        BufferedReader in = null;
        String zeile = "-1";
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/xbt.txt"));
            zeile = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return zeile;
    }

    public String getXrpPrice() {
        BufferedReader in = null;
        String zeile = "APIError";
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/xrp.txt"));
            zeile = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }

        return zeile;
    }

    public String getEthPrice() {
        BufferedReader in = null;
        String zeile = "APIError";
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/eth.txt"));
            zeile = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return zeile;
    }

    public float[] getChart(){
        BufferedReader in = null;
        ArrayList<String> sChart = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/chart.txt"));
            String zeile = "api error";
            do
            {
                zeile = in.readLine();
                sChart.add(zeile);
            }
            while (zeile != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        for (int i = 0; i < sChart.size(); i++){
            if(sChart.get(i) == null){
                sChart.remove(i);
            }
        }
        float[] chart = new float[sChart.size()];
        for (int i = 0; i < chart.length; i++){
                chart[i] = Float.parseFloat(sChart.get(i));
        }
        return chart;
    }

    //--------------------------------------------------position-management----------------------------------------------

    public ArrayList getPositions(){
        BufferedReader in = null;
        ArrayList<String> positions = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/position.txt"));
            String zeile = "api error";
            do
            {
                zeile = in.readLine();
                positions.add(zeile);
            }
            while (zeile != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return positions;
    }

    public ArrayList getOpenOrders(){
        BufferedReader in = null;
        ArrayList<String> openOrders = new ArrayList<>();
        try {
            in = new BufferedReader(new FileReader("src/de/skrrt/stacy/BitmexAPI/openOrders.txt"));
            String zeile = "api error";
            do
            {
                zeile = in.readLine();
                openOrders.add(zeile);
            }
            while (zeile != null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return openOrders;
    }

    //---------------------------------getter&setter--------------------------------------------------------------------
    public void setTakeProfit(float takeProfit) {
        this.takeProfit = takeProfit;
    }
}
