package br.com.vitim.urlshortener.exception;

import br.com.vitim.urlshortener.model.ErrorCode;

public abstract class UrlShortenerException extends RuntimeException {
    public UrlShortenerException(String message) {
        super(message);
    }

    public abstract ErrorCode getErrorCode();
}