package com.proiect2.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User extends RepresentationModel<User>{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="name")
    @Size(max=20, message = "max 20 ch.")
    private String name;

    @Column(name="email")
    @Size(max=30, message = "max 30 ch.")
    private String email;

    @Column(name="pasword")
    @Size(max=30, message = "max 30 ch.")
    private String pasword;
}
