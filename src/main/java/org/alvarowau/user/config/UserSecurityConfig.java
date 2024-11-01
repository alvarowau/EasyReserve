package org.alvarowau.user.config;

import org.alvarowau.user.config.security.filter.JwtAuthenticationFilter;
import org.alvarowau.user.config.security.JwtTokenProvider;
import org.alvarowau.user.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(UserSecurityConfig.class);
    private final JwtTokenProvider jwtTokenProvider;

    public UserSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        logger.info("UserSecurityConfig inicializado con JwtTokenProvider.");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("Configurando SecurityFilterChain...");
        return httpSecurity
                .csrf(csrf -> {
                    csrf.disable();
                    logger.debug("Protección CSRF deshabilitada.");
                })
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(http -> {
                    http.requestMatchers("/user/public-data", "/auth/**", "/user/**").permitAll();
                    logger.debug("Configurados puntos finales públicos: /user/public-data, /auth/**, /user/**.");
                    http.requestMatchers("/test/**").authenticated();
                    logger.debug("Configurado punto final autenticado: /test/**.");
                })
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                    logger.debug("Política de sesión establecida en STATELESS.");
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        logger.info("Creando bean AuthenticationManager...");
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl service) {
        logger.info("Configurando DaoAuthenticationProvider...");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        logger.debug("DaoAuthenticationProvider configurado con UserDetailsServiceImpl y BCryptPasswordEncoder.");
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Creando bean BCryptPasswordEncoder...");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        logger.info("Creando bean CorsFilter...");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", config);
        logger.debug("CorsFilter configurado con orígenes, encabezados y métodos permitidos.");
        return new CorsFilter(source);
    }
}
