package ru.rodichev.webBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.rodichev.webBlog.entity.User;
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

    @GetMapping("/login")
    public String login(Model model){return "login";}

//    @PostMapping("/login")
//    public String postLogin(@RequestParam String username, @RequestParam String password, Model model) {
//
//        if (userRepository.findByUsername(username) != null) {
//
//            User user = userRepository.findUserByUsername(username);
//            if (!bCryptPasswordEncoder.matches(password, bCryptPasswordEncoder.encode(user.getPassword()))) {
//                model.addAttribute("msg", "Incorrect username or password.");
//            } else {
//                UserService userService = new UserService();
//                userService.loadUserByUsername(username);
//                return "redirect: /";
//            }
//        } else {
//            model.addAttribute("msg", "Incorrect username or password.");
//        } return "login";
//    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (userRepository.findByUsername(username) != null) {
            User user = userRepository.findUserByUsername(username);
            if (bCryptPasswordEncoder.matches(password, bCryptPasswordEncoder.encode(user.getPassword()))) {
                model.addAttribute("passwordError", "Please enter correct password..");
                return "login";
            } else if (!bCryptPasswordEncoder.matches(password, bCryptPasswordEncoder.encode(user.getPassword()))) {
                UserService userService = new UserService();
                userService.loadUserByUsername(username);
                return "redirect: /";
            }

        } else model.addAttribute("loginError", "incorrect username");
        return "login";
    }



//        if (bCryptPasswordEncoder.matches(password, bCryptPasswordEncoder.encode(user.getPassword()))){
//            model.addAttribute("msg" , "Please enter correct previous password..");
//        } else if(!newPass.equals(confirmNewPass)){
//            model.addAttribute("msg", "Password mismatch..");
//        }   else {
//            user.setPassword(bCryptPasswordEncoder.encode(newPass));
//            userRepository.save(user);
//            model.addAttribute("msg", "password changed successfully");
//        }
//        return "changePass";
//    }




    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect: /";
    }
}
