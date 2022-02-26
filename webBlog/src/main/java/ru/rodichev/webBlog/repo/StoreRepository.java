package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.*;
import ru.rodichev.webBlog.entity.*;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
