package br.com.vitim.urlshortener.service;

import br.com.vitim.urlshortener.model.Alias;

public interface AliasService {
    Alias createAlias(Alias alias);
    Alias getAlias(String alias);
    
    void deleteAlias(String alias);
    void updateAlias(Alias alias);
}
