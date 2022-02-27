package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.*;
import ru.rodichev.webBlog.entity.*;

public interface ProductLotRepository extends JpaRepository<ProductLot, Long> {
}
