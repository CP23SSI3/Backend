package com.example.internhub.repositories;

import com.example.internhub.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    @Query(
          value = "select * from posts p",
          countQuery = "select * from posts p",
          nativeQuery = true
    )
    public Page<Object> findAllByQuery(Pageable pageable);

    @Query(
            value = "select * from (posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId " +
                    "where lower(p.title) like concat('%', :searchText, '%') or lower(c.compName) like concat('%', :searchText, '%') ",
            countQuery = "select count(*) from (posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId " +
                    "where lower(p.title) like concat('%', :searchText, '%') or lower(c.compName) like concat('%', :searchText, '%') ",
            nativeQuery = true
    )
//    @Query(
//            value = "select * from posts p join companies c on p.compId = c.compId " +
//                    "where lower(p.title) like concat('%', :searchText, '%') or lower(c.compName) like concat('%', :searchText, '%') ",
//            countQuery = "select count(*) from posts p join companies c on p.compId = c.compId " +
//                    "where lower(p.title) like concat('%', :searchText, '%') or lower(c.compName) like concat('%', :searchText, '%') ",
//            nativeQuery = true
//    )
    public Page<Post> findByQuery(@Param("searchText") String searchText,  Pageable pageable);
}
