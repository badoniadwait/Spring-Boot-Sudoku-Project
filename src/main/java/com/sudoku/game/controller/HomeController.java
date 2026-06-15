package com.sudoku.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sudoku.game.repo.UserRepo;

@Controller
public class HomeController {

    @Autowired
    private UserRepo repo;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {


        model.addAttribute("users", repo.findAll(Sort.by(Sort.Direction.DESC, "wins")));
        return "leaderboard";
    }
}