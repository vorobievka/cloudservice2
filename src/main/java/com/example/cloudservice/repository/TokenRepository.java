package com.example.cloudservice.repository;

import com.example.cloudservice.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity,Long> {

    Optional<TokenEntity> getTokenEntityByToken(String token);
}
