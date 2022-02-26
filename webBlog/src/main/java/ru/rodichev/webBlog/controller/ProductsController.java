package ru.rodichev.webBlog.controller;

import java.util.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.*;

@Controller
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/listAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(Model model) {
        return new ResponseEntity<>(productsRepository.findAll(), HttpStatus.OK);
    }
}
