package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.*;
import ru.rodichev.webBlog.entity.*;

public interface ProductsRepository extends JpaRepository<Product, Long> {
}
