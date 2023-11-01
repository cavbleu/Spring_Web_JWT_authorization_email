package com.dmitriykh.test.Spring.TEST.project.passAuth.services;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPassAuthService {
    List<PassAuth> getAllPassAuth();
    PassAuth getPassAuthById(long id);

    void savePassAuth (PassAuth passAuth);

//    void savePassAuthUser (PassAuth passAuth, User user);
    void delPassAuthById (long id);
    Page<PassAuth> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);


}
