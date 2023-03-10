/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.copernic.cat.erp.sales_4_us.controllers;


import com.copernic.cat.erp.sales_4_us.models.User;
import com.copernic.cat.erp.sales_4_us.repository.UserRepository;
import com.copernic.cat.erp.sales_4_us.service.UserService;
import com.copernic.cat.erp.sales_4_us.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private UserService userService;

    //Register Form Controller
    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    //Register process controller
    @RequestMapping("/process_register")
    @PostMapping("process_register")
    public String processRegistration(
            @ModelAttribute(name="user") User user,
            Errors errors,
            RedirectAttributes msg,
            @RequestParam("fileImage") MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            System.out.println(errors.getAllErrors());
            return "error_test";
        }

        Utilities u = new Utilities();
        // Dni no valido
        if (!u.checkDni(user.getDni())) {
            msg.addFlashAttribute("error", u.message("profile.error.dni"));
            return "redirect:/register";
        }
        // If mail exists in DB
        if (checkIfUserExist(user.getEmail())) {
            msg.addFlashAttribute("error", u.message("profile.error.emailAlreadyTaken"));
            return "redirect:/register";
        }
        String fileName;
        user.setPassword(u.encryptPass(user.getPassword()));
        user.setRol("client");
        User savedUser = repo.save(user);

        if (multipartFile.getOriginalFilename() == null || multipartFile.isEmpty()){
            fileName = "default_profile.png";
            user.setImage(fileName);
            String uploadDir = "./src/main/resources/static/images/user-image/" + savedUser.getEmail();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./static/images/default_profile.png")) {
                Path filePath = uploadPath.resolve(fileName);
                assert inputStream != null;
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException){
                throw new IOException("Could not save img " + fileName);
            }
        } else {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setImage(fileName);
            String uploadDir = "./src/main/resources/static/images/user-image/" + savedUser.getEmail();
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException){
                throw new IOException("Could not save img " + fileName);
            }
        }
        userService.addUser(user);
        return "index";
    }

    // Checks if email exists in DB
    private boolean checkIfUserExist(String email) {
        List<User> userList = userService.listUsers();
        for (User u : userList) {
            if (u.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}