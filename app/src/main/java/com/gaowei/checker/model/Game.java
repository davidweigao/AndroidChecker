package com.gaowei.checker.model;

public class Game implements PieceMovedListener {
    private Player mPlayer1;
    private Player mPlayer2;
    private Board mBoard;
    private Player mCurrentPlayer;
    private GameStateListener mGameStateListener;

    public Game(Board board) {
        mBoard = board;
    }

    public void newGame(Player player1, Player player2, GameStateListener gameStateListener) {
        mPlayer1 = player1;
        mPlayer2 = player2;
        mPlayer1.joinBoard(mBoard);
        mPlayer1.mMyPiece = mBoard.mWhitePieces;
        mPlayer2.joinBoard(mBoard);
        mPlayer2.mMyPiece = mBoard.mBlackPieces;
        mPlayer1.setPieceMovedListener(this);
        mPlayer2.setPieceMovedListener(this);
        mGameStateListener = gameStateListener;
        reset();
    }

    public void reset() {
        mBoard.reset();
        mPlayer1.reset();
        mPlayer2.reset();
        mCurrentPlayer = mPlayer2;
        mCurrentPlayer.turnedToPlay();
        mGameStateListener.switchPlayer(mPlayer2);
    }

    private void changePlayer() {
        mCurrentPlayer = mCurrentPlayer == mPlayer1 ? mPlayer2 : mPlayer1;
        mCurrentPlayer.turnedToPlay();

    }

    private boolean isGameOver() {
        return !mCurrentPlayer.hasNextStep();
    }

    @Override
    public void pieceMoved() {
        changePlayer();
        if (!isGameOver()) {
            mGameStateListener.switchPlayer(mCurrentPlayer);
        } else {
            mCurrentPlayer.reset();
            mGameStateListener.gameover(mCurrentPlayer == mPlayer1 ? mPlayer2 : mPlayer1);
        }
    }

}
