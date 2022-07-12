package br.com.fiap.keycloakmiddleware.domain.services;

import br.com.fiap.keycloakmiddleware.domain.config.KeyCloakConfig;
import br.com.fiap.keycloakmiddleware.domain.model.*;
import br.com.fiap.keycloakmiddleware.infra.keycloak.AdmKeycloakClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    KeyCloakConfig config;


    @Autowired
    AdmKeycloakClient admKeycloakClient;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Value("${keycloak.url}")
    private String keyCloakUrl;

    public void createNewUser(User user) {

        Auth adminToken = getAdminToken();

        KeycloakUser keycloakUser = createUserObject(user);

        String userLocation = createUserOnKeyCloak(adminToken.getAccessToken(), keycloakUser);
        addUserOnGroup(adminToken.getAccessToken(),userLocation);
    }

    private KeycloakUser createUserObject(User user) {
        return KeycloakUser.builder().enabled(true).username(user.cpf).credentials(List.of(Credentials.builder().value(user.password).temporary(false).type("password").build())).build();
    }

    private Auth getAdminToken() {
        return admKeycloakClient.generateMasterToken(config.getUsername(), config.getPassword(), config.getGrantType(), config.getClientId());
    }

    public Auth getUserToken(User user) {
        return admKeycloakClient.generateMasterToken(user.getCpf(), user.getPassword(), config.getGrantType(), config.getClientId());
    }

    private String createUserOnKeyCloak(String auth, KeycloakUser user){

        String url = keyCloakUrl + "/admin/realms/fiap/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(auth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity(user,headers);

        ResponseEntity entity = restTemplateBuilder.build().exchange(url, HttpMethod.POST, request, ResponseEntity.class);
        if(entity.getStatusCodeValue() == 201){
            log.info("user created successfully");
            return entity.getHeaders().getLocation().toString();
        }

        throw new RuntimeException("Error creating new user");
    }

    private void addUserOnGroup(String accessToken, String location){
        List<RoleRepresentation> roles = List.of(new RoleRepresentation("8b654338-ab8a-4e72-b608-30a7901a174c", "user"));

        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `accept` header
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(roles,headers);

        ResponseEntity<Void> entity = restTemplateBuilder.build().exchange(location.concat("/role-mappings/realm"),HttpMethod.POST, request, Void.class);

        if(entity.getStatusCodeValue() == 204){
            log.info("role assigned successfully");
        }
    }
}
