package restaurantvoting.error;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String message) {
        super(message);
    }
}