package com.gaowei.checker.model;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  This is a Bot player, using a very simple AI: random move, jump if available
 */
public class BotPlayer extends Player {

    private static Handler mHandler = new Handler();

    private static final int DELAY_MS = 300;

    private Runnable mCurrentCallback;

    @Override
    public void reset() {
        super.reset();
        if(mCurrentCallback != null) {
            mHandler.removeCallbacks(mCurrentCallback);
        }

    }

    @Override
    public void turnedToPlay() {
        super.turnedToPlay();
        mCurrentCallback = new Runnable() {
            @Override
            public void run() {
                autoPlay();
                mCurrentCallback = null;
            }
        };
        mHandler.postDelayed(mCurrentCallback, DELAY_MS);

    }

    /**
     *  AI logic
     */
    private void autoPlay() {
        List<Piece> candidates = new ArrayList<>();
        if(mustJump()) {
            for(Piece p : mMyPiece) {
                if(p.canJump()) candidates.add(p);
            }
        } else {
            for (Piece p : mMyPiece) {
                candidates.add(p);
            }
        }
        Random random = new Random();
        Piece selected = null;
        while(!candidates.isEmpty()) {
            int r = random.nextInt(candidates.size());
            Piece candidate = candidates.get(r);
            if(candidate.hasNextStep()) {
                selected = candidate;
                break;
            } else {
                candidates.remove(r);
            }
        }
        if(selected == null) {
            mPieceMovedListener.pieceMoved();
            return;
        }
        else {
            final Move selectedMove =
                    mustJump() ? getRandomJump(selected) : getRandomMove(selected);
            if(selectedMove == null) {
                Log.e(LOG_TAG, "impossible");
                mPieceMovedListener.pieceMoved();
                return;
            }
            selectedMove.implement();
            mPieceMovedListener.pieceMoved();
        }

    }

    private Move getRandomMove(Piece piece) {
        if(!piece.getLeftMove().isValidMove() && !piece.getRightMove().isValidMove()) return null;
        if(!piece.getLeftMove().isValidMove()) return piece.getRightMove();
        else if(!piece.getRightMove().isValidMove()) return piece.getLeftMove();
        else return new Random().nextBoolean() ? piece.getLeftMove() : piece.getRightMove();
    }

    private Move getRandomJump(Piece piece) {
        if(piece.getLeftMove().isJump() && !piece.getRightMove().isJump()) {
            return piece.getLeftMove();
        } else if (!piece.getLeftMove().isJump() && piece.getRightMove().isJump()) {
            return piece.getRightMove();
        } else {
            return new Random().nextBoolean() ? piece.getLeftMove() : piece.getRightMove();
        }
    }
}
