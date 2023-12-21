package com.example.internhub.repositories;

import com.example.internhub.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String>{
    public Page<Post> findAllByTitleContainsIgnoreCaseOrComp_CompNameContainsIgnoreCase(String title, String companyName, Pageable pageable);
    public Page<Post> findAllByTitleContainsIgnoreCaseOrComp_CompNameContainsIgnoreCaseAndAddress_CityContainsIgnoreCase(String title, String companyName, String city, Pageable pageable);

//    @Query(value = ("select * from posts p, companies c, addresses a " +
//            "where " +
////            " (p.addressId = a.addressId) and " +
////            "((lower(p.title) like concat('%', lower(?1), '%')) or (lower(c.compName) like concat('%', lower(?1), '%'))) " +
////            "and (lower(a.city) like concat('%', lower(?2), '%')) and (lower(a.district) like concat('%', lower(?3), '%'))"
//            "p.addressId = a.addressId and p.compId = c.compId " +
//            "and (lower(p.title) like concat('%', lower(?1), '%'))"),
//            countQuery = ("select count(*) from posts p, companies c, addresses a " +
//                    "where " +
////            " (p.addressId = a.addressId) and " +
////            "((lower(p.title) like concat('%', lower(?1), '%')) or (lower(c.compName) like concat('%', lower(?1), '%'))) " +
////            "and (lower(a.city) like concat('%', lower(?2), '%')) and (lower(a.district) like concat('%', lower(?3), '%'))"
//                    "p.addressId = a.addressId and p.compId = c.compId " +
//                    "and (lower(p.title) like concat('%', lower(?1), '%'))")
//            , nativeQuery = true)
//    @Query(value = ("select p.* from posts p join addresses a on p.addressId = a.addressId where (lower(p.title) like concat('%', lower(?1), '%'))"),
//countQuery = ("select count(*) from posts p join addresses a on p.addressId = a.addressId (lower(p.title) like concat('%', lower(?1), '%'))")
////            "where (lower(p.title) like concat('%', lower(?1), '%'))"
//    , nativeQuery = true)
//    @Query(value = "select * from Posts p, Companies c, Addresses a where (p.compId = c.compId) and (p.addressId = a.addressId) and (lower(p.title) like concat('%', lower(?1), '%'))",
//    countQuery = "select count(*) from Posts p, Companies c, Addresses a where (p.compId = c.compId) and (p.addressId = a.addressId) and (lower(p.title) like concat('%', lower(?1), '%'))",
//    nativeQuery = true)

//        @Query(value = "select * from posts p, companies c, addresses a " +
//            "where p.addressId = a.addressId and p.compId = c.compId and " +
//            "((lower(p.title) like concat('%', lower(?1), '%')) or (lower(c.compName) like concat('%', lower(?1), '%'))) " +
//            "and (lower(a.city) like concat('%', lower(?2), '%')) and (lower(a.district) like concat('%', lower(?3), '%'))"
//            , countQuery = "select count(*) from posts p, companies c, addresses a " +
//                    "where p.addressId = a.addressId and p.compId = c.compId and " +
//            "((lower(p.title) like concat('%', lower(?1), '%')) or (lower(c.compName) like concat('%', lower(?1), '%'))) " +
//            "and (lower(a.city) like concat('%', lower(?2), '%')) and (lower(a.district) like concat('%', lower(?3), '%'))"
//            , nativeQuery = true)
    public Page<Post> findPostByQuery(String searchText, String city, String district, Pageable pageable);
}
