package com.lastproject.ender.controller;

import com.lastproject.ender.entity.User;
import com.lastproject.ender.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "api")
public class UserApiController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/users")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userRepository.findAll();

    }

    @GetMapping(path = "/users/{id}")
    public @ResponseBody
    Optional<User> getUserById(@PathVariable("id") Long id) {
        return userRepository.findById(id);
    }

    @PostMapping(path = "/users", headers ="Accept=application/json")
    public @ResponseBody
    User addNewUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PatchMapping(path = "/users/{id}", headers ="Accept=application/json")
    public @ResponseBody
    User updateUser(@PathVariable("id") long id, @RequestBody User currentUser) {
        Optional<User> maybeUser = userRepository.findById(id);
        User user = null;

        if(maybeUser.isPresent()) {
            user = maybeUser.get();
        }

        if(currentUser.getEmail() != null) {
            user.setEmail(currentUser.getEmail());
        }
        if(currentUser.getFirstName() != null) {
            user.setFirstName(currentUser.getFirstName());
        }
        if(currentUser.getFamilyName() != null) {
            user.setFamilyName(currentUser.getFamilyName());
        }
        if(currentUser.getDescription() != null) {
            user.setDescription(currentUser.getDescription());
        }
        if(currentUser.getPseudo() != null) {
            user.setPseudo(currentUser.getPseudo());
        }

        return userRepository.save(user);
    }


    @DeleteMapping(path = "/users/{id}", headers ="Accept=application/json")
    public @ResponseBody
    ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
