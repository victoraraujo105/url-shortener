package br.com.vitim.urlshortener.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    ALIAS_ALREADY_EXISTS(1, "CUSTOM ALIAS ALREADY EXISTS"),
    ALIAS_NOT_FOUND(2, "ALIAS NOT FOUND"),
    EMPTY_URL(3, "EMPTY URL"),
    INVALID_URL(4, "INVALID URL"),
    UNKNOWN_ERROR(5, "UNKNOWN ERROR");

    private int code;
    private String description;
}