package org.alvarowau.user.config.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.user.config.security.JwtTokenProvider;
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
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            jwtToken = jwtToken.substring(7);

            try {
                log.info("Token JWT recibido: {}", jwtToken);

                DecodedJWT decodedJWT = jwtTokenProvider.validateToken(jwtToken);
                String username = jwtTokenProvider.extractUsername(decodedJWT);
                List<String> authoritiesList = jwtTokenProvider.getSpecificClaim(decodedJWT, "authorities").asList(String.class);

                log.info("Nombre de usuario extraído del token: {}", username);
                log.info("Roles extraídos del token: {}", authoritiesList);

                Collection<? extends GrantedAuthority> authorities = authoritiesList.stream()
                        .map(SimpleGrantedAuthority::new).toList();

                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.info("Autenticación establecida para el usuario: {}", username);

            } catch (JWTVerificationException e) {
                log.info("Error al verificar el token JWT: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
        } else {
            log.info("No se encontró el token JWT en la solicitud.");
        }

        filterChain.doFilter(request, response);
    }
}
