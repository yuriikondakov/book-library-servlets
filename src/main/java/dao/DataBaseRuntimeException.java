package dao;

public class DataBaseRuntimeException extends RuntimeException {

    public DataBaseRuntimeException(String message) {
        super(message);
    }

    DataBaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseRuntimeException(Throwable cause) {
        super(cause);
    }
}
