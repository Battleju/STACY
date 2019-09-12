package de.skrrt.stacy.InternalException;

public class LimitInternalException extends Exception implements InternalException {
    public LimitInternalException() {
        super("No Limit set");
    }
}
