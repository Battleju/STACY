package de.skrrt.stacy.core.runPattern;

import de.skrrt.stacy.BitmexAPI.BitmexAPI;
import de.skrrt.stacy.core.Bot;
import de.skrrt.stacy.enums.Asset;
import de.skrrt.stacy.utils.HashMapBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Ticker extends Pattern {

    private String actualPriceBTC;
    private String actualPriceETH;
    private String actualPriceXRP;

    public Ticker(Bot bot, BitmexAPI bitmexAPI) {
        super(bot, bitmexAPI);
        actualPriceETH = "error";
        actualPriceBTC = "error";
        actualPriceXRP = "error";
    }

    @Override
    public void execute() {
        try {
            sleep(1000);
                actualPriceBTC = bitmexAPI.getXbtPrice();
                actualPriceETH = bitmexAPI.getEthPrice();
                actualPriceXRP = bitmexAPI.getXrpPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getActualPrice() {
        if(bot.getAsset().equals(Asset.BTC)){
            return actualPriceBTC;
        }else if(bot.getAsset().equals(Asset.ETH)){
            return actualPriceETH;
        }else if(bot.getAsset().equals(Asset.XRP)){
            return actualPriceXRP;
        }
        return null;
    }

    public String getPrice(Asset asset){
        switch (asset){
            case XRP:
                return actualPriceXRP;
            case ETH:
                return actualPriceETH;
            case BTC:
                return actualPriceBTC;
                default:
                    return null;
        }
    }

}
