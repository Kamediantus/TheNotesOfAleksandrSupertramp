package ru.rodichev.webBlog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rodichev.webBlog.entity.BlockOfSite;
import ru.rodichev.webBlog.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    /***
     * get contact block that have id from param
     * @param id to search block of Contact
     * @return  contact that has id from param
     */
    @Query(value = "SELECT * FROM t_contact WHERE id = :id", nativeQuery = true)
    Contact getContactById(@Param("id") Long id);

    /***
     * get Contacts List that have true flag in Visible field
     * @return List of Contacts that have true flag in Visible field
     */
    @Query(value = "SELECT * FROM t_contact WHERE is_visible = 'true'", nativeQuery = true)
    Iterable<Contact> getVisibleContacts();
}
