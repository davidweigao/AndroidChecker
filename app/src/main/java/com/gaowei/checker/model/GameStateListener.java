package com.gaowei.checker.model;


public interface GameStateListener {
    public void gameover(Player winner);
    public void switchPlayer(Player player);

}
