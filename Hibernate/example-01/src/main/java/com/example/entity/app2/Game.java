package com.example.entity.app2;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Game {

    @Id
    private Long id;

    private String name;

    public Game() {
    }

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", name=" + name + "]";
    }
}
