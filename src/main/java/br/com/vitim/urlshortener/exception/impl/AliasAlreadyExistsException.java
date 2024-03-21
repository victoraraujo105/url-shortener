package br.com.vitim.urlshortener.exception.impl;

import br.com.vitim.urlshortener.exception.AliasException;
import br.com.vitim.urlshortener.model.ErrorCode;

public class AliasAlreadyExistsException extends AliasException {
    public AliasAlreadyExistsException(String alias) {
        super("Alias already exists: " + alias);
    }

    public ErrorCode getErrorCode() {
        return ErrorCode.ALIAS_ALREADY_EXISTS;
    }
}
