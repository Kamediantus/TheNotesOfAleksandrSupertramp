package ru.rodichev.webBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rodichev.webBlog.entity.Comment;
import ru.rodichev.webBlog.repo.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Iterable<Comment> reverseFindById(Long id){
        return commentRepository.reverseFindById(id);
    }

    public void saveComment(Comment comment){
        commentRepository.save(comment);
    }
}
