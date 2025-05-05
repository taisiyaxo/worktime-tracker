//package com.worktime.tracker.util;
//
//import com.worktime.tracker.service.UserDetailsServiceImpl;
//import com.worktime.tracker.util.AuthEntryPointJwt;
//import com.worktime.tracker.util.JwtAuthenticationFilter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.*;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.*;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.*;
//
//import java.util.List;
//
//@Configuration(proxyBeanMethods = false)     // <- это ключ к разрыву цикла
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//
//    private final UserDetailsServiceImpl userDetailsService;
//    private final AuthEntryPointJwt unauthorizedHandler;
//    private final JwtAuthenticationFilter jwtAuthFilter;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public static AuthenticationManager authenticationManager(
//            AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthProvider() {
//        var p = new DaoAuthenticationProvider();
//        p.setUserDetailsService(userDetailsService);
//        p.setPasswordEncoder(passwordEncoder());
//        return p;
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration cfg = new CorsConfiguration();
//        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
//        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        cfg.setAllowedHeaders(List.of("*"));
//        cfg.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
//        src.registerCorsConfiguration("/**", cfg);
//        return src;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler))
//                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(a -> a
//                        // 1) Разрешаем все preflight
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        // 2) Как раньше
//                        .requestMatchers("/error").permitAll()
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .requestMatchers(
//                                "/api/tasks", "/api/tasks/**",
//                                "/api/time-entries", "/api/time-entries/**"
//                        ).hasAnyRole("USER", "ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .authenticationProvider(daoAuthProvider());
//
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
