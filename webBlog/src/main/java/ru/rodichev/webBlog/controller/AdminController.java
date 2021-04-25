package ru.rodichev.webBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.BlockOfSite;
import ru.rodichev.webBlog.entity.Contact;
import ru.rodichev.webBlog.entity.Role;
import ru.rodichev.webBlog.entity.User;
import ru.rodichev.webBlog.repo.BlockRepository;
import ru.rodichev.webBlog.repo.ContactRepository;
import ru.rodichev.webBlog.repo.UserRepository;
import ru.rodichev.webBlog.service.BlockService;
import ru.rodichev.webBlog.service.UserService;

/**
 *
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlockService blockService;
//    @Autowired
//    private BlockRepository blockRepository;
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("user", userService.allUsers());
        return "admin/admin";
    }

    @PostMapping("/admin")
    public String searchUsers(@RequestParam(required = false) Long id, @RequestParam(required = false) String username, @RequestParam(required = false) String role, Model model) {
        if (id != null) {
            if (userService.findUserById(id).getUsername() == null) {
                model.addAttribute("message", "User with id: " + id + " wasn't found");
            } else
                model.addAttribute("user", userService.findUserById(id));
        } else if (username != "") {
            if (userService.findUserByUsernameLike(username).size() == 0) {
                model.addAttribute("message", "User with username mask: '" + username + "%' wasn't found");
            }
            model.addAttribute("user", userService.findUserByUsernameLike(username));
        } else if (!role.equals("empty")) {
            model.addAttribute("user", userService.findUserByRole(role));
        } else model.addAttribute("message", "Please choose at least one parameter for search");
        return "admin/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String gtUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/userEdit";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @RequestParam String role, Model model) {
        User user = userService.findUserById(id);
        user.setRole(Role.valueOf(role));
        userRepository.save(user);
        model.addAttribute("user", userService.findUserById(id));
        return "admin/userEdit";
    }

    @PostMapping("/admin/edit/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        String msg;
        // disable to delete user with ROLE_ADMIN
        if (userRepository.findUserById(id).getRole() != Role.ROLE_ADMIN) {
            System.out.println(userRepository.findUserById(id).getRole());
            if (userService.deleteUser(id)) {
                msg = "User was deleted successfully. id: " + id;
                model.addAttribute("message", msg);

            } else {
                msg = "User wasn't deleted successfully. id: " + id;
            }
        } else msg = "if you want to delete admin, please use DB";

        model.addAttribute("message", msg);
        return "admin/userDelete";
    }

    @GetMapping("/admin/edit_site")
    public String editWebsitePage(Model model) {
        Iterable<BlockOfSite> mainInfos = blockService.findAll();
        model.addAttribute("blocks", mainInfos);
        return "admin/editSite";
    }

    @GetMapping("/admin/new_block")
    public String newBlock(Model model) {
        return "admin/newBlock";
    }

    @PostMapping("/admin/new_block")
    public String createNewBlock(@RequestParam String newText, @RequestParam String name, Model model) {
        BlockOfSite block = new BlockOfSite();
        block.setFullText(newText);
        block.setName(name);
        block.setId(1L);
        blockService.saveBlock(block);
        return "admin/newBlock";
    }

    @GetMapping("/admin/edit_site/{id}")
    public String editWebsitePage(@PathVariable("id") Long id, Model model) {
        BlockOfSite blockOfSite = blockService.getBlockById(id);
        model.addAttribute("block", blockOfSite);
        return "admin/editBlock";
    }

    @PostMapping("/admin/edit_site/{id}")
    public String saveChanges(@PathVariable("id") Long id, @RequestParam(required = false) String rollback, String newText, Model model) {
        BlockOfSite block = blockService.getBlockById(id);
        if (rollback != null) {
            if (block.rollback()) {
                blockService.saveBlock(block);
            } else model.addAttribute("msg", "rollback Error");
        } else if (newText != "") {
            if (block.setNewText(newText)) {
                blockService.saveBlock(block);
            } else
                model.addAttribute("msg", "new text Error. Please check that new text is different from the previous version");
        } else model.addAttribute("msg", "there are no changes");
        model.addAttribute("block", block);
        return "admin/editBlock";
    }

    @GetMapping("/admin/edit_contact")
    public String editContactsPage(Model model) {
        Iterable<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "admin/listOfContacts";
    }

    @GetMapping("/admin/edit_contact/{id}")
    public String editContact(@PathVariable("id") Long id, Model model) {
        Contact contact = contactRepository.getContactById(id);
        model.addAttribute("contact", contact);
        return "admin/editContact";
    }

    @PostMapping("/admin/edit_contact/{id}")
    public String saveChanges(@PathVariable("id") Long id, String name, String visibleText, String link, String isVisible, String description, Model model) {
        Contact contact = contactRepository.getContactById(id);
        contact.update(name, link, visibleText, description, isVisible);
        contactRepository.save(contact);
        model.addAttribute("contact", contact);
        return "admin/editContact";
    }

    @GetMapping("/admin/new_contact")
    public String toNewContactPage(Model model) {
        return "admin/newContact";
    }

    @PostMapping("admin/new_contact")
    public String createNewContact(String name, String visibleText, String link, String isVisible, @RequestParam(required = false) String description, Model model) {
        Contact contact = new Contact(name, link, visibleText, description, isVisible);
        contactRepository.save(contact);
        model.addAttribute("contact", contact);
        return "redirect:/admin/edit_contact/" + contact.getId();
    }

    @PostMapping("/admin/delete_contact/{id}")
    public String deleteContact(@PathVariable("id") Long id, Model model) {
        contactRepository.delete(contactRepository.findById(id).orElseThrow());
        return "redirect:/admin/edit_contact";
    }

}


