package br.com.vitim.urlshortener.repository;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.vitim.urlshortener.model.Alias;

public interface AliasRepository extends CrudRepository<Alias, Long> {
    Optional<Alias> findByAlias(String alias);

    @Transactional
    @Query("INSERT INTO aliases (url) VALUES (:url) RETURNING *")
    Alias createRandomAlias(@Param("url") String url);

    default Alias createAlias(Alias alias) {
        if (Objects.isNull(alias.getAlias())) {
            return createRandomAlias(alias.getUrl());
        }
        return this.save(alias);
    }

}
