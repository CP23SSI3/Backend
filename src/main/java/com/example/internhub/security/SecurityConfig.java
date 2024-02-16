package com.example.internhub.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
@Configuration
public class SecurityConfig {

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) {
        try {
            http.addFilterBefore(new JwtRequestFilter(), FilterSecurityInterceptor.class);
            http.authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/v1/auth").permitAll()
                    .antMatchers(HttpMethod.PUT, "/api/v1/addresses/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/open-positions").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/position-tags").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/post-position-tags").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/api/v1/posts/**").hasAnyRole("ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.GET, "api/v1/posts").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/posts").hasAnyRole("ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.PUT,"/api/v1/posts/**").hasAnyRole("ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/users").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/username-email-checking").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll();
            http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            return http.build();
        } catch (Exception ex) {
            return null;
        }
    }
//    @Bean
//    fun securityFilterChain(http: HttpSecurity, jwtRequestFilter: JwtRequestFilter): DefaultSecurityFilterChain? {
//        http.addFilterBefore(jwtRequestFilter, FilterSecurityInterceptor::class.java)
//            .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()
//                .antMatchers("/api/v1/user").hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/leave-request").hasAnyRole("ADMIN")
//                .antMatchers("/api/v1/attendance").hasAnyRole("ADMIN")
//                .and().cors()
//                .and().csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        return http.build()
//    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

}
