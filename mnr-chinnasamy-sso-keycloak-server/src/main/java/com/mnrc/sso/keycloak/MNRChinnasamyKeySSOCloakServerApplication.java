package com.mnrc.sso.keycloak;

import com.mnrc.sso.keycloak.config.MNRChinnasamyKeycloakServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = LiquibaseAutoConfiguration.class)
@EnableConfigurationProperties({ MNRChinnasamyKeycloakServerProperties.class })
public class MNRChinnasamyKeySSOCloakServerApplication {

    private static final Logger LOG = LoggerFactory.getLogger(MNRChinnasamyKeySSOCloakServerApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MNRChinnasamyKeySSOCloakServerApplication.class, args);
    }

    @Bean
    ApplicationListener<ApplicationReadyEvent> onApplicationReadyEventListener(ServerProperties serverProperties,
                                                                               MNRChinnasamyKeycloakServerProperties keycloakServerProperties) {
        return (evt) -> {
            Integer port = serverProperties.getPort();
            String keycloakContextPath = keycloakServerProperties.getContextPath();

            LOG.info("Embedded Keycloak started: http://localhost:{}{} to use keycloak", port, keycloakContextPath);
        };
    }
}