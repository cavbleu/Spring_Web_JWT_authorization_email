package com.dmitriykh.test.Spring.TEST.project.passAuth.repositories;

import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPassAuthRepository extends JpaRepository<PassAuth,Long> {
    Optional<PassAuth> findById(Long id);

    void deleteAll ();

    @Modifying
    @Query(value = "UPDATE PassAuth pa set pa.id=:id,"+
            " pa.name =:name," + "pa.url =:url," +
            " pa.username =:username," + " pa.password =:password" +" where pa.id =:id")
    void update(long id, String name, String url, String username, String password);
}
