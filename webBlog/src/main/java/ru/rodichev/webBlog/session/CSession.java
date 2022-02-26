package ru.rodichev.webBlog.session;

import java.security.*;
import java.util.*;

import javax.crypto.*;
import javax.persistence.*;

import org.json.*;
import ru.rodichev.webBlog.entity.*;
import ru.rodichev.webBlog.service.*;

@Table(name = "t_session")
@Entity
public class CSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String role;
    private String sessionKey;
    private Date creationDate;
    private Long cardNumber;
    private Long userId;

    public CSession(User user) {
        this.userName = user.getUsername();
        this.role = user.getRole().toString();
        this.sessionKey = generateKey(user);
        this.creationDate = new Date();
//        this.cardNumber = user.get();
        this.sessionKey = generateKey(user);
    }

    public CSession() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

//    public User getUser() {
//        UserService service = new UserService();
////        service.findUserById(get)
//    }

    public String generateKey(User user) {
        String key = null;
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            StringBuilder builder = new StringBuilder();
            builder.append((new Date()).getTime());
            builder.append(keyGen.generateKey().getEncoded().toString().substring(2));
            key = builder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

    public String getJsonSessionKey() {
        JSONObject json = new JSONObject();
        json.put("sessionKey", this.sessionKey);
        return json.toString();
    }
}
