package com.nighthawk.csa.database.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsJpaRepository extends JpaRepository<Posts, Long>{

    List<Posts> findAll();

    List<Posts> findByNameContainingIgnoreCaseOrMessageContainingIgnoreCase(String name, String message);

    @Query(
            value = "SELECT * FROM Posts p WHERE p.name LIKE ?1 or p.message LIKE ?1",
            nativeQuery = true)
    List<Posts> findByLikeTermNative(String term);



}
