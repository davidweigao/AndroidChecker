package com.gaowei.checker.model;


public interface GameStateListener {
    void gameover(Player winner);
    void switchPlayer(Player player);
}
