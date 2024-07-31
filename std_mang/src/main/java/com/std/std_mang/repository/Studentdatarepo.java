package com.std.std_mang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.std.std_mang.dto.Student;


public interface Studentdatarepo extends JpaRepository<Student, Integer>{

  
} 
