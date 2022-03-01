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
public class OrderController {
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductLotRepository productLotRepository;
    @Autowired
    ProductLotRepository lotRepository;

    @PostMapping("/addOrder")
    public ResponseEntity postSingUp(@RequestBody String rawJson, Model model) {
        JSONObject json = new JSONObject(rawJson);
        String sessionKey = json.getString("sessionKey");
        CSession session = sessionRepository.getSessionByKey(sessionKey);
        Long productId = json.getLong("productId");
        ProductLot lot = lotRepository.getProductLotsByProductId(productId);
        Order order = new Order(session.getCardNumber(), session.getUserId(), productId,
                json.getLong("storeId"), new Date(), json.getInt("count"), productsRepository.getOne(productId), lot);
        if (orderRepository.save(order) != null) {
            return new ResponseEntity<String>("OK", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("NOT OK", HttpStatus.CONFLICT);
        }
    }

    private ProductLot getLastLot(Long productId) {
        List<ProductLot> lots = productLotRepository.findAll().stream().filter(l -> l.getProductId() == productId).collect(Collectors.toList());
        lots.sort(Comparator.comparing(ProductLot::getDateOfProduction));
        return lots.stream().findFirst().get();
    }
}
