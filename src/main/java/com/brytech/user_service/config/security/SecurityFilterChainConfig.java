package com.brytech.user_service.config.security;

import com.brytech.user_service.config.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    public SecurityFilterChainConfig(
            AuthenticationProvider authenticationProvider,
            JWTAuthenticationFilter jwtAuthenticationFilter,
            LogoutHandler logoutHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }

    public static final String[] whiteListedRoutes = {
            "/pharmacy/api/v1/users",
            "/pharmacy/api/v1/auth/**",
            "/pharmacy/api/v1/auth/register",
            "/pharmacy/api/v1/auth/login",
            "/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(getRequestMatchers()).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/customer/**")).hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers(new AntPathRequestMatcher("/pharmacist/**")).hasAuthority("ROLE_PHARMACIST")
                        .requestMatchers(new AntPathRequestMatcher("/courier/**")).hasAuthority("ROLE_COURIER")
                        .requestMatchers(new AntPathRequestMatcher("/veterinarian/**")).hasAuthority("ROLE_VETERINARIAN")
                        .requestMatchers(new AntPathRequestMatcher("/finance_manager/**")).hasAuthority("ROLE_FINANCE_MANAGER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(
                                ((request, response, accessDeniedException) -> response.setStatus(403))
                        )
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/pharmacy/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        ((request, response, authentication) -> SecurityContextHolder.clearContext())
                                )
                )
                .oauth2Login(oath2 ->
                        oath2.loginPage("/pharmacy/api/v1/auth/login").permitAll()
                )
                .build();
    }

    private RequestMatcher getRequestMatchers() {
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String route: whiteListedRoutes) {
            matchers.add(new AntPathRequestMatcher(route));
        }
        return new OrRequestMatcher(matchers);
    }
}
