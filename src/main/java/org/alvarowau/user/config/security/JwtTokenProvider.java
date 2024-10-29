package org.alvarowau.user.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for handling JWT (JSON Web Tokens) operations such as creation and validation.
 */
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    private static final long TOKEN_EXPIRATION_TIME = 1800000; // 30 minutes
    private static final String AUTHORITIES_CLAIM = "authorities"; // Claim key for authorities

    /**
     * Creates a JWT token based on the provided authentication details.
     *
     * @param authentication the authentication object containing user information
     * @return a signed JWT token as a String
     */
    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        String username = authentication.getName(); // Get the username
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(userGenerator)
                .withSubject(username)
                .withClaim(AUTHORITIES_CLAIM, authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
    }

    /**
     * Validates the provided JWT token and returns the decoded token.
     *
     * @param token the JWT token to validate
     * @return the decoded JWT object
     * @throws JWTVerificationException if the token is invalid
     */
    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();
            return verifier.verify(token); // Validate and decode the token
        } catch (JWTVerificationException ex) {
            throw new JWTVerificationException("Token inválido, no autorizado");
        }
    }

    /**
     * Extracts the username from the decoded JWT token.
     *
     * @param decodedJWT the decoded JWT object
     * @return the username as a String
     */
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    /**
     * Retrieves a specific claim from the decoded JWT token.
     *
     * @param decodedJWT the decoded JWT object
     * @param claimName  the name of the claim to retrieve
     * @return the specified claim
     */
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }

    /**
     * Returns all claims contained in the decoded JWT token.
     *
     * @param decodedJWT the decoded JWT object
     * @return a map of claims
     */
    public Map<String, Claim> returnAllClaims(DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }
}
