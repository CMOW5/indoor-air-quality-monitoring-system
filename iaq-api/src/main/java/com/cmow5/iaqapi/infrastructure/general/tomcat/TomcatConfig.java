package com.cmow5.iaqapi.infrastructure.general.tomcat;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static com.cmow5.iaqapi.infrastructure.general.tomcat.TomcatConfig.LOCAL_PROFILE;

@Configuration
@Profile(LOCAL_PROFILE)
public class TomcatConfig {

    public static final String LOCAL_PROFILE = "local";

    /**
     * Here, we customize the tomcat server by adding an HTTP connector for local testing
     */
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer(
            @Value("${server.http.port}") int httpPort) {

        return new WebServerFactoryCustomizer<TomcatServletWebServerFactory>() {
            @Override
            public void customize(TomcatServletWebServerFactory server) {
                Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
                connector.setPort(httpPort);
                server.addAdditionalTomcatConnectors(connector);
            }
        };
    }
}
