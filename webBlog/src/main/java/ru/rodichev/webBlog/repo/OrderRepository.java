package ru.rodichev.webBlog.repo;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.session.*;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders t where card_number = :cardNumber AND product_id = :productId", nativeQuery = true)
    List<Order> getOrdersByCardNumberAndProductId(@Param("cardNumber") Long cardNumber, @Param("productId") Long productId);
}
