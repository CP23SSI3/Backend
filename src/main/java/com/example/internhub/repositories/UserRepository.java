package com.example.internhub.repositories;

import com.example.internhub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
    public User findByEmail(String email);
}
