package br.com.fiap.keycloakmiddleware.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credentials {

    private String type;
    private String value;
    private boolean temporary;
}
