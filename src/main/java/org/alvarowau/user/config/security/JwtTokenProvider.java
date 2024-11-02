package org.alvarowau.user.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    private static final long TOKEN_EXPIRATION_TIME = 1800000; // 30 minutos
    private static final String AUTHORITIES_CLAIM = "authorities"; // Clave para autoridades

    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String username = authentication.getName(); // Obtener el nombre de usuario

        // Convertir autoridades a una lista de nombres de roles (ej. ["PROVIDER", "CUSTOMER"])
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        logger.info("Creando token para el usuario: {}, Authorities: {}", username, authorities);

        String token = JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim(AUTHORITIES_CLAIM, authorities) // Almacenar autoridades como lista
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        logger.info("Token creado: {}", token);
        return token;
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token); // Validar y decodificar el token

            logger.info("Token validado: {}", decodedJWT);
            logger.info("Usuario extraído del token: {}", decodedJWT.getSubject());
            logger.info("Autoridades extraídas del token: {}", decodedJWT.getClaim(AUTHORITIES_CLAIM).asList(String.class));
            return decodedJWT; // Retornar el token decodificado
        } catch (JWTVerificationException ex) {
            logger.error("Error al validar el token: {}", ex.getMessage());
            throw new JWTVerificationException("Token inválido, no autorizado");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        logger.info("Extrayendo nombre de usuario: {}", username);
        return username;
    }

    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        Claim claim = decodedJWT.getClaim(claimName);
        logger.info("Reclamando '{}': {}", claimName, claim);
        return claim;
    }

    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        Map<String, Claim> claims = decodedJWT.getClaims();
        logger.info("Todos los reclamos extraídos: {}", claims);
        return claims;
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token); // Valida y decodifica el token
        return extractUsername(decodedJWT); // Extrae el nombre de usuario del token decodificado
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT decodedJWT = validateToken(token);
        List<String> roles = decodedJWT.getClaim(AUTHORITIES_CLAIM).asList(String.class);

        // Agregar log para ver qué se obtiene del claim
        logger.info("Roles extraídos del claim '{}': {}", AUTHORITIES_CLAIM, roles);

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Agregar el prefijo ROLE_
                .collect(Collectors.toList());
    }

}
