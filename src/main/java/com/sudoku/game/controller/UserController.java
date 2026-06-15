package com.sudoku.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sudoku.game.model.User;
import com.sudoku.game.repo.UserRepo;

@Controller
public class UserController {

    @Autowired
    private UserRepo repo;

    private Boolean signedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null
            && auth.isAuthenticated()
            && !auth.getPrincipal().equals("anonymousUser");
    }

    @GetMapping("/login")
    public String login() {

        
        System.out.println("login called");
        if(signedIn()) return "home";
        System.out.println("login success");

        return "login";
    }

    @GetMapping("/signup")
    public String SignUp() {

        if(signedIn()) return "home";

        return "signup";
    }

    @PostMapping("/signup")
    public String register(User user) {

        if(repo.findByUsername(user.getUsername()).isPresent()) {
            return "redirect:/signup?error=usernameexists";
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        
        user.setPassword(
            encoder.encode(user.getPassword())
        );
        
        user.setWins(0);
        
        repo.save(user);

        return "redirect:/login";
 
    }

    public void updateWins() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = repo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
                        
        user.updateWins();

        repo.save(user);

    }
    
}
