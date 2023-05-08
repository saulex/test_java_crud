package com.example.demo.response;

public class CustomerCreationException extends RuntimeException {

    private final String message;

    public CustomerCreationException(String message) {
        super(message);
        this.message = message;
    }

    public CustomerCreationException(String message, String constraintViolationMessage,
                                     String dataIntegrityViolationMessage,
                                     String persistentObjectMessages) {
        super(message);
        String constraintMessage = constraintViolationMessage != null
                ? "Integrity constraint violation error: "
                + constraintViolationMessage
                : "";
        String dataIntegrityMessage = dataIntegrityViolationMessage != null
                ? "Data integrity violation error: "
                + dataIntegrityViolationMessage
                : "";
        String persistentObjectMessage = persistentObjectMessages != null
                ? "Persistent object error: "
                + persistentObjectMessages
                : "";

        this.message = message + " " + constraintMessage + " "
                + dataIntegrityMessage + " "
                + persistentObjectMessage;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
