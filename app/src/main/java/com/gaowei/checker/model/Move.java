package com.gaowei.checker.model;

import android.util.Log;

public class Move {
    private static final String LOG_TAG = Move.class.getSimpleName();
    private Cell targetCell;
    private Cell sourceCell;
    private Cell middle;


    public void implement() {
        logJump();
        targetCell.setPiece(sourceCell.getPiece());
        sourceCell.setPiece(null);
        if(middle != null) {
            middle.getPiece().setCellStaying(null);
            middle.setPiece(null);
        }
        targetCell.setHint(null);
    }

    private void logJump() {
        if(middle == null) {
            Log.d(LOG_TAG, sourceCell + " move to " + targetCell);
        } else {
            Log.d(LOG_TAG, sourceCell + " jump over " + middle + " to " + targetCell);
        }
    }

    public void hint() {
        if(isValidMove())
            targetCell.setHint(this);
    }

    public void hideHint() {
        if(isValidMove())
            targetCell.setHint(null);
    }

    public void reset() {
        targetCell = null;
        sourceCell = null;
        setMiddle(null);
    }

    public void setMiddle(Cell mid) {
        middle = mid;
        if(middle != null) {
            middle.getPiece().setJumpTarget(true);
        }
    }

    public void setSourceCell(Cell sourceCell) {
        this.sourceCell = sourceCell;
    }

    public void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    public boolean isJump() {
        return middle != null;
    }

    public boolean isValidMove() {
        return targetCell != null && sourceCell != null;
    }

}
