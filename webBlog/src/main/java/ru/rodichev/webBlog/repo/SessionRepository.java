package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.transaction.annotation.*;
import ru.rodichev.webBlog.session.*;

public interface SessionRepository extends JpaRepository<CSession, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM t_session where user_name like :username", nativeQuery = true)
    void deleteByUsername(@Param("username") String username);

    @Query(value = "SELECT * FROM t_session t where t.session_key like :key", nativeQuery = true)
    CSession getSessionByKey(@Param("key") String key);
}
