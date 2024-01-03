package com.example.internhub.repositories;

import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(
            value = "select * from (posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId " +
                    "where ((lower(p.title) like concat('%', :searchText, '%')) or (lower(c.compName) like concat('%', :searchText, '%'))) " +
                    "and ((lower(a.city) like concat('%', :city, '%')) and (lower(a.district) like concat('%', :district, '%'))) "
//                    +
//                    "and (p.status = :status)"
            ,
            countQuery = "select count(*) from (posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId " +
                    "where ((lower(p.title) like concat('%', :searchText, '%')) or (lower(c.compName) like concat('%', :searchText, '%'))) " +
                    "and ((lower(a.city) like concat('%', :city, '%')) and (lower(a.district) like concat('%', :district, '%'))) "
//                    +
//                    "and (p.status = :status)"
            ,
            nativeQuery = true
    )
    public Page<Post> findByQuery(@Param("searchText") String searchText, @Param("city") String city,
                                  @Param("district") String district,
//                                  @Param("status") String status,
                                  Pageable pageable);
}
