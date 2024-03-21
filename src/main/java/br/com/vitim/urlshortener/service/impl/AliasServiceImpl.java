package br.com.vitim.urlshortener.service.impl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;

import br.com.vitim.urlshortener.exception.impl.AliasAlreadyExistsException;
import br.com.vitim.urlshortener.exception.impl.AliasNotFoundException;
import br.com.vitim.urlshortener.exception.impl.EmptyUrlException;
import br.com.vitim.urlshortener.exception.impl.InvalidUrlException;
import br.com.vitim.urlshortener.model.Alias;
import br.com.vitim.urlshortener.repository.AliasRepository;
import br.com.vitim.urlshortener.service.AliasService;

@Service
public class AliasServiceImpl implements AliasService {

    @Autowired
    private AliasRepository aliasRepository;

    @Override
    public Alias createAlias(Alias alias) {
        try {
            // validate url
            new URL(alias.getUrl()).toURI();
            return aliasRepository.createAlias(alias);
        } catch (MalformedURLException | URISyntaxException e) {
            if (Objects.isNull(alias.getUrl()))
                throw new EmptyUrlException();
            throw new InvalidUrlException(alias.getUrl());
        } catch (DbActionExecutionException e) {
            if (e.getCause() instanceof DuplicateKeyException)
                throw new AliasAlreadyExistsException(alias.getAlias());
            throw e;
        }
    }

    @Override
    public Alias getAlias(String alias) {
        return aliasRepository.findByAlias(alias)
            .orElseThrow(() -> new AliasNotFoundException(alias));
    }

    @Override
    public void deleteAlias(String alias) {
        // @TODO: implement this
    }

    @Override
    public void updateAlias(Alias alias) {
        // @TODO: implement this
    }
}
