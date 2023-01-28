package com.haw.srs.customerservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

 //   @Getter
 //   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
 //   @Setter(AccessLevel.NONE)
 //   private Customer customer = new Customer(Gender.MALE, email, getPassword(), "name", "name", null);

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Role> roles;



}
