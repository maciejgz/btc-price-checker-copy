package pl.mg.btc.store.model;

public class NotEnoughProductsInStorageException extends Exception {
    public NotEnoughProductsInStorageException() {
        super();
    }

    public NotEnoughProductsInStorageException(String message) {
        super(message);
    }
}
