package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rodichev.webBlog.entity.BlockOfSite;

public interface BlockRepository extends JpaRepository<BlockOfSite, Long> {

    /***
     * get text to display info at AboutUs page
     * @param id id of block. Now site has only one block
     * @return  block that has id == id from param
     */
    @Query(value = "SELECT full_text from t_block where id = :id", nativeQuery = true)
    String getTextById(@Param("id") Long id);

    /***
     * get all Block ti edit it from admin panel
     * @param id id of Block that will be editing by admin
     * @return Block that has id == id from param
     */
    @Query(value = "SELECT * FROM T_block WHERE id = :id", nativeQuery = true)
    BlockOfSite getBlockById(@Param("id") Long id);

    @Query(value = "SELECT * FROM T_block WHERE name = :name", nativeQuery = true)
    BlockOfSite getBlockByName(@Param("name") String name);

    @Query(value = "SELECT full_text from t_block where name = :name", nativeQuery = true)
    String getTextByName(@Param("name") String name);
}
