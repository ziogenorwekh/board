package com.portfolio.boardproject.security;

import com.portfolio.boardproject.jpa.UserRepository;
import org.apache.catalina.core.ApplicationContext;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@ComponentScan
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    public SecurityConfig(CustomUserDetailsService customUserDetailsService,
                          AuthenticationConfiguration authenticationConfiguration,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST, "/api/posts").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/posts").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/posts").authenticated()
                    .requestMatchers(HttpMethod.POST, "/api/users").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/users/").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/api/users/").authenticated();
        });
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.userDetailsService(customUserDetailsService);
        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(jwtAccessDeniedHandler)
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint);
        });
        http.cors(abstractHttpConfigurer->{
            abstractHttpConfigurer.configurationSource(corsConfigurationSource());
        });
        http.authenticationManager(authenticationManager(authenticationConfiguration));
        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                                   authenticationConfiguration) throws Exception {
        ProviderManager authenticationManager = (ProviderManager)authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("**"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.PUT.name(),
                HttpMethod.GET.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.POST.name()));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
