package br.com.vitim.urlshortener.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Table("aliases")
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alias {
    @Id
    private Long id;
    private String alias;
    @NonNull
    private String url;
    @CreatedDate
    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now();

    public static Alias of(AliasDTO aliasDTO, String baseUrl) {
        return Alias.builder()
                .alias(aliasDTO.alias())
                .url(baseUrl + "/" + aliasDTO.alias())
                .build();
    }
}