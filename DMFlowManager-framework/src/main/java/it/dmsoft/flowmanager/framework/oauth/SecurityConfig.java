package it.dmsoft.flowmanager.framework.oauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

@Configuration
@EnableWebSecurity
@EnableWebSocketSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	public static ThreadLocal<Jwt> threadLocalJwt = new ThreadLocal<>();

	@Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
	private String clientId;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/execute/**", "/flow-websocket/**", "/flow-websocket").hasAnyAuthority("flowmanager_use");
                })
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        return http.build();
    }
    
    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        // The following code configures message-level authorization rules.
    	//SecurityContext sc = SecurityContextHolder.getContext();
        //sc.setAuthentication(auth);
    	messages.simpDestMatchers("/flow-websocket", "/flow-websocket/**").hasAnyAuthority("flowmanager_use");// fullyAuthenticated();
            // Specify that messages with destination "/user/**" should have the "USER" role.
            //.simpDestMatchers("/user/**").hasRole("USER");

        // Return the built MessageMatcherDelegatingAuthorizationManager.
        return messages.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterForKeycloak() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt -> {
        	threadLocalJwt.set(jwt);
        	
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

            Object client = resourceAccess.get(clientId);

            LinkedTreeMap<String, List<String>> clientRoleMap = (LinkedTreeMap<String, List<String>>) client;

            List<String> clientRoles = new ArrayList<>(clientRoleMap.get("roles"));

            return clientRoles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        };

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}