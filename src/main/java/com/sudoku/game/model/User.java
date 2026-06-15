package com.sudoku.game.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String username;
    private String password;
    private int wins;

    
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public int getWins() {
        return wins;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }
    public void updateWins() {
        this.wins++;
    }
}
