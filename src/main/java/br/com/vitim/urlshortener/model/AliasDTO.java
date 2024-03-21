package br.com.vitim.urlshortener.model;

import java.util.Optional;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.StdConverter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.PositiveOrZero;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AliasDTO(
    @JsonProperty("alias") String alias,
    @JsonProperty("statistics") StatisticsDTO statistics,
    @JsonProperty("url") String url,
    @JsonIgnore ErrorCode errorCode
) {
    public record StatisticsDTO(
        @JsonProperty("time_taken")
        @JsonSerialize(converter = TimeTakenSerializer.class)
        @JsonDeserialize(converter = TimeTakenDeserializer.class)
        @PositiveOrZero
        Long timeTaken
    ) {}

    private static String urlFromRequest(HttpServletRequest request, String alias) {
        return ServletUriComponentsBuilder.fromRequestUri(request).replacePath("u/" +  alias).toUriString();
    }

    public static AliasDTO of(Alias alias, HttpServletRequest request, Long timeTaken) {
        return new AliasDTO(alias.getAlias(), new StatisticsDTO(timeTaken), urlFromRequest(request, alias.getAlias()), null);
    }

    public static AliasDTO of(String alias, ErrorCode errorCode) {
        return new AliasDTO(alias, null, null, errorCode);
    }

    @JsonProperty("err_code")
    public String getErrorCode() {
        return Optional.ofNullable(errorCode).map(ErrorCode::getCode).map("%03d"::formatted).orElse(null);
    }

    @JsonProperty("description")
    public String getErrorDescription() {
        return Optional.ofNullable(errorCode).map(ErrorCode::getDescription).orElse(null);
    }
}

class TimeTakenSerializer extends StdConverter<Long, String> {
    @Override
    public String convert(Long value) {
        return value + "ms";
    }
}

class TimeTakenDeserializer extends StdConverter<String, Long> {
    @Override
    public Long convert(String value) {
        return Long.parseLong(value.replace("ms", ""));
    }
}
