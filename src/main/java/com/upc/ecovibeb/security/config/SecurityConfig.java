package com.upc.ecovibeb.security.config;

import com.upc.ecovibeb.security.filters.JwtRequestFilter;
import com.upc.ecovibeb.security.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer; // <-- 1. IMPORTA ESTO
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration; // <-- 2. IMPORTA ESTO
import org.springframework.web.cors.CorsConfigurationSource; // <-- 3. IMPORTA ESTO
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // <-- 4. IMPORTA ESTO

import java.util.Arrays; // <-- 5. IMPORTA ESTO

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // --- ¡AQUÍ ESTÁ LA CORRECCIÓN DE CORS! ---
                // 6. Habilita CORS usando la configuración "corsConfigurationSource" de abajo
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        // 7. CORRECCIÓN DE RUTA: Tu log llama a "/api/auth/login"
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // --- ¡AÑADE ESTE BEAN COMPLETO PARA CONFIGURAR CORS! ---
    // 8. Define las reglas de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite peticiones SÓLO desde tu frontend de Angular
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        // Permite los métodos HTTP que usaremos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Permite todos los headers (incluyendo "Authorization" para el token)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Permite que el frontend envíe credenciales (cookies, etc.)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica esta regla a TODAS las rutas
        return source;
    }
}