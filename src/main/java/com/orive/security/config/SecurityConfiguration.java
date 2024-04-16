package com.orive.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.orive.security.user.Permission.MP_CREATE;
import static com.orive.security.user.Permission.MP_DELETE;
import static com.orive.security.user.Permission.MP_READ;
import static com.orive.security.user.Permission.MP_UPDATE;
import static com.orive.security.user.Permission.MLA_CREATE;
import static com.orive.security.user.Permission.MLA_DELETE;
import static com.orive.security.user.Permission.MLA_READ;
import static com.orive.security.user.Permission.MLA_UPDATE;
import static com.orive.security.user.Role.MP;
import static com.orive.security.user.Role.MLA;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
    		"/api/featurecollection/**",
    		"/api/feature/**",
    		"/campaigndetails/**",
    		"/volunteerdetails/**",	
    		"/voter/**",	
    		"/gpstracker/**",
    		"/teams/**",
    		"/events/**",
    		"/opportunities/**",
    		"/strengths/**",
    		"/mail/**",
    		"/threats/**",
    		"/weaknesses/**",
    		"/expensecategories/**",
    		"/expenses/**",
    		"/donations/**",
    		"/donors/**",
    		"/notices/**",
    		"/sms/**",
    		"/areas/**",
    		"/votercategories/**",
    		"voterdatabase/**",
    		"/volunteers/**",
    		"/api/v1/user/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/management/**").hasAnyRole(MP.name(), MLA.name())
                                //.requestMatchers("/api/v1/users/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, "/api/v1/management/**").hasAnyAuthority(MP_READ.name(), MLA_READ.name())
                                .requestMatchers(POST, "/api/v1/management/**").hasAnyAuthority(MP_CREATE.name(), MLA_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/management/**").hasAnyAuthority(MP_UPDATE.name(), MLA_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/management/**").hasAnyAuthority(MP_DELETE.name(), MLA_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
