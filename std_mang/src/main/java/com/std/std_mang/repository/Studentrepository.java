package com.std.std_mang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import com.std.std_mang.dto.MyUser;

@Component
public interface Studentrepository extends JpaRepository<MyUser, Integer>{

  boolean existsByEmail(String email);

  MyUser findByEmail(String email);

}

