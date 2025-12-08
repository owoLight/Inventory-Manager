package com.labinventory.backend.controller;

import com.labinventory.backend.model.User;
import com.labinventory.backend.model.User.Permissions;
import com.labinventory.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------- LOGIN ----------

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ---------- SIGNUP (REGISTER) ----------

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        // model can carry error messages if needed
        return "signup";
    }

    @PostMapping("/signup")
    public String handleSignup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model
    ) {
        // basic validation: passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "signup";
        }

        // username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already taken.");
            model.addAttribute("email", email);
            return "signup";
        }

        // email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "An account with this email already exists.");
            model.addAttribute("username", username);
            return "signup";
        }

        // create new user with NORMAL role
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPerms(Permissions.normal);

        userRepository.save(user);

        // redirect to login with a flag
        return "redirect:/login?registered";
    }
}
