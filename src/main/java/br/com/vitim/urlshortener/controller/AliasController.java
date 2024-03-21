package br.com.vitim.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vitim.urlshortener.exception.UrlShortenerException;
import br.com.vitim.urlshortener.exception.impl.AliasNotFoundException;
import br.com.vitim.urlshortener.model.Alias;
import br.com.vitim.urlshortener.model.AliasDTO;
import br.com.vitim.urlshortener.model.ErrorCode;
import br.com.vitim.urlshortener.service.AliasService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class AliasController {

    @Autowired
    private AliasService aliasService;

    @PutMapping("/create")
    public AliasDTO create(
        @RequestParam("url") String url,
        @RequestParam(name = "CUSTOM_ALIAS", required = false) String customAlias,
        HttpServletRequest request
    ) {
        // start timer
        var start = System.currentTimeMillis();
        // create alias
        var alias = Alias.builder()
            .alias(customAlias)
            .url(url)
            .build();
        // save alias
        try {
            alias = aliasService.createAlias(alias);
        } catch (Exception e) {
            var errorCode = (e instanceof UrlShortenerException) ? ((UrlShortenerException) e).getErrorCode() : ErrorCode.UNKNOWN_ERROR;
            return AliasDTO.of(customAlias, errorCode);
        }
        // end timer
        var end = System.currentTimeMillis();
        // return alias
        return AliasDTO.of(alias, request, end - start);
    }

    @GetMapping("/u/{alias}")
    public ResponseEntity<Object> retrieve(@PathVariable("alias") String alias, HttpServletRequest request) {
        // get alias
        try {
            var myAlias = aliasService.getAlias(alias);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, myAlias.getUrl())
                    .build();
        } catch (Exception e) {
            var errorCode = (e instanceof UrlShortenerException) ? ((UrlShortenerException) e).getErrorCode() : ErrorCode.UNKNOWN_ERROR;
            var status = (e instanceof AliasNotFoundException) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return ResponseEntity
                    .status(status)
                    .body(AliasDTO.of(alias, errorCode));
        }
    }

}
