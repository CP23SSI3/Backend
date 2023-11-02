package com.example.internhub.repositories;

import com.example.internhub.entities.OpenPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenPositionRepository extends JpaRepository<OpenPosition, String> {
}
