package com.emp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.model.Emp;
import com.emp.repository.EmpRepository;

@Service
public class EmpService {

    @Autowired
    private EmpRepository repo;

    public void register(Emp e) {
        repo.save(e);
    }

    public Emp login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}