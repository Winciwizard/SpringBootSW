package com.lastproject.ender.controller;

import com.lastproject.ender.entity.User;
import com.lastproject.ender.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserWebController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "users-list")
    String getAllUsers(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users-list";
    }

    @GetMapping(path = "show-user/{id}")
    String getUser(@PathVariable("id") Long id, Model model) {
        Optional<User> maybeUser = userRepository.findById(id);
        User user = maybeUser.get();
        model.addAttribute("user", user);
        return "show-user";
    }

    @GetMapping(path = "add-user")
    public String addUserForm(Model model) {
        model.addAttribute("add-user", new User());
        return "add-user";
    }

    @PostMapping(path = "add-user")
    public String addUserSubmit(@ModelAttribute User user) {
        userRepository.save(user);
        return "show-user";
    }

    @GetMapping(path = "delete-user/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/users-list";
    }

    @GetMapping(path = "update-user/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> maybeUser = userRepository.findById(id);
        User user = maybeUser.get();
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping(path = "update-user/{id}")
    public String updateUserSubmit(@PathVariable("id") Long id, @ModelAttribute User user) {
        Optional<User> maybeUser = userRepository.findById(id);
        User updatedUser = maybeUser.get();

        updatedUser.setEmail(user.getEmail());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setFamilyName(user.getFamilyName());
        updatedUser.setDescription(user.getDescription());
        updatedUser.setPseudo(user.getPseudo());
        updatedUser.setPssword(user.getPssword());

        userRepository.save(updatedUser);
        return "show-user";
    }

}
