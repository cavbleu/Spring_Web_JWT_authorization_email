package com.dmitriykh.test.Spring.TEST.project.passAuth.repositories;

import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPassAuthRepository extends JpaRepository<PassAuth,Long> {
    Optional<PassAuth> findById(Long id);
}
