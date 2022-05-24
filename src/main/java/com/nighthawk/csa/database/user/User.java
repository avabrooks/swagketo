package com.nighthawk.csa.database.user;

import com.nighthawk.csa.database.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;


@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min=8)
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    private String recipes;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();


    /* Initializer*/
    public User(String password, String name, String recipes) {
        this.password = password;
        this.name = name;
        this.recipes = recipes;
    }

    public String getName(){
        return name;
    }
    public String getRecipes(){
        return recipes;
    }


}