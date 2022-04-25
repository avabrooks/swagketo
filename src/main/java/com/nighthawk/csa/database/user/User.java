package com.nighthawk.csa.database.user;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


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