package ru.rodichev.webBlog.controller;

import antlr.*;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.repo.UserRepository;
import ru.rodichev.webBlog.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationConltroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }

//    @PostMapping("/login")
//    public ResponseEntity postLogin(@RequestParam String username, @RequestParam String password, Model model) {
////        JSONObject usersCreds = new JSONObject(creds);
////        String email = usersCreds.getString("email");
////        String password = usersCreds.getString("password");
//        User user = null;
//        try {
//            user = userRepository.loadUserByUsername(email);
//        } catch (UsernameNotFoundException e) {
//            return new ResponseEntity<String>("User was not found. Please check your email.", HttpStatus.CONFLICT);
//        }
//        if (password.equals(user.getPassword())) {
//            return new ResponseEntity<String>("Welcome", HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("Bad credentials. Check your password.", HttpStatus.CONFLICT);
//    }

    @PostMapping("/singIn")
    public ResponseEntity postLogin(@RequestBody String creds, Model model) {
        JSONObject usersCreds = new JSONObject(creds);
        String username = usersCreds.getString("username");
        String password = usersCreds.getString("password");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            if (!StringUtils.equals(user.getPassword(), password)) {
                return new ResponseEntity<String>("Bad credentials. Check your password.", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<String>("Welcome", HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<String>("User was not found. Please check your email.", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect: /";
    }
}
