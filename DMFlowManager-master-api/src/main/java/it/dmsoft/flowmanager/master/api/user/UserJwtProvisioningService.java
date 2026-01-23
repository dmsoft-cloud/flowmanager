package it.dmsoft.flowmanager.master.api.user;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.dmsoft.flowmanager.be.entities.User;
import it.dmsoft.flowmanager.be.keys.UserId;
import it.dmsoft.flowmanager.common.model.UserData;
import it.dmsoft.flowmanager.framework.util.StringUtils;
import it.dmsoft.flowmanager.master.api.user.mapper.UserMapper;
import it.dmsoft.flowmanager.master.repositories.UserRepository;


@Service
public class UserJwtProvisioningService {

    private static final Logger log = LoggerFactory.getLogger(UserJwtProvisioningService.class);

    @Autowired
    private UserMapper userMapper;

    private final UserRepository userRepository;
    private final JwtDecoder jwtDecoder;       // opzionale, se vuoi anche la versione “da stringa”

    public UserJwtProvisioningService(UserRepository userRepository,
                                      UserMapper userMapper,
                                      JwtDecoder jwtDecoder // puoi rimuoverlo se non ti serve decode da stringa
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtDecoder = jwtDecoder;
    }

    /**
     * Flusso “standard” quando stai già dietro a un Resource Server:
     * prende il Jwt dal SecurityContext, fa auto-provisioning e restituisce i claim.
     */
    @Transactional
    public UserData processCurrentJwtAndReturnClaims(JwtAuthenticationToken jwt) {
        Map<String, Object> claims = new LinkedHashMap<>(jwt.getToken().getClaims());

        List<String> roles = jwt.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        return  autoProvisionUser(jwt);
    }

    @Transactional
    protected UserData autoProvisionUser(JwtAuthenticationToken jwt) {


        String username  = jwt.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        String keycloakUserId = jwt.getToken().getClaimAsString(StandardClaimNames.SUB);
        String firstName = jwt.getToken().getClaimAsString(StandardClaimNames.GIVEN_NAME);
        String familyName = jwt.getToken().getClaimAsString(StandardClaimNames.FAMILY_NAME);
        String name = jwt.getToken().getClaimAsString(StandardClaimNames.NAME);
        String email     = jwt.getToken().getClaimAsString(StandardClaimNames.EMAIL);

        String customerRealm = StringUtils.firstNonBlank(
                jwt.getToken().getClaimAsString("realm"),
                jwt.getToken().getClaimAsString("realm_name"),
                extractRealmFromIssuer(jwt.getToken().getClaimAsString("iss"))
        );

        if (username == null || username.isBlank()) {
        	username = email;
        }

        final String userKey      = username;
        final String customerKey  = customerRealm;
        
        // chiave composta per l'entità User
        UserId id = new UserId(userKey, customerKey);

        User entity = userRepository.findById(id).orElseGet(() -> {
            User u = new User();
            u.setUsername(userKey);
            u.setCustomer(customerKey);
            return u;
        });

        // UPDATE PARZIALE: setto SOLO se il claim è presente (non sovrascrivo style/language)
        if (keycloakUserId != null) {
			entity.setKeycloakUserId(keycloakUserId);
		}
        /* spostati nella chiave
        if (customerRealm  != null) {
			entity.setCustomer(customerRealm);
		}
		*/
        if (firstName      != null) {
			entity.setFirstName(firstName);
		}
        if (familyName       != null) {
			entity.setLastName(familyName);
		}
        if (email          != null) {
			entity.setEmail(email);
		}

        return  userMapper.convertEntity(userRepository.save(entity));
    }

    private static String extractRealmFromIssuer(String iss) {
        if (StringUtils.isBlank(iss)) {
			return "";
		}
        int idx = iss.indexOf("/realms/");
        if (idx < 0) {
			return "";
		}
        String tail = iss.substring(idx + "/realms/".length());
        int slash = tail.indexOf('/');
        return (slash >= 0) ? tail.substring(0, slash) : tail;
    }


}
