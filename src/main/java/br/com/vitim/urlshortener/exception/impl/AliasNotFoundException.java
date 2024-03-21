package br.com.vitim.urlshortener.exception.impl;

import br.com.vitim.urlshortener.exception.AliasException;
import br.com.vitim.urlshortener.model.ErrorCode;

public class AliasNotFoundException extends AliasException {
    public AliasNotFoundException(String alias) {
        super("Alias not found: " + alias);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ALIAS_NOT_FOUND;
    }
}
