package com.gaowei.checker.model;

import android.util.Log;

public class Player {

    static final String LOG_TAG = Player.class.getSimpleName();
    Piece[] mMyPiece;
    private Board mBoard;
    PieceMovedListener mPieceMovedListener;
    private boolean mMultipleJump = false;
    private boolean mJumpAvailable = false;
    private Piece mPieceHolding = null;

    boolean mustJump() {
        return mJumpAvailable;
    }

    void joinBoard(Board board) {
        mBoard = board;
    }


    void setPieceMovedListener(PieceMovedListener pieceMovedListener) {
        mPieceMovedListener = pieceMovedListener;
    }

    public void reset() {
        mMultipleJump = false;
        mJumpAvailable = false;
    }

    public void turnedToPlay() {
        Log.d(LOG_TAG, this.toString() + " is turned to play");
        mBoard.updateNextSteps(this);
        mJumpAvailable = false;
        for (Piece p : mMyPiece) {
            if (p.canJump()) {
                Log.d(LOG_TAG, "must jump");
                mJumpAvailable = true;
                break;
            }
        }
    }

    public void movePieceTo(Cell cell) {
        if (cell.hasHint()) {

            boolean isJump = cell.implementHint();
            mPieceHolding.hideHint();
            mPieceHolding = null;
            if (isJump) {
                mPieceHolding = cell.getPiece();
                mPieceHolding.updateMoves();
                if (mPieceHolding.canJump()) {
                    mPieceHolding.showJumpHint();
                    Log.d(LOG_TAG, "multiple jump available");
                    mMultipleJump = true;
                    return;
                }
            }
            mMultipleJump = false;
            mPieceMovedListener.pieceMoved();
            Log.d(LOG_TAG, this.toString() + " is done");
        }
    }

    public void showHint(Cell cell) {
        if (mMultipleJump) return;
        if (cell.isOccupied()) {
            if (mustJump() && !cell.getPiece().canJump()) return;
            if (cell.getPiece().getColor() != mMyPiece[0].getColor()) return;
            if (mPieceHolding != null) {
                mPieceHolding.hideHint();
            }
            mPieceHolding = cell.getPiece();
            if (mustJump()) mPieceHolding.showJumpHint();
            else mPieceHolding.showHint();
        }
    }

    boolean isBlacePiecePlayer() {
        return !(mMyPiece == null || mMyPiece.length == 0) && mMyPiece[0].isBlackPiece();
    }

    boolean hasNextStep() {
        for (Piece piece : mMyPiece) {
            if (piece.hasNextStep()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return isBlacePiecePlayer() ? "BPlayer" : "WPlayer";
    }
}
