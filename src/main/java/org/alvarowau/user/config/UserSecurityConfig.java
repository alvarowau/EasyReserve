package org.alvarowau.user.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.user.config.security.filter.JwtAuthenticationFilter;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class UserSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        log.info("Configurando SecurityFilterChain...");

        SecurityFilterChain securityFilterChain = httpSecurity
                .csrf(csrf -> {
                    log.info("Desactivando CSRF para permitir solicitudes sin autenticación");
                    csrf.disable();
                })
                .authorizeHttpRequests(http -> {
                    log.info("Configurando reglas de autorización...");
                    http.requestMatchers("/user/public-data", "/auth/**", "/user/**", "/test/public-data")
                            .permitAll();
                    log.info("Rutas públicas: /user/public-data, /auth/**, /user/**, /test/public-data");

                    http.requestMatchers("/test/**", "/user-management/**", "/services/**", "/schedule/**",
                                    "/appointments/**", "/bookings/**", "/feedbacks/**", "/actions/**")
                            .authenticated();
                    log.info("Rutas autenticadas: /test/**, /user-management/**, /services/**, /schedule/**, " +
                            "/appointments/**, /bookings/**, /feedbacks/**");
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> {
                    log.info("Configurando sesión sin estado (STATELESS)");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), BasicAuthenticationFilter.class)
                .build();

        log.info("SecurityFilterChain configurado exitosamente.");
        return securityFilterChain;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        log.info("Configurando AuthenticationManager...");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserDetailsService service) {
        log.info("Configurando AuthenticationProvider...");

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());

        log.info("AuthenticationProvider configurado con CustomUserDetailsService y BCryptPasswordEncoder.");
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("Configurando PasswordEncoder con BCryptPasswordEncoder...");
        return new BCryptPasswordEncoder();
    }
}
