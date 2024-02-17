package com.example.internhub.repositories;


import com.example.internhub.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepositories extends JpaRepository<Language, String> {
}
