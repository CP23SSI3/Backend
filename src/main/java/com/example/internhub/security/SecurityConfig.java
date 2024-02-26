package com.example.internhub.security;

import com.example.internhub.entities.UserPrinciple;
import com.example.internhub.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) {
//        try {
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
//                    .antMatchers(HttpMethod.GET, "/api/v1/users").hasAnyRole("ADMIN")
//                    .antMatchers(HttpMethod.DELETE, "/api/v1/users").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/v1/username-email-checking").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll();
//            http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            return http.build();
//        } catch (Exception ex) {
//            return null;
//        }
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
//                    .antMatchers(HttpMethod.GET, "/api/v1/auth").permitAll();
//        http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("user")
////                .password(passwordEncoder().encode("password"))
////                .roles("USER");
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
