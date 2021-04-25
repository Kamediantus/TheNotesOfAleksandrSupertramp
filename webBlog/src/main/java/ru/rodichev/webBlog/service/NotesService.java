package ru.rodichev.webBlog.service;


import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.logic.SearchFromRepo;
import ru.rodichev.webBlog.repo.NotesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private NotesRepository notesRepository;

    public String getTagsById(Long id){
        return notesRepository.getTagsById(id);
    }

    public Note getNoteById(Long id){
        return notesRepository.getById(id);
    }

    public List<Note> reverseFindByText(String text){
        return notesRepository.reverseFindByText(SearchFromRepo.toLike(text));
    }

    public List<Note> reverseFindByTag(String tag){
        return  notesRepository.reverseFindByTag(SearchFromRepo.toLike(tag));
    }

    public  Iterable getOnlyUnchecked(){
        return notesRepository.getOnlyUnchecked();
    }

    public Iterable<Note> getOnlyUnfixed(){
        return notesRepository.getOnlyUnfixed();
    }

    public  Iterable<Note> getOnlyChecked(){
        return notesRepository.getOnlyChecked();
    }

    public void saveNote(Note note){
        notesRepository.save(note);
    }

    public  boolean existsById(Long id){
        return notesRepository.existsById(id);
    }

    public Optional<Note> findById(Long id){
        return notesRepository.findById(id);
    }

    public void delete(Note note){
        notesRepository.delete(note);
    }

}
