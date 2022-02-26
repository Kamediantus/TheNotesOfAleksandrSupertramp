package ru.rodichev.webBlog.controller;


import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import ru.rodichev.webBlog.entity.UserBranch;
import ru.rodichev.webBlog.repo.*;
import ru.rodichev.webBlog.service.*;

@Controller
public class MockController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping ("/ss")
    public ResponseEntity postLogin(@RequestBody String creds, Model model) {
        JSONObject usersCreds = new JSONObject(creds);
        String email = usersCreds.getString("email");
        String password = usersCreds.getString("password");
        UserBranch user = null;
        try {
//            user = userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<String>("User was not found. Please check your email.", HttpStatus.CONFLICT);
        }
        if (password.equals(user.getPassword())) {
            return new ResponseEntity<String>("Welcome", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Bad credentials. Check your password.", HttpStatus.CONFLICT);
    }

    @GetMapping ("/test")
    public String test(Model model) {
        int s = 2;
        return "loh";
    }
}
