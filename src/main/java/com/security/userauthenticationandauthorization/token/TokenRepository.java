package com.security.userauthenticationandauthorization.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    //mtd to get all valid tokens for specific user
    @Query("select t from Token t inner join User u on t.user.id = u.id where u.id=:userId and (t.expired = false or t.revoked =false)")
    List<Token> findAllValidTokenByUser(Integer userId);
    Optional<Token> findByToken(String token);

    //mtd to find token by token itself

}
