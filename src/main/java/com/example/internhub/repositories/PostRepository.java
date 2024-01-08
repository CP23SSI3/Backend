package com.example.internhub.repositories;

import com.example.internhub.entities.Post;
import com.example.internhub.entities.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(
            value = "select distinct p.* from ((posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId) join postPositionTags ppt on p.postId = ppt.postId " +
                    "where ((lower(p.title) like concat('%', :searchText, '%')) or (lower(c.compName) like concat('%', :searchText, '%'))) " +
                    "and ((lower(a.city) like concat('%', :city, '%')) and (lower(a.district) like concat('%', :district, '%'))) " +
                    "and (p.status in :status) " +
//                    "and (ppt.positionTagName in :tags or :tags IS NULL) "
                    "and ppt.positionTagName in :tags"
            ,
            countQuery = "select count(distinct p.postId) from ((posts p join companies c on p.compId = c.compId) join addresses a on p.addressId = a.addressId) join postPositionTags ppt on p.postId = ppt.postId " +
                    "where ((lower(p.title) like concat('%', :searchText, '%')) or (lower(c.compName) like concat('%', :searchText, '%'))) " +
                    "and ((lower(a.city) like concat('%', :city, '%')) and (lower(a.district) like concat('%', :district, '%'))) " +
                    "and (p.status in :status) " +
//                    "and (ppt.positionTagName in :tags or :tags IS NULL) "
                    "and ppt.positionTagName in :tags"
            ,
            nativeQuery = true
    )
    public Page<Post> findByQuery(@Param("searchText") String searchText,
                                  @Param("city") String city,
                                  @Param("district") String district,
                                  @Param("status") String[] status,
                                  @Param("tags") List<String> tags,
                                  Pageable pageable);
}
