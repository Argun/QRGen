package net.glxn.qrgen.exception;

@SuppressWarnings("serial")
public class QRGenerationException extends RuntimeException {
    public QRGenerationException(String message, Throwable underlyingException) {
        super(message, underlyingException);
    }
}
