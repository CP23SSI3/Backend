package com.example.internhub.security;

import com.example.internhub.entities.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
//        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
//        dao.setUserDetailsService(userDetailsService);
//        dao.setPasswordEncoder(passwordEncoder());
//        return dao;
//    }

    @Bean
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) {
        try {
            http
//                    .addFilterBefore(jwtRequestFilter, FilterSecurityInterceptor.class)
                    .addFilterBefore(jwtRequestFilter, JwtRequestFilter.class)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/companies").hasAnyAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/position-tags").permitAll()
                    .antMatchers(HttpMethod.DELETE, "/api/v1/posts").hasAnyAuthority("ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.GET, "/api/v1/posts").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/v1/posts").hasAnyAuthority( "ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.PUT, "/api/v1/posts/**").hasAnyAuthority( "ADMIN", "COMPANY")
                    .antMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ADMIN")
                    .antMatchers(HttpMethod.GET, "/api/v1/users/username-checking").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/users/username-email-checking").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/users/**").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/v1/users").anonymous()
                    .antMatchers(HttpMethod.POST, "/api/v1/users").hasAnyAuthority("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/v1/users/**").authenticated()
                    .antMatchers("/api/**").denyAll()
                    .and().cors()
                    .and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


//            http.addFilterBefore(new JwtRequestFilter(), FilterSecurityInterceptor.class);
//            http.authorizeRequests()
//                    .antMatchers(HttpMethod.GET, "/api/v1/auth").permitAll()
//                    .antMatchers(HttpMethod.PUT, "/api/v1/addresses/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/companies/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/open-positions").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/position-tags").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/post-position-tags").permitAll()
//                    .antMatchers(HttpMethod.DELETE,"/api/v1/posts/**").hasAnyRole("ADMIN", "COMPANY")
//                    .antMatchers(HttpMethod.GET, "api/v1/posts").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
//                    .antMatchers(HttpMethod.POST,"/api/v1/posts").hasAnyRole("ADMIN", "COMPANY")
//                    .antMatchers(HttpMethod.PUT,"/api/v1/posts/**").hasAnyRole("ADMIN", "COMPANY")
//                    .antMatchers(HttpMethod.GET, "/api/v1/users").permitAll()
//                    .antMatchers(HttpMethod.DELETE, "/api/v1/users").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/username-email-checking").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll();
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