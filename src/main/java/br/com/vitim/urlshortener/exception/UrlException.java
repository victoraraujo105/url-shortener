package br.com.vitim.urlshortener.exception;

public abstract class UrlException extends UrlShortenerException {
    public UrlException(String message) {
        super(message);
    }
}