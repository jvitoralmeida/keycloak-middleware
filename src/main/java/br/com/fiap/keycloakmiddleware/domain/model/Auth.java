package br.com.fiap.keycloakmiddleware.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    @JsonProperty("access_token")
    private String accessToken;
}
