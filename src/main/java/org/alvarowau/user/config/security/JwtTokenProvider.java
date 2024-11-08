package org.alvarowau.user.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase utilitaria para manejar operaciones de JWT (JSON Web Tokens) como creación y validación.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    private static final long TOKEN_EXPIRATION_TIME = 1800000;
    private static final String AUTHORITIES_CLAIM = "authorities";
    @Value("${security.jwt.key.private}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String username = authentication.getName();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).toList();

        log.info("Creando token JWT para usuario: {}", username);
        log.info("Roles asignados al usuario: {}", authorities);

        String token = JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim(AUTHORITIES_CLAIM, authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        log.info("Token JWT generado exitosamente: {}", token);
        return token;
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            log.info("Token JWT validado correctamente para el usuario: {}", decodedJWT.getSubject());

            return decodedJWT;
        } catch (JWTVerificationException ex) {
            log.info("Error al validar el token JWT: {}", ex.getMessage());
            throw new JWTVerificationException("Token inválido, no autorizado");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        log.info("Nombre de usuario extraído del token: {}", username);
        return username;
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        log.info("Claim específico '{}' extraído: {}", claimName, claim);
        return claim;
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        Map<String, Claim> claims = decodedJWT.getClaims();
        log.info("Todos los claims extraídos del token: {}", claims);
        return claims;
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        String username = extractUsername(decodedJWT);
        log.info("Nombre de usuario extraído del token validado: {}", username);
        return username;
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        List<String> roles = decodedJWT.getClaim(AUTHORITIES_CLAIM).asList(String.class);

        log.info("Roles extraídos del token: {}", roles);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}
