package com.nighthawk.csa.database;

import com.nighthawk.csa.database.user.User;
import com.nighthawk.csa.database.user.UserJpaRepository;
import com.nighthawk.csa.database.role.Role;
import com.nighthawk.csa.database.role.RoleJpaRepository;
import com.nighthawk.csa.database.scrum.ScrumJpaRepository;
import com.nighthawk.csa.database.posts.Posts;
import com.nighthawk.csa.database.posts.PostsJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
This class has an instance of Java Persistence API (JPA)
-- @Autowired annotation. Allows Spring to resolve and inject collaborating beans into our bean.
-- Spring Data JPA will generate a proxy instance
-- Below are some CRUD methods that we can use with our database
*/
@Service
@Transactional
public class ModelRepository implements UserDetailsService {  // "implements" ties ModelRepo to Spring Security
    // Encapsulate many object into a single Bean (User, Roles, and Scrum)
    @Autowired  // Inject UserJpaRepository
    private UserJpaRepository UserJpaRepository;
    @Autowired  // Inject RoleJpaRepository
    private RoleJpaRepository roleJpaRepository;
    @Autowired  // Inject RoleJpaRepository
    private ScrumJpaRepository scrumJpaRepository;

    // Setup Password style for Database storing and lookup
    @Autowired  // Inject PasswordEncoder
    private PasswordEncoder passwordEncoder;
    @Bean  // Sets up password encoding style
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /* UserDetailsService Overrides and maps Person & Roles POJO into Spring Security */
    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = UserJpaRepository.findByEmail(email); // setting variable user equal to the method finding the username in the database
        if(user==null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> { //loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); //create a SimpleGrantedAuthority by passed in role, adding it all to the authorities list, list of roles gets past in for spring security
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


    /* Person Section */

    public  List<User>listAll() {
        return UserJpaRepository.findAllByOrderByNameAsc();
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<User>listLike(String term) {
        return UserJpaRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or email ignoring case
    public  List<User>listLikeNative(String term) {
        String like_term = String.format("%%%s%%",term);  // Like required % rappers
        return UserJpaRepository.findByLikeTermNative(like_term);
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserJpaRepository.save(user);
    }

    public User get(long id) {
        return (UserJpaRepository.findById(id).isPresent())
                ? UserJpaRepository.findById(id).get()
                : null;
    }

    public User getByEmail(String email) {
        return (UserJpaRepository.findByEmail(email));
    }


    public void defaults(String password, String roleName) {
        for (User user: listAll()) {
            if (user.getPassword() == null || user.getPassword().isEmpty() || user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(password));
            }
            if (user.getRoles().isEmpty()) {
                Role role = roleJpaRepository.findByName(roleName);
                if (role != null) { // verify role
                    user.getRoles().add(role);
                }
            }
        }
    }


    /* Roles Section */

    public void saveRole(Role role) {
        Role roleObj = roleJpaRepository.findByName(role.getName());
        if (roleObj == null) {  // only add if it is not found
            roleJpaRepository.save(role);
        }
    }

    public  List<Role>listAllRoles() {
        return roleJpaRepository.findAll();
    }

    public Role findRole(String roleName) {
        return roleJpaRepository.findByName(roleName);
    }

    public void addRoleToUser(String email, String roleName) { // by passing in the two strings you are giving the user that certain role
        User user = UserJpaRepository.findByEmail(email);
        if (user != null) {   // verify person
            Role role = roleJpaRepository.findByName(roleName);
            if (role != null) { // verify role
                boolean addRole = true;
                for (Role roleObj : user.getRoles()) {    // only add if user is missing role
                    if (roleObj.getName().equals(roleName)) {
                        addRole = false;
                        break;
                    }
                }
                if (addRole) user.getRoles().add(role);   // everything is valid for adding role
            }
        }
    }


    /* Scrum Section */


    public List<Posts> listallPosts() {
        return PostsJpaRepository.findAllByOrderByNameAsc();
    }





}