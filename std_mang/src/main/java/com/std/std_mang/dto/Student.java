package com.std.std_mang.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
// import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Student {
  
//  @GeneratedValue (strategy = GenerationType.IDENTITY)

  @Id
  @GeneratedValue(generator = "x")
  @SequenceGenerator(initialValue = 101, allocationSize = 1,name = "x")
  private int sid;

  private String name;
  private String standard;
  private LocalDate dob;
  private long mobile;
  private int subject1;
  private int subject2;
  private int subject3;
  private int subject4;
  private int subject5;
  private int subject6;
  private String image;


}

