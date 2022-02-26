package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.session.*;

public interface SessionRepository extends JpaRepository<CSession, Long> {
}
