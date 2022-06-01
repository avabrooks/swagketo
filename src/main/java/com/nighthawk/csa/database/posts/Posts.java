package com.nighthawk.csa.database.posts;

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
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String role;

    @NotEmpty
    private String message;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;


    public Posts(String message, String name, String role) {
        this.name = name;
        this.role = role;
        this.message = message;
    }

    public String getName(){
        return name;
    }
    public String getMessage(){
        return message;
    }
    public String getRole(){return role;}


}