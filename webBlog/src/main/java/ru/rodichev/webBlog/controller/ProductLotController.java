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
public class ProductLotController {
    @Autowired
    ProductLotRepository productLotRepository;
    @Autowired
    ProductsRepository productsRepository;

    @PostMapping("/addProductLot")
    public ResponseEntity<List<Product>> addProductLot(@RequestBody String rawProductLot, Model model) {
        JSONObject json = new JSONObject(rawProductLot);
        ProductLot productLot = new ProductLot();
        productLot.setProductId(json.getLong("productId"));
        productLot.setStoreIdId(json.getLong("storeId"));
        productLot.setDateOfProduction(new Date(json.getLong("produceDate")));
        Product product = productsRepository.getOne(productLot.getProductId());
        productLot.setShelLife(product.getShelLife());
        if (productLotRepository.save(productLot) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
