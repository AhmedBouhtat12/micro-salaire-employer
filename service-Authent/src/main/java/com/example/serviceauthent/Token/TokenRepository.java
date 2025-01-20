package com.example.serviceauthent.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
  @Query(value = """
      select t from Token t inner join Account acc\s
      on t.account.id = acc.id\s
      where acc.id = :id and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(UUID id);
  Optional<Token> findByToken(String token);
}
