package com.std.std_mang.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.std.std_mang.dto.MyUser;
import com.std.std_mang.dto.Student;
import com.std.std_mang.helper.Aes;
import com.std.std_mang.helper.HelperforSendingMail;
import com.std.std_mang.repository.Studentdatarepo;
import com.std.std_mang.repository.Studentrepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Mycontroller {

    @Autowired
    Studentrepository studentrepository;

    @Autowired
    HelperforSendingMail mailSender;

    @Autowired
    Studentdatarepo studentdatarepo;

    @GetMapping("/")
    public String getOne() {
        return "home.html";
    }

    @GetMapping("/signup")
    public String loadSignup(ModelMap map) {
        map.put("myUser", new MyUser());
        return "signup.html";
    }

    @PostMapping("/signup")
    public String signup(@Valid MyUser myUser, BindingResult result, ModelMap map) {
        if (studentrepository.existsByEmail(myUser.getEmail())) {
            result.rejectValue("email", "error.email", "Email already exists");
        }

        if (result.hasErrors()) {
            return "signup.html";
        } else {
            try {
                int otp = new Random().nextInt(100000, 1000000);
                myUser.setOtp(otp);
                System.out.println(myUser.getOtp());
                // logic for sending otp
                // carry message anything you want to carry message to frontend use modelmap
                myUser.setPassword(Aes.encrypt(myUser.getPassword(), "123"));
                // mailSender.sendEmail(myUser);
                studentrepository.save(myUser);
                map.put("success", "Otp set successfully");
                map.put("id", myUser.getId());
                return "enter-otp.html";
            } catch (Exception e) {
                map.put("failure", "Error while encrypting password");
                return "signup.html";
            }
        }
    }

    @PostMapping("/otpvervication")
    public String otpVerification(@RequestParam int id, @RequestParam int otp, ModelMap map) {
        MyUser myUser = studentrepository.findById(id).orElseThrow();
        if (myUser.getOtp() == otp) {
            myUser.setVerification(true);
            studentrepository.save(myUser);
            map.put("success", "Verified successfully. Welcome!");
            return "home.html";
        } else {
            map.put("failure", "Invalid otp");
            map.put("id", myUser.getId());
            return "enter-otp.html";
        }
    }

    @GetMapping("/login")
    public String loadLogin() {
        return "login.html";
    }

    @PostMapping("/login")
    public String loginForm(HttpSession session, @RequestParam String email, @RequestParam String password, ModelMap map) {
        MyUser myUser = studentrepository.findByEmail(email);
        if (myUser == null) {
            map.put("failure", "Email not found");
            return "login.html";
        } else {
            try {
                String decryptedPassword = Aes.decrypt(myUser.getPassword(), "123");
                if (password.equals(decryptedPassword)) {
                    if (myUser.isVerification()) {
                        session.setAttribute("user", myUser);
                        map.put("success", "Login Success");
                        return "home.html";
                    } else {
                        int otp = new Random().nextInt(100000, 1000000);
                        myUser.setOtp(otp);
                        studentrepository.save(myUser);
                        System.out.println(myUser.getOtp());
                        // mailSender.sendEmail(myUser);
                        map.put("success", "Otp set successfully");
                        map.put("id", myUser.getId());
                        return "enter-otp.html";
                    }
                } else {
                    map.put("failure", "Password is wrong");
                    return "login.html";
                }
            } catch (Exception e) {
                map.put("failure", "Error while decrypting password");
                return "login.html";
            }
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, ModelMap map) {
        session.removeAttribute("user");
        map.put("success", "Logout success");
        return "home.html";
    }


    @GetMapping("/insert")
    public String insert(HttpSession session,ModelMap map) {
    if (session.getAttribute("user")!=null) {
        return "insert.html";
    }
    else{
        map.put("failure", "Invalid Session");
        return "login.html";
    }
    }

    @PostMapping("/insert")
    public String print(Student student,HttpSession session,ModelMap map,@RequestParam MultipartFile picture) {
    if (session.getAttribute("user")!=null) {
        student.setImage(addToCloudinary(picture));
        studentdatarepo.save(student);
        map.put("success", "record save succsfully");
        return "home.html";

    }
    else{
        map.put("failure", "Invalid Session");
        return "login.html";
    }
}


    public String addToCloudinary(MultipartFile image) {
      Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "your loud name", "api_key",
          "your api key", "api_secret", "yor secret key", "secure", true));
      Map resume = null;
      try {
        Map<String, Object> uploadOptions = new HashMap<String, Object>();
        uploadOptions.put("folder", "Student Pictures");
        resume = cloudinary.uploader().upload(image.getBytes(), uploadOptions);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return (String) resume.get("url");
    }



    @GetMapping("/fetch")
    public String getData() {
       


        return new String();
    }
    





    

}
