package br.com.fiap.keycloakmiddleware.infra.keycloak;

import br.com.fiap.keycloakmiddleware.domain.model.Auth;
import feign.*;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@FeignClient(name = "adm-keycloak-client", configuration = AdmKeycloakClient.ClientConfig.class)
public interface AdmKeycloakClient {


    @RequestLine("POST  /realms/fiap/protocol/openid-connect/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Auth generateMasterToken(@Param("username") String username, @Param("password") String password, @Param("grant_type") String grantType, @Param("client_id") String clientId);


    @Configuration
    class ClientConfig {

        @Value("${keycloak.url}")
        private String keyCloakUrl;

        @Bean("adm")
        Logger.Level feignLoggerLevel() {
            return Logger.Level.FULL;
        }

        @Bean
        public AdmKeycloakClient createClient1() {
            return Feign.builder().encoder(new FormEncoder(new JacksonEncoder())).decoder(new JacksonDecoder()).logger(new Slf4jLogger()).logLevel(Logger.Level.FULL).target(AdmKeycloakClient.class, keyCloakUrl);
        }
    }
}
