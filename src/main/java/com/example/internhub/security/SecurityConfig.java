package com.example.internhub.security;

import com.example.internhub.entities.UserPrinciple;
import com.example.internhub.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests()
//                .antMatchers("/api/v1/posts")
//                .permitAll();
//        http.authorizeRequests()
//                .antMatchers("/api/v1/users")
//                        .hasAuthority("ADMIN");
//        http.addFilterBefore(new JwtRequestFilter(), FilterSecurityInterceptor.class);
        http.cors().and().csrf().disable();
//        http.addFilterBefore(new JwtRequestFilter(), FilterSecurityInterceptor.class)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"api/v1/posts").permitAll()
//                .antMatchers(HttpMethod.POST, "api/v1/posts").authenticated()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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
