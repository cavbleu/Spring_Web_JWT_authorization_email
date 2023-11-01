package com.dmitriykh.test.Spring.TEST.project.passAuth.services;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import com.dmitriykh.test.Spring.TEST.project.passAuth.entity.PassAuth;
import com.dmitriykh.test.Spring.TEST.project.passAuth.repositories.IPassAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PassAuthServiceIMPL implements IPassAuthService {
    @Autowired
    private IPassAuthRepository iPassAuthRepository;

    private List<PassAuth> passAuths;

    @Override
    public List<PassAuth> getAllPassAuth() {
        return iPassAuthRepository.findAll();
    }

    @Override
    public PassAuth getPassAuthById(long id) {
        Optional <PassAuth> optional = iPassAuthRepository.findById(id);
        PassAuth passAuth = null;
        if (optional.isPresent()) {
            passAuth = optional.get();
        } else {
            throw new RuntimeException("Данные пароля не найден по id :: " + id);
        }
        return passAuth;
    }

    @Override
    public void savePassAuth(PassAuth passAuth) {
        try {
            passAuths = getAllPassAuth();
            Long newId = passAuths.stream().mapToLong(PassAuth::getId).max().getAsLong() + 1;
            passAuth.setId(newId);
            this.iPassAuthRepository.save(passAuth);
        }
        catch (NoSuchElementException e) {
            passAuth.setId(1L);
            this.iPassAuthRepository.save(passAuth);
        }
    }

    @Override
    public void delPassAuthById(long id) {
        this.iPassAuthRepository.deleteById(id);
    }

    @Override
    public Page<PassAuth> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.iPassAuthRepository.findAll(pageable);
    }

//    @Override
//    public void savePassOrUpd(PassAuth passAuth) {
//        if (passAuth.getId() == null) {
//            Long newId = passAuths.stream().mapToLong(PassAuth::getId).max().getAsLong()+1;
//            passAuth.setId(newId);
//            iPassAuthRepository.save(passAuth);
//            return;
//        }
//        else {
//            Optional<PassAuth> optional = iPassAuthRepository.findById(passAuth.getId());
//
//        }
//
//    }
}
