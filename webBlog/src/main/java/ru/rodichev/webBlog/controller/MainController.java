package ru.rodichev.webBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.rodichev.webBlog.entity.Contact;
import ru.rodichev.webBlog.entity.Note;
import ru.rodichev.webBlog.repo.BlockRepository;
import ru.rodichev.webBlog.repo.ContactRepository;
import ru.rodichev.webBlog.repo.NotesRepository;
import ru.rodichev.webBlog.repo.UserRepository;
import ru.rodichev.webBlog.service.BlockService;

@Controller
public class MainController {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private BlockService blockService;
//    @Autowired
//    private BlockRepository blockRepository;

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/")
    public String homepage(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            String currentUsername = auth.getName();
//            model.addAttribute("user", currentUsername);
//        }
//        Iterable<Note> notes = notesRepository.getOnlyChecked();
//        model.addAttribute("notes", notes);
        return "index";
    }

    @GetMapping("/aboutUs")
    public String aboutMe(Model model) {
        Iterable<Contact> contacts = contactRepository.getVisibleContacts();
        model.addAttribute("contacts", contacts);
        String aboutMeInfo = blockService.getTextByName("about us");
        model.addAttribute("text", aboutMeInfo);
        return "aboutMe";
    }
}
