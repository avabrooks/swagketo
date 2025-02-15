package com.nighthawk.csa.database;

import com.nighthawk.csa.database.posts.Posts;
import com.nighthawk.csa.database.posts.PostsJpaRepository;
import com.nighthawk.csa.database.role.Role;
import com.nighthawk.csa.database.role.RoleJpaRepository;
import com.nighthawk.csa.database.scrum.Scrum;
import com.nighthawk.csa.database.scrum.ScrumJpaRepository;
import com.nighthawk.csa.database.user.User;
import com.nighthawk.csa.database.user.UserJpaRepository;
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
    @Autowired
    private PostsJpaRepository PostsJpaRepository;

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
        System.out.println("this is running");
        User users = UserJpaRepository.findByEmail(email); // setting variable user equal to the method finding the username in the database
        if(users==null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        users.getRoles().forEach(role -> { //loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); //create a SimpleGrantedAuthority by passed in role, adding it all to the authorities list, list of roles gets past in for spring security
        });
        return new org.springframework.security.core.userdetails.User(users.getEmail(), users.getPassword(), authorities);
    }


    /*

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String message) throws UsernameNotFoundException {
        Posts posts = PostsJpaRepository.findByMessage(message); // setting variable user equal to the method finding the username in the database
        System.out.println("its running this portion");
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(posts.getMessage(), posts.getName(), authorities);

        if(posts==null){
            throw new UsernameNotFoundException("User not found in database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        posts.getRoles().forEach(role -> { //loop through roles
            authorities.add(new SimpleGrantedAuthority(role.getName())); //create a SimpleGrantedAuthority by passed in role, adding it all to the authorities list, list of roles gets past in for spring security
        });
        return new org.springframework.security.core.userdetails.User(posts.getMessage(), authorities);
    }*/





    /* Person Section */

    public  List<User>listAll() {
        return UserJpaRepository.findAllByOrderByNameAsc();
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

    public void delete(long id) {
        deleteScrumMember(id);   // make sure ID is no longer present in SCRUM Teams
        UserJpaRepository.deleteById(id);
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

    public void saveScrum(Scrum scrum) {
        scrumJpaRepository.save(scrum);
    }

    public List<Scrum> listAllScrums() {
        return scrumJpaRepository.findAllByOrderByNameAsc();
    }

    public Scrum getScrum(long id) {
        return (scrumJpaRepository.findById(id).isPresent())
                ? scrumJpaRepository.findById(id).get()
                : null;
    }

    public void deleteScrum(long id) {
        scrumJpaRepository.deleteById(id);
    }

    private boolean is_deletedScrum(User p, long id) {
        return (p != null && p.getId() == id );
    }

    public void deleteScrumMember(long id) {
        List<Scrum> scrum_list = scrumJpaRepository.findAll();
        for (Scrum scrum: scrum_list) {
            boolean changed = false;
            if (is_deletedScrum(scrum.getPrimary(), id)) {scrum.setPrimary(null); changed = true;}
            if (is_deletedScrum(scrum.getMember1(), id)) {scrum.setMember1(null); changed = true;}
            if (is_deletedScrum(scrum.getMember2(), id)) {scrum.setMember2(null); changed = true;}
            if (is_deletedScrum(scrum.getMember3(), id)) {scrum.setMember3(null); changed = true;}
            if (is_deletedScrum(scrum.getMember4(), id)) {scrum.setMember4(null); changed = true;}
            if (changed) {
                scrumJpaRepository.save(scrum);}
        }

    }

    /* posts section */
    public List<Posts> listAllPosts() {
        return PostsJpaRepository.findAll();
    }

    public void save(Posts posts) {
        PostsJpaRepository.save(posts);
    }

    // custom query to find anything containing term in name or message ignoring case
    public  List<Posts>listLike(String term) {
        return PostsJpaRepository.findByNameContainingIgnoreCaseOrMessageContainingIgnoreCase(term, term);
    }

    // custom query to find anything containing term in name or message ignoring case
    public  List<Posts>listLikeNative(String term) {
        String like_term = String.format("%%%s%%",term);  // Like required % rappers
        return PostsJpaRepository.findByLikeTermNative(like_term);
    }



}