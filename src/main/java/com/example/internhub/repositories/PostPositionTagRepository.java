package com.example.internhub.repositories;

import com.example.internhub.entities.PostPositionTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PostPositionTagRepository extends JpaRepository<PostPositionTag, String> {

}
