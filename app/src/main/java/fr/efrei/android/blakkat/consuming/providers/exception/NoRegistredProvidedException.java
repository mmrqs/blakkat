package fr.efrei.android.blakkat.consuming.providers.exception;

public class NoRegistredProvidedException extends Exception {
    public NoRegistredProvidedException() {
        super();
    }

    public NoRegistredProvidedException(String message) {
        super(message);
    }

    public NoRegistredProvidedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRegistredProvidedException(Throwable cause) {
        super(cause);
    }

    protected NoRegistredProvidedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
