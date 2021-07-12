package com.ato.bananaleaf.controller;

import com.ato.bananaleaf.dto.UserRegistrationDto;
import com.ato.bananaleaf.entity.User;
import com.ato.bananaleaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegisterForm() {
        return "auth/register";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {
        User existing = userService.findByEmail(userDto.getEmail());

        if (existing != null) {
            result.rejectValue("email", null, "Email address already taken");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        userService.save(userDto);

        return "redirect:/register?success";
    }
}
