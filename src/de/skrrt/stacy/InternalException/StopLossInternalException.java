package de.skrrt.stacy.InternalException;

public class StopLossInternalException extends Exception implements InternalException {
    public StopLossInternalException() {
        super("No StopLoss set");
    }
}
