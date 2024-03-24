package com.example.internhub.repositories;


import com.example.internhub.entities.Language;
import com.example.internhub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    public Language[] getLanguagesByUser(User user);
    public Language getLanguagesByUserAndLanguageName(User user, String languageName);

}
