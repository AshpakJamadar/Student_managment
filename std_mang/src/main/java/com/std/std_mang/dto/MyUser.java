package com.std.std_mang.dto;

import com.std.std_mang.helper.Aes;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class MyUser {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private int id;

  private int otp;
  private boolean verification;
  
  @Size(min = 3, max = 20,message = "*Enter the corect name")
  private String name;

  @Email(message = "*Enter the correct email id")
  @NotBlank(message = "*The email is blank")
  private String email;

  
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "* Enter more than 8 characters Consisting of One Upper Case, One Lower Case, One Special Charecter, One Number")
    private String password;

  // public void setPassword(String password){
  //     this.password= Aes.encrypt(password, "123");
  // }
  // public String getPassword(){
  //   return Aes.decrypt(this.password, "123");
  // }


}
