package com.dmitriykh.test.Spring.TEST.project.authorization.repositories;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u set u.firstName =:firstName,"+
            " u.lastName =:lastName," + "u.email =:email where u.id =:id")
    void update(String firstName, String lastName, String email, Long id);
}
