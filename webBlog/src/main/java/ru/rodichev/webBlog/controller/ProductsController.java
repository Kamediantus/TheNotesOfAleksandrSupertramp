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

    @PostMapping("/addProduct")
    public ResponseEntity<List<Product>> addProduct(@RequestBody String rawProduct, Model model) {
        JSONObject json = new JSONObject(rawProduct);
        Product product = new Product();
        product.setTitle(json.getString("title"));
        product.setDescription(json.getString("description"));
        product.setPrice(json.getDouble("price"));
        product.setShelLife(json.getInt("selfLife"));
        product.setStoreId(json.getLong("storeId"));
        if (productsRepository.save(product) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
