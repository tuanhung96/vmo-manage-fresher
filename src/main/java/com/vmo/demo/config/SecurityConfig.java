package com.vmo.demo.config;

import com.vmo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    private UserService userService;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(UserService userService, JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    //passwordEncoder bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationManager bean definition
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
//                                .antMatchers("/**").permitAll()
                                .antMatchers("/hello").permitAll()
                                .antMatchers("/authenticate").permitAll()
                                .antMatchers("/refreshToken").permitAll()
                                .antMatchers("/swagger-ui", "/swagger-ui/**").permitAll()
                                .antMatchers("/swagger-resources", "/swagger-resources/**").permitAll()
                                .antMatchers("/v2/api-docs").permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/authenticate")).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(false)     // second login will cause the first to be invalidated
                )
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint((request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage())
                        )
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf(csrf -> csrf.disable());

//        To protect MVC applications, Spring adds a CSRF token to each generated view.
//        This token must be submitted to the server on every HTTP request that modifies state (PATCH, POST, PUT and DELETE — not GET).
//        This protects our application against CSRF attacks since an attacker can’t get this token from their own page.
//        token chỉ bắt buộc với PATCH, POST, PUT and DELETE

        return http.build();
    }
}
