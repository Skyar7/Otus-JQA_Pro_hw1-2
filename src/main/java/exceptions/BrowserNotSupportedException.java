package exceptions;

public class BrowserNotSupportedException extends RuntimeException {

    public BrowserNotSupportedException(String browserName) {
        super(String.format("Browser %s not supported", browserName));
    }
}