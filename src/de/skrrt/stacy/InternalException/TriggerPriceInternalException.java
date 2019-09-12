package de.skrrt.stacy.InternalException;

public class TriggerPriceInternalException extends Exception implements InternalException {
    public TriggerPriceInternalException() {
        super("No TriggerPrice set");
    }
}
