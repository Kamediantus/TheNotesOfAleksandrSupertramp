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
        productLot.setStoreId(json.getLong("storeId"));
        productLot.setDateOfProduction(new Date(json.getLong("produceDate")));
        productLot.setCount(json.getInt("count"));
        Product product = productsRepository.getOne(productLot.getProductId());
        productLot.setShelLife(product.getShelLife());
        productLotRepository.findAll().forEach(lot -> {
            if (lot.getProductId() == product.getId()) {
                lot.setActive(false);
                productLotRepository.save(lot);
            }
        });
        productLot.setActive(true);
        if (productLotRepository.save(productLot) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/listAllProductLots")
    public ResponseEntity<List<ProductLot>> getAllProductLots(Model model) {
        return new ResponseEntity<>(productLotRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/listActiveProductLots")
    public ResponseEntity<String> geActiveProductLots(Model model) {
        JSONArray result = new JSONArray();
        Date date = new Date();
        productsRepository.findAll().stream().forEach(product -> {
            ProductLot lot = productLotRepository.getProductLotsByProductId(product.getId());
            if (lot != null) {
                JSONObject jsonProduct = new JSONObject(lot);
                jsonProduct.put("fresh", Order.isFresh(lot, date));
                result.put(jsonProduct);
            }
        });
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }
}
