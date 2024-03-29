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
    @Query(value = "select * " +
            "from (((users u left join languages l on l.userId = u.userId) " +
            "left join skills s on u.userId = s.userId) " +
            "left join educations edu on u.userId = edu.userId) " +
            "left join experiences ex on u.userId = ex.userId " +
            "where u.userId = :userId " +
            "order by l.languageName asc, s.skillName asc, edu.startedYear desc, ex.startedYear desc"
            ,
    nativeQuery = true)
    public User getUserByIdSort(@RequestParam("userId") String userId);
}
