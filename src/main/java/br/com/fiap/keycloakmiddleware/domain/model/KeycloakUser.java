package br.com.fiap.keycloakmiddleware.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakUser {

    private String username;
    private boolean enabled;
    private List<Credentials> credentials;
    private List<String> groups;
}
