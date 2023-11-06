package com.dmitriykh.test.Spring.TEST.project.passAuth.services;

import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IPassAuthService {
    List<PassAuth> getAllPassAuth();
    PassAuth getPassAuthById(long id);

    Optional<PassAuth> findById(Long id);

    void savePassAuth (PassAuth passAuth);

    void deleteAllPassAuth ();

//    void savePassAuthUser (PassAuth passAuth, User user);
    void delPassAuthById (long id);
    Page<PassAuth> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    void updPassAuth (long id, String name, String url, String username, String password);
}
