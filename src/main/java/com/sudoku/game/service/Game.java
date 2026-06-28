package com.sudoku.game.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudoku.game.controller.UserController;


@Service
public class Game {

    @Autowired
    private UserController controller;

    private  List<List<Integer>> finalBoard = new ArrayList<>();
    private  List<List<Integer>> puzzle = new ArrayList<>();
    private Random random = new Random();

    private List<Integer> shuffIntegers = new ArrayList<>();
    
    private long time;
    private long finishTime;
    private int life;
    private Boolean over;

    public List<List<Integer>> getPuzzle() {
        if(puzzle.isEmpty()) createPuzzle();
        return puzzle;
    }

    public Boolean checkOver() {
        if(over) return over;

        for (List<Integer> list : puzzle) {
            for(int val : list) {
                if(val == 0) return false;
            }
        }
        finishTime = System.currentTimeMillis()/1000 - time;
        
        updateWinsDB();

        return over = true;
    }
    
    private void updateWinsDB() {
        controller.updateWins();
    }

    private void createPuzzle() {
        time = System.currentTimeMillis()/1000;
        life = 3;
        finishTime = 0;
        over = false;
        puzzle = copyBoard(finalBoard);

        for(int i = 0;i < 60; i++) {
            int a = random.nextInt(1,9);
            int b = random.nextInt(1,9);
            puzzle.get(a).set(b, 0);

        }
    }
    
    private List<List<Integer>> copyBoard(List<List<Integer>> board){
        List<List<Integer>> newBoard = new ArrayList<>();
        
        for(List<Integer> row : board){
            newBoard.add(new ArrayList<>(row));
        }

        return newBoard;
    }

    public List<List<Integer>> getCompleteBoard() {
        int cnt = 0;
        List <List<Integer>> emptyBoard = new ArrayList<>();
        for(int i = 0;i<9;i++) {
            List<Integer> temp = new ArrayList<>();
            for(int j = 0;j < 9; j++) {
                temp.add(0);
            }
            emptyBoard.add(temp);
        }
        
        
    
        List<List<Integer>> completeBoard = generateBoard(emptyBoard,0,0, cnt);
        return completeBoard;
        
    }
    
    private List<List<Integer>> generateBoard(List<List<Integer>> board, int i, int j, int cnt) {
        if(checkComplete(cnt)){

            finalBoard = board;
            return board;
        }
        if(!finalBoard.isEmpty()) return finalBoard;
        
        if(j >= 9) {
            i++;
            j = 0;
            shuffIntegers = shuffIntegers();
        }
        if(i >= 9) return board;

        if(shuffIntegers.isEmpty()) {
            shuffIntegers = shuffIntegers();
        }

        for(int ind = 0;ind<9;ind++) {
            if(isValid(board, i, j, shuffIntegers.get(ind))){
                List<List<Integer>> newBoard = copyBoard(board);
                putValue(newBoard, i, j, shuffIntegers.get(ind));
                generateBoard(newBoard, i, j+1, cnt+1);
            }
        }

        return finalBoard;
        
    }
    
    private boolean checkComplete(int cnt) {
        return cnt == 81;
    }

    private boolean isValid(List<List<Integer>> board, int i, int j, int value) {
        
        if(board.get(i).get(j) != 0) return false;

        for (int ind = 0; ind < 9; ind++) {
            if(board.get(i).get(ind) == value) return false;
        }
        for (int ind = 0; ind < 9; ind++) {
            if(board.get(ind).get(j) == value) return false;
        }
        int startRow = (i/3)*3;
        int startCol = (j/3)*3;

        for(int r = startRow; r < startRow+3; r++) {
            
            for(int c = startCol; c < startCol+3; c++)
                if(board.get(r).get(c) == value)
                    return false;
    }
    return true;
    }
    
    private void putValue(List<List<Integer>> board, int i, int j, int value) {
        board.get(i).set(j, value);
    }
    
    
    
    
    private List<Integer> shuffIntegers() {
        List<Integer> shuffIntegers = new ArrayList<>();

        for(int i = 1;i<10;i++){
            shuffIntegers.add(i);
        }
        
        Collections.shuffle(shuffIntegers);
        
        return shuffIntegers;
        
	}
    
    public long getLife() {
        if(!over && life == 0) {
            finishTime = System.currentTimeMillis()/1000 - time;
        } 
        return life;
    }
    public void setOver() {
        over = false;
    }
    public void deductLife() {
        life--;
    }
    public void resetBoard() {
        finalBoard.clear();
        puzzle.clear();
        shuffIntegers.clear();
    }
    public long getTime() {
        return time;
    }
    public long getFinishTime() {
        return finishTime;
    }


}
