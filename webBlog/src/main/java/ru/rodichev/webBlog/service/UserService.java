package ru.rodichev.webBlog.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rodichev.webBlog.entity.Role;
import ru.rodichev.webBlog.entity.User;
import ru.rodichev.webBlog.repo.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.*;

/*** class for communicate with user repository and realize authenticates user ot site
 * @author
 */
@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    /***
     *
     * @param username from login page which is entered by the user
     * @return  new session and authenticates user ot site
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }

    /***
     *
     * @param userId user ID to search
     * @return user which have id from param OR if user with this param dose not exist, new user
     */
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    /***
     * add '%' after username and search users by mask
     * @param username substring of username which will be used as mask
     * @return list of users which username match the mask
     */
    public List<User> findUserByUsernameLike(String username) {
        List<User> usersFromDbLike = userRepository.findUsersByMask(username + "%");
        return usersFromDbLike;
    }

    /***
     *
     * @param role to search for users
     * @return List of users which have role from param
     */
    public List<User> findUserByRole(String role) {
        return userRepository.findUserByRole(role);
    }

    /***
     *
     * @return all users
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /***
     *  save user's fields in db, uses an encoder for secure password storage
     * @param user which will be saved id db
     * @return true if the save was successful, false if the user does not exist
     */
    public boolean saveNewUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setRole(Role.ROLE_USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /***
     *  delete user from db
     * @param userId user ID to be deleted
     * @return true if the delete was successful, false if the user does not exist
     */
    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public User findUserByUsername(String name){
        return userRepository.findUserByUsername(name);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

}
