package com.gaowei.checker.model;

import android.util.Log;

class Move {
    private static final String LOG_TAG = Move.class.getSimpleName();
    private Cell targetCell;
    private Cell sourceCell;
    private Cell middle;

    void implement() {
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

    void hint() {
        if(isValidMove())
            targetCell.setHint(this);
    }

    void hideHint() {
        if(isValidMove())
            targetCell.setHint(null);
    }

    void reset() {
        targetCell = null;
        sourceCell = null;
        setMiddle(null);
    }

    void setMiddle(Cell mid) {
        middle = mid;
        if(middle != null) {
            middle.getPiece().setJumpTarget(true);
        }
    }

    void setSourceCell(Cell sourceCell) {
        this.sourceCell = sourceCell;
    }

    void setTargetCell(Cell targetCell) {
        this.targetCell = targetCell;
    }

    boolean isJump() {
        return middle != null;
    }

    boolean isValidMove() {
        return targetCell != null && sourceCell != null;
    }

}
