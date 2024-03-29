package com.example.internhub.repositories;


import com.example.internhub.entities.Language;
import com.example.internhub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, String> {
    public Language[] getLanguagesByUser(User user);
    public Language getLanguagesByUserAndLanguageName(User user, String languageName);

    @Query(value = "select l.* " +
            "from languages l join users u on l.userId = u.userId " +
            "where binary l.languageName = binary :languageName and u.userId = :userId;",
            nativeQuery = true)
    public Language findExistedLanguageName(@Param("languageName") String languageName,
                                            @Param("userId") String userId);

}
