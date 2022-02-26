package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rodichev.webBlog.entity.*;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    /***
     * find users with role from param. Need for administration search
     * @param role to search for users
     * @return List of users which have role from param
     */
    @Query(value = "SELECT * FROM t_user t where t.role = :role", nativeQuery = true)
    List<User> findUserByRole(@Param("role") String role);

    /***
     * find user with id from param. Need for administration search
     * @param id to search for users
     * @return user which have id from param
     */
    @Query(value = "SELECT * FROM t_user t where t.id = :id", nativeQuery = true)
    User findUserById(@Param("id") Long id);

    /***
     * find users with username that meets mask from param. Need for administration search
     * @param username to search for users. Must have structure: username + %
     * @return  List of users which have username that meets mask from param.
     */
    @Query(value = "SELECT * FROM t_user t where t.username like :username", nativeQuery = true)
    List<User> findUsersByMask(@Param("username") String username);

    /***
     * find users with username that equals username from param. Need for administration search
     * @param username to search for users. Just username
     * @return  user which have username that equals username from param.
     */
    @Query(value = "SELECT * FROM users t where t.username = :username", nativeQuery = true)
    User findUserByUsername(@Param("username") String username);


}
