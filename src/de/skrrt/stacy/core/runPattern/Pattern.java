package de.skrrt.stacy.core.runPattern;

import de.skrrt.stacy.BitmexAPI.BitmexAPI;
import de.skrrt.stacy.core.Bot;

public abstract class Pattern extends Thread{

    protected boolean running;
    protected Bot bot;
    protected BitmexAPI bitmexAPI;

    public Pattern(Bot bot, BitmexAPI bitmexAPI) {
        running = true;
        this.bot = bot;
        this.bitmexAPI = bitmexAPI;
    }

    public void run() {
        while (running) {
            execute();
        }
    }

    public abstract void execute();
}
