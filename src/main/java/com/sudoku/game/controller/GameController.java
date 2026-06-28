package com.sudoku.game.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sudoku.game.service.Game;

import jakarta.servlet.http.HttpSession;

@Controller
public class GameController {

    @Autowired
    private Game sudoku;

    private List<List<Integer>> board, puzzle;
    
    @PostMapping("/play")
    public String play() 
    {
        board = null;
        puzzle = null;
        sudoku.setOver();
        return "redirect:/game";
    }

    @GetMapping("/game")
    public String game(Model model) {

        
        if(board == null) {
            board = sudoku.getCompleteBoard();
            puzzle = sudoku.getPuzzle();
        }

        model.addAttribute("puzzle", puzzle);
        model.addAttribute("time", sudoku.getTime());
        model.addAttribute("elapsed", System.currentTimeMillis()/1000 - sudoku.getTime());
        model.addAttribute("life", sudoku.getLife());
        model.addAttribute("over", sudoku.checkOver());
        return "game";

    }

    @PostMapping("/game/move")
    public String move(@RequestParam Map<String, String> moves) {

      

        for(Map.Entry<String, String> move : moves.entrySet()){
            String key = move.getKey();
            String val = move.getValue();
            
            if(val.isEmpty() || key.equals("_csrf")) continue;

            String parts[] = key.split("-");
            int i = Integer.parseInt(parts[0]);
            int j = Integer.parseInt(parts[1]);
            int value = Integer.parseInt(val);

            if(value == board.get(i).get(j)) {
                puzzle.get(i).set(j, value);
            }
            else {
                sudoku.deductLife();
            }

            
        }
        if(sudoku.checkOver() || sudoku.getLife() == 0) {
            return "redirect:/game/status";
        }
        
        return "redirect:/game";
    }

    @GetMapping("/game/status") 
    public String status(Model model, HttpSession session) {

        model.addAttribute("life", sudoku.getLife());
        model.addAttribute("time", sudoku.getFinishTime());

        if(sudoku.getLife() == 0) {
            sudoku.resetBoard();
            return "status";
        }
        
        if(!sudoku.checkOver()) {
            return "redirect:/game";
        }
        else{
            sudoku.resetBoard();
        }
        return "status";
    }

}