package br.com.vitim.urlshortener.exception;

public abstract class AliasException extends UrlShortenerException {
    public AliasException(String message) {
        super(message);
    }
}