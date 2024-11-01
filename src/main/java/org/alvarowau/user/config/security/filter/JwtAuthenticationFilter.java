package org.alvarowau.user.config.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

/**
 * Filter for validating JWT tokens and setting the authentication in the security context.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Validates the JWT token from the request header and sets the authentication in the security context.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7); // Eliminar "Bearer " del token
            logger.info("Token JWT recibido: {}", jwtToken);

            try {
                DecodedJWT decodedJWT = jwtTokenProvider.validateToken(jwtToken); // Validar el token
                logger.info("Token JWT validado exitosamente.");

                String username = jwtTokenProvider.extractUsername(decodedJWT); // Extraer el nombre de usuario
                logger.info("Usuario extraído del token: {}", username);

                String stringAuthorities = jwtTokenProvider.getSpecificClaim(decodedJWT, "authorities").asString(); // Extraer autoridades
                logger.info("Autoridades extraídas del token: {}", stringAuthorities);

                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities); // Convertir a GrantedAuthority

                // Configurar la autenticación en el contexto de seguridad
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                logger.error("Error al validar el token JWT: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("Token JWT no proporcionado o malformado.");
        }

        filterChain.doFilter(request, response); // Continuar con la cadena de filtros
    }
}
