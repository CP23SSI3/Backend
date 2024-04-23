package com.example.internhub.repositories;

import com.example.internhub.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, String> {
}
