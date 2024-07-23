package main.java.com.example.Pharmacy.Application.config.security;

import main.java.com.example.Pharmacy.Application.config.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final LogoutHandler logoutHandler;

    public SecurityFilterChainConfig(
            AuthenticationProvider authenticationProvider,
            JWTAuthenticationFilter jwtAuthenticationFilter,
            @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authenticationEntryPoint,
            LogoutHandler logoutHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
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
        http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                .requestMatchers(
                        whiteListedRoutes
                )
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/pharmacy/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
                )
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .oauth2Login(oath2 ->
                        oath2.loginPage("/pharmacy/api/v1/auth/login").permitAll()
                )
        ;
        return http.build();
    }
}
