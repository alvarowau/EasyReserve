package org.alvarowau.user.config.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("Header Authorization: {}", jwtToken);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);
            logger.info("Token recibido: {}", jwtToken);

            try {
                DecodedJWT decodedJWT = jwtTokenProvider.validateToken(jwtToken);
                logger.info("Token validado: {}", decodedJWT);

                String username = jwtTokenProvider.extractUsername(decodedJWT);
                List<String> authoritiesList = jwtTokenProvider.getSpecificClaim(decodedJWT, "authorities").asList(String.class);
                logger.info("Authorities extraídas del token: {}", authoritiesList);

                Collection<? extends GrantedAuthority> authorities = authoritiesList.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Autenticación configurada para el usuario: {}", username);

                // Verificación de roles
                logger.info("Roles asignados al usuario {}: ", username);
                for (GrantedAuthority authority : authorities) {
                    logger.info("Rol: {}", authority.getAuthority());
                }

            } catch (JWTVerificationException e) {
                logger.error("Error al validar el token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return; // Termina el flujo aquí
            }
        } else {
            logger.warn("No se encontró un token en el header Authorization");
        }

        filterChain.doFilter(request, response);
    }
}
