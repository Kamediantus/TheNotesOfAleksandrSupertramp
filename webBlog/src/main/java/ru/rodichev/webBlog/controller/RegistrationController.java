package ru.rodichev.webBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.UserRepository;
import ru.rodichev.webBlog.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/registration")
    public String registration(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String passwordConfirm, Model model) {
        if (!password.equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Password mismatch.");
            return "registration";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", "A user with the same name already exists.");
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/changePass")
    public String goToChangePass(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        return "changePass";
    }

    @PostMapping("/changePass")
    public String updatePass(@RequestParam String previousPass, @RequestParam String newPass, @RequestParam String confirmNewPass, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(auth.getName());
        model.addAttribute("username", auth.getName());
        if (bCryptPasswordEncoder.matches(previousPass, bCryptPasswordEncoder.encode(user.getPassword()))) {
            model.addAttribute("msg", "Please enter correct previous password..");
        } else if (!newPass.equals(confirmNewPass)) {
            model.addAttribute("msg", "Password mismatch..");
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(newPass));
            userRepository.save(user);
            model.addAttribute("msg", "password changed successfully");
        }
        return "changePass";
    }
}