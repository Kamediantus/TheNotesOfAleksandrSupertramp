package ru.rodichev.webBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import ru.rodichev.webBlog.entity.Contact;
import ru.rodichev.webBlog.repo.ContactRepository;

import java.net.ContentHandler;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact getContactById(Long id){
        return contactRepository.getContactById(id);
    }

    public Iterable<Contact> getVisibleContacts(){
        return contactRepository.getVisibleContacts();
    }

    public Iterable<Contact> findAll(){
        return contactRepository.findAll();
    }

    public void saveContact(Contact contact){
        contactRepository.save(contact);
    }

    public void delete(Contact contact){
        contactRepository.delete(contact);
    }

    public Contact findById(Long id){
        return contactRepository.findById(id).orElseThrow();
    }

}
