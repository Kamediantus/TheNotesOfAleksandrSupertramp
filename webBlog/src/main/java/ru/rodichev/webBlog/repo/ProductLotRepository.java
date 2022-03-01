package ru.rodichev.webBlog.repo;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import ru.rodichev.webBlog.entity.*;

public interface ProductLotRepository extends JpaRepository<ProductLot, Long> {
    @Query(value = "SELECT * FROM t_lot t WHERE t.product_id =:productId AND t.active = true", nativeQuery = true)
    ProductLot getProductLotsByProductId(@Param("productId") Long productId);
}
