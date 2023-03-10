package com.copernic.cat.erp.sales_4_us.controllers;

import com.copernic.cat.erp.sales_4_us.models.Category;
import com.copernic.cat.erp.sales_4_us.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CRUDCategoryController {

    @Autowired
    CategoryService categoryService;

    //Llistem totes les categories
    @GetMapping("/crud_category")
    public String inici(Model model){
        List<Category> listCategories = categoryService.listCategories();
        model.addAttribute("listCategories", listCategories);
        return ("crud_list_category");
    }


    //Eliminem la categoria seleccionada
    @GetMapping("/delete/category{id}")
    public String deleteCategory(Category category){
        categoryService.deleteCategory(category);
        return "redirect:/crud_category";
    }

    //Formulari per crear la categoria
    @GetMapping("/formCategory")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "formCategory";
    }

    //Guardem la categoria creada a la base de dades
    @PostMapping("/saveCategory")
    public String saveCategory(Category category, Errors errors) {
        if (errors.hasErrors()) {
            System.out.println(errors);
            return "formCategory";
        }
        categoryService.addCategory(category);
        return "redirect:/crud_category";
    }

    //Editem la categoria seleccionada
    @GetMapping("/edit/category{id}")
    public String editCategory(Category category, Model model) {
        Category c = categoryService.findCategory(category);
        model.addAttribute("category", c);
        return "formCategory";
    }
}
