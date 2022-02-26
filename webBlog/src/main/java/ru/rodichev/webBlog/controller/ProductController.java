package ru.rodichev.webBlog.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.*;

@Controller
public class ProductController {
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/store{id}")
    public ResponseEntity<Store> getStoreNameAndDiscount(@PathVariable(value="id") Long storeId) {
        return new ResponseEntity<>(storeRepository.findById(storeId).get(), HttpStatus.OK);
    }
}
