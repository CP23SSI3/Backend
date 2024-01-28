package com.example.internhub.services;

import com.example.internhub.entities.User;
import com.example.internhub.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            User user = userService.findUserByUserName(username);
            ArrayList<SimpleGrantedAuthority> role = new ArrayList<>();
            role.add(new SimpleGrantedAuthority(user.getRole().toString()));
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), role);
            System.out.println(userDetails);
            return userDetails;
        } catch (Exception ex) {
            throw new UsernameNotFoundException("User with inputed data is not existed.");
        }
    }
}
