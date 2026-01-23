package it.dmsoft.flowmanager.master.app.controller.user;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.master.api.user.UserJwtProvisioningService;

@RestController
@PreAuthorize("hasAnyAuthority('flowmanager_use')")
public class UserInfoController {

	private final UserJwtProvisioningService provisioningService;

	public UserInfoController(UserJwtProvisioningService provisioningService) {
        this.provisioningService = provisioningService;
    }

/*
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}
*/
/*
	@Bean
	public SecurityFilterChain resourceServerSecurityFilterChain(
	      HttpSecurity http,
	      Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
	  http.oauth2ResourceServer(resourceServer -> {
	    resourceServer.jwt(jwtDecoder -> {
	      jwtDecoder.jwtAuthenticationConverter(authenticationConverter);
	    });
	  });

	  http.sessionManagement(sessions -> {
	    sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  }).csrf(csrf -> {
	    csrf.disable();
	  });

	  /*
	  http.authorizeHttpRequests(requests -> {
	    requests.requestMatchers("/me").authenticated();/*.permitAll();*//*
	    //requests.anyRequest().denyAll();
	  });

	  return http.build();
	}*/

	@GetMapping("/loginUserInfo")
	@PreAuthorize("hasAnyAuthority('flowmanager_use')")
	public UserData getGretting(JwtAuthenticationToken auth) {
		return provisioningService.processCurrentJwtAndReturnClaims(auth);
	}

	@GetMapping("/me2")
	@PreAuthorize("hasAnyAuthority('flowmanager_use2')")
	public UserInfoDto getGretting2(JwtAuthenticationToken auth) {
		//jwtAuthenticationConverter().andThen(auth);
		return null;
	    /*return new UserInfoDto(
	  auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME),
	  auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());*/
	}

	public static record UserInfoDto(String name, List roles) {}
}