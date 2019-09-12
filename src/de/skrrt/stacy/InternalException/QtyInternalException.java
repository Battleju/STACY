package de.skrrt.stacy.InternalException;

public class QtyInternalException extends Exception implements InternalException {
    public QtyInternalException() {
        super("No Quantity set");
    }
}
