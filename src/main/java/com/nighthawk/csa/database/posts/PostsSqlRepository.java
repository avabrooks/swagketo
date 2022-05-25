package com.nighthawk.csa.database.posts;

import com.nighthawk.csa.database.user.User;
import com.nighthawk.csa.database.user.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostsSqlRepository {
    @Autowired
    private PostsJpaRepository jpa;
    private UserJpaRepository userjpa;

    //public  List<Posts>listAll() {
        //return jpa.findAll();
    //}

    //public void save(Posts message) {
      //  jpa.save(message);
    //}

    public User get(long id) {
        return userjpa.findById(id).get();
    }

    public void delete(long id) {
        jpa.deleteById(id);
    }
}
