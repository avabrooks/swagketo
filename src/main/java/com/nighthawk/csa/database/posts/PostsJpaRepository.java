package com.nighthawk.csa.database.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsJpaRepository extends JpaRepository<Posts, Long>{
    List<Posts> findAllByOrderByNameAsc();


    @Query(
            value = "SELECT * FROM User u WHERE u.name LIKE ?1 or u.email LIKE ?1",
            nativeQuery = true)
    List<Posts> findByLikeTermNative(String term);
}
