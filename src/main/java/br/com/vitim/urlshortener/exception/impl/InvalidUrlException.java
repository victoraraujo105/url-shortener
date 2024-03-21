package br.com.vitim.urlshortener.exception.impl;

import br.com.vitim.urlshortener.exception.UrlException;
import br.com.vitim.urlshortener.model.ErrorCode;

public class InvalidUrlException extends UrlException {
    public InvalidUrlException(String url) {
        super("Invalid URL: " + url);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.INVALID_URL;
    }
}
