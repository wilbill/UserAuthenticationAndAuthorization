package com.security.userauthenticationandauthorization.user;

import com.security.userauthenticationandauthorization.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data //Getters, setter, toString, equals, hashcode
@Builder //find its difference from setter
@Table(name="_user)")
@AllArgsConstructor
@NoArgsConstructor
//check need for @RequiredAllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING) //By default its EnumType.STRING.ORDINAL, ie takes 1,0,2 etc
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    //this mtd below should return a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return null
        //we i need to return a new obje of simpleGrantedAuthority, takes in our role.name()
        //return List.of(new SimpleGrantedAuthority(role.name()));
        return role.getAuthorities(); //added when working on permissions
    }

    @Override ///this is from the spring security
    public String getPassword(){
        return password;
        //return null;
    }

    @Override
    public String getUsername() {
        //return null;
        return email; //username is our email
    }

    @Override
    public boolean isAccountNonExpired() {
        //return false;
        return true; //we remove the negation
    }
    @Override
    public boolean isAccountNonLocked() {
        //return false;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return false;
        return true;
    }

    @Override
    public boolean isEnabled() {
        //return false;
        return true;

    }
}
