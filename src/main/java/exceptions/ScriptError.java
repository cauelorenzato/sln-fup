package exceptions;

public class ScriptError extends Exception {
    String message = "Something went wrong while trying to add a new tester.";

    public ScriptError() {

    }

    public ScriptError(String message) {
        super(message);
    }

    public ScriptError(String message, Throwable throwable) {
        super(message, throwable);
    }
}
