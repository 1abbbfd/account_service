package account_service.exception;

public class UnknownAccountException extends Exception {
    public UnknownAccountException(String message) {
        super(message);
    }
}
