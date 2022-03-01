package ru.rodichev.webBlog.controller;

import java.util.*;
import java.util.stream.*;

import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.*;
import ru.rodichev.webBlog.session.*;

@Controller
public class ProductsController {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductLotRepository productLotRepository;

    @GetMapping("/listAllProducts")
    public ResponseEntity<List<Product>> getAllProducts(Model model) {
        return new ResponseEntity<>(productsRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/listAllProductsWithPersonalPrice")
    public ResponseEntity<List<Product>> getAllProductsWithPersonalPrice(@RequestBody String rawKey, Model model) {
        JSONObject json = new JSONObject(rawKey);
        String sessionKey = json.getString("sessionKey");
        List<Product> products = productsRepository.findAll();
        List<Order> ordersByUser = orderRepository.findAll();
        Date date = new Date();
        products.forEach(product -> {
            product.setPersonalDiscount(getPersonalPrice(sessionKey, product.getId(), ordersByUser, date));
        });
        return new ResponseEntity<>(products, HttpStatus.OK);
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

    @GetMapping("/listProductsByStore{id}")
    public ResponseEntity<List<Product>> getProductsByStoreId(@PathVariable(value="id") Long storeId) {
        return new ResponseEntity<>(productsRepository.getProductsByStoreId(storeId), HttpStatus.OK);
    }

    private Double getPersonalPrice(String sessionKey, Long productId, List<Order> orders, Date date) {
        Double personalDiscount = 0d;
        CSession session = sessionRepository.getSessionByKey(sessionKey);
        List<Order> ordersByProduct = orders.stream().filter(order -> order.getConsumerId() == session.getUserId()
                && order.getProductId() == productId).collect(Collectors.toList());
        List<Order> freshDeal = ordersByProduct.stream().filter(pr -> !pr.getBuyInWarningSpoilPeriod()).collect(Collectors.toList());
        List<Order> notFreshDeal = ordersByProduct.stream().filter(pr -> pr.getBuyInWarningSpoilPeriod()).collect(Collectors.toList());
        ProductLot lot = productLotRepository.getProductLotsByProductId(productId);
        // если нет активной партии - скидки нет, т.к. нам неизвестна дата производства
        if(lot == null) {
            return personalDiscount;
        }
        if (!Order.isFresh(lot, date) &&
                notFreshDeal.size() < freshDeal.size() ) {
            personalDiscount = 25d;
        }
        return personalDiscount;
    }


}
