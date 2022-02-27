package ru.rodichev.webBlog.repo;

import java.util.*;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import ru.rodichev.webBlog.entity.*;

public interface ProductsRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products t where t.store_id = :storeId", nativeQuery = true)
    List<Product> getProductsByStoreId(@Param("storeId") Long storeId);

}
