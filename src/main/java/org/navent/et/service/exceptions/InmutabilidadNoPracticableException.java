package org.navent.et.service.exceptions;

public class InmutabilidadNoPracticableException extends Exception{

    public InmutabilidadNoPracticableException(String message) {
        super(message);
    }

    public InmutabilidadNoPracticableException(String message, Throwable cause) {
        super(message, cause);
    }

    public InmutabilidadNoPracticableException(Throwable cause) {
        super(cause);
    }

    public InmutabilidadNoPracticableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


}
