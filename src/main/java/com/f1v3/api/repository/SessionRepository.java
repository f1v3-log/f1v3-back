package com.f1v3.api.repository;

import com.f1v3.api.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByAccessToken(String accessToken);
}
