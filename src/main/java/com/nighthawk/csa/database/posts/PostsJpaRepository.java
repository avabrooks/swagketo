package com.nighthawk.csa.database.posts;

import com.nighthawk.csa.database.posts.Posts
import com.nighthawk.csa.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsJpaRepository extends JpaRepository<Posts, Long>{
    Posts findByEmail(String email);
    List<Posts> findAllByOrderByNameAsc();

    List<Posts> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);


    @Query(
            value = "SELECT * FROM User u WHERE u.name LIKE ?1 or u.email LIKE ?1",
            nativeQuery = true)
    List<Posts> findByLikeTermNative(String term);
}
