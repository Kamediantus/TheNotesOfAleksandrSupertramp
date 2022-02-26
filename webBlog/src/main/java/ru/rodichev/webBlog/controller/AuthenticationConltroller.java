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
import ru.rodichev.webBlog.repo.*;
import ru.rodichev.webBlog.service.UserService;
import ru.rodichev.webBlog.session.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthenticationConltroller {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

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
                CSession session = new CSession(user);
                sessionRepository.save(session);
                return new ResponseEntity<String>(getJsonSession(user, session), HttpStatus.OK);
            }

        } else {
            return new ResponseEntity<String>("User was not found. Please check your email.", HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/singUp")
    public ResponseEntity postSingUp(@RequestBody String creds, Model model) {
        JSONObject usersCreds = new JSONObject(creds);
        String username = usersCreds.getString("username");
        String password = usersCreds.getString("password");
        String name = usersCreds.getString("name");
        String surname = usersCreds.getString("surname");
        User user = new User(username, password, name, surname, userService.getCardNumber(), Role.ROLE_USER);
        User exsistUser = userRepository.findByUsername(username);
        if (exsistUser == null) {
            userRepository.save(user);
                return new ResponseEntity<String>("Successful registration", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("User with the same email was found. Please check your email or try to sing in.", HttpStatus.CONFLICT);
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

    private String getJsonSession(User user, CSession session) {
        JSONObject json = new JSONObject();
        json.put("username", user.getUsername());
        json.put("role", user.getRole());
        json.put("sessionKey", session.getSessionKey());
        return json.toString();
    }
}
