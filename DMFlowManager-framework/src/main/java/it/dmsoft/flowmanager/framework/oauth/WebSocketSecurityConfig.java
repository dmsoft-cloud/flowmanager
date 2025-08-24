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
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;

//@Configuration
//@EnableWebSocketSecurity
public class WebSocketSecurityConfig {
	
	public static ThreadLocal<Jwt> threadLocalJwt = new ThreadLocal<>();

	@Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
	private String clientId;
	
    @Bean
    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        // The following code configures message-level authorization rules.
    	//SecurityContext sc = SecurityContextHolder.getContext();
        //sc.setAuthentication(auth);
        
    	messages.simpDestMatchers("/**").fullyAuthenticated();
            // Specify that messages with destination "/user/**" should have the "USER" role.
            //.simpDestMatchers("/user/**").hasRole("USER");

        // Return the built MessageMatcherDelegatingAuthorizationManager.
        return messages.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverterWebsocketForKeycloak() {
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
