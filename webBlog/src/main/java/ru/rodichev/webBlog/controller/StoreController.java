package ru.rodichev.webBlog.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.*;

@Controller
public class StoreController {
    @Autowired
    private StoreRepository storeRepository;

    @GetMapping("/store{id}")
    public ResponseEntity<Store> getStoreNameAndDiscount(@PathVariable(value="id") Long storeId) {
        return new ResponseEntity<>(storeRepository.findById(storeId).get(), HttpStatus.OK);
    }

    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStores() {
        return new ResponseEntity<>(storeRepository.findAll(), HttpStatus.OK);
    }
}
