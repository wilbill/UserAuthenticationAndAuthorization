package com.security.userauthenticationandauthorization.repository;


import com.security.userauthenticationandauthorization.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //though not compulsory coz spring already knows it exists
public interface UserRepository extends JpaRepository<User, Integer> {

}
