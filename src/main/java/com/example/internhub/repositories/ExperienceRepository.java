package com.example.internhub.repositories;

import com.example.internhub.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, String> {
}
