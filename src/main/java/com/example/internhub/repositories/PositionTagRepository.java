package com.example.internhub.repositories;

import com.example.internhub.entities.PositionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionTagRepository extends JpaRepository <PositionTag, String> {
}
