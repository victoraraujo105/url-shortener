package br.com.vitim.urlshortener.exception.impl;

import br.com.vitim.urlshortener.exception.UrlException;
import br.com.vitim.urlshortener.model.ErrorCode;

public class EmptyUrlException extends UrlException {
    public EmptyUrlException() {
        super("URL cannot be empty");
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.EMPTY_URL;
    }
}
