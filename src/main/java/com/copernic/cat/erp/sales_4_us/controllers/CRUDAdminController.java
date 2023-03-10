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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class CRUDAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    //Llistem els usuaris administradors
    @GetMapping("/crud_admin")
    public String inici(Model model) {
        List<User> listUsers = userService.listAdmins();
        model.addAttribute("listUsers", listUsers);
        return ("crud_list_admin");
    }


     //Eliminem l'usuari seleccionat amb l'id
    @GetMapping("/delete/admin/{userId}")
    public String deleteAdmin(User user) {
        userService.deleteUser(user);
        return "redirect:/crud_admin";
    }

    //Formulari de creacio d'administrador
    @GetMapping("/formAdmin")
    public String createAdminForm(Model model) {
        model.addAttribute("user", new User());
        return "formAdmin";
    }

    //Guardem l'administrador creat a la BD
    @PostMapping("/saveAdmin")
    public String saveAdmin(
            @ModelAttribute(name = "user") User user,
            Errors errors,
            @RequestParam("fileImage") MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "formAdmin";
        }
        String fileName;
        User savedUser = userRepository.save(user);
        String uploadDir = "./src/main/resources/static/images/user-image/" + savedUser.getEmail();
        //Si no s'ha pujat cap imatge al formulari es guardara una per defecte en l'administrador
        if (multipartFile.getOriginalFilename() == null || multipartFile.isEmpty()) {
            fileName = "default_profile.png";
            user.setImage(fileName);
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("./static/images/default_profile.png")) {
                Path filePath = uploadPath.resolve(fileName);
                assert inputStream != null;
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                throw new IOException("Could not save img " + fileName);
            }
        } else {
            //Si s'ha pujat una imatge al formulari es guardara la imatge per al administrador creat
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setImage(fileName);
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                throw new IOException("Could not save img " + fileName);
            }
        }
        Utilities u = new Utilities();
        user.setPassword(u.encryptPass(user.getPassword()));
        user.setRol("admin");
        userService.addUser(user);
        return "redirect:/crud_admin";
    }


    //Guardem l'administrador creat a la BD
    @PostMapping("saveEditAdmin")
    public String saveEditAdmin(
            User user,
            Errors errors,
            @RequestParam("fileImage") MultipartFile multipartFile
    ) throws IOException {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "/crud_admin";
        }
        String fileName;
        //Si s'ha afegit una imatge al formulari es guardara en la base de dades
        if (multipartFile.getOriginalFilename() != null && !multipartFile.isEmpty()) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setImage(fileName);
            String uploadDir = "./src/main/resources/static/images/user-image/" + user.getEmail();
            Path uploadPath = Paths.get(uploadDir);
            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioException) {
                throw new IOException("Could not save img " + fileName);
            }
        }
        Utilities u = new Utilities();
        user.setPassword(u.encryptPass(user.getPassword()));
        userService.addUser(user);
        return "redirect:/crud_admin";
    }

    //Editem l'usuari administador seleccionat amb l'id
    @GetMapping("/edit/admin/{userId}")
    public String editAdmin(User user, Model model) {
        User u = userService.searchUser(user);
        model.addAttribute("user", u);
        return "formEditAdmin";
    }

}
