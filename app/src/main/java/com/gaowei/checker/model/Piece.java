package com.gaowei.checker.model;

public class Piece {
    private Cell mCellStaying;
    private int mColor;
    private Move leftMove = new Move();
    private Move rightMove = new Move();
    private boolean jumpTarget;

    public boolean isJumpTarget() {
        return jumpTarget;
    }

    public void setJumpTarget(boolean target) {
        jumpTarget = target;
        mCellStaying.invalidate();
    }

    public void setCellStaying(Cell mCellStaying) {
        this.mCellStaying = mCellStaying;
    }

    public Piece(int color) {
        mColor = color;
    }

    public int getColor() {
        return mColor;
    }

    public boolean hasNextStep() {
        return isOnCell() && ( leftMove.isValidMove() || rightMove.isValidMove());
    }

    public Move getLeftMove() {
        return leftMove;
    }

    public Move getRightMove() {
        return rightMove;
    }

    public void showHint() {
        leftMove.hint();
        rightMove.hint();
    }

    public boolean canJump() {
        return isOnCell() && (leftMove.isJump() || rightMove.isJump());
    }

    public void showJumpHint() {
        if(leftMove.isJump()) leftMove.hint();
        if(rightMove.isJump()) rightMove.hint();
    }

    public boolean isBlackPiece() {
        return mColor == Board.DEFAULT_PIECE_COLOR_2;
    }

    public void hideHint() {
        leftMove.hideHint();
        rightMove.hideHint();
    }

    public boolean isOnCell() {
        return mCellStaying != null;
    }

    public void updateMoves() {
        if(isJumpTarget()) {
            jumpTarget = false;
            if(mCellStaying != null) mCellStaying.invalidate();
        }
        if(mCellStaying == null){
            leftMove.reset();
            rightMove.reset();
            return;
        }
        Cell leftFrontCell = leftFrontCell();
        Cell rightFrontCell = rightFrontCell();
        leftMove.reset();
        rightMove.reset();
        if(leftFrontCell != null ) {
            if(!leftFrontCell.isOccupied()) {
                leftMove.setSourceCell(mCellStaying);
                leftMove.setTargetCell(leftFrontCell);
            } else {
                updateJumpLeftFront();
            }
        }

        if(rightFrontCell != null) {
            if(!rightFrontCell.isOccupied()) {
                rightMove.setSourceCell(mCellStaying);
                rightMove.setTargetCell(rightFrontCell);
            } else {
                updateJumpRightFront();
            }
        }

    }

    public void updateJumpLeftFront() {
        leftMove.reset();
        Cell leftFrontCell = leftFrontCell();
        if(leftFrontCell != null
                && leftFrontCell.isOccupied()
                && leftFrontCell.getPieceColor() != mColor) {
            Cell jumpTarget = leftFrontCell.getLeftFrontCellFromDirection(isBlackPiece());
            if(jumpTarget != null && !jumpTarget.isOccupied()) {
                leftMove.setSourceCell(mCellStaying);
                leftMove.setTargetCell(jumpTarget);
                leftMove.setMiddle(leftFrontCell);
            }
        }
    }

    public void updateJumpRightFront() {
        rightMove.reset();
        Cell rightFrontCell = rightFrontCell();
        if(rightFrontCell != null
                && rightFrontCell.isOccupied()
                && rightFrontCell.getPieceColor() != mColor) {
            Cell jumpTarget = rightFrontCell.getRightFrontCellFromDirection(isBlackPiece());
            if(jumpTarget != null && !jumpTarget.isOccupied()) {
                rightMove.setSourceCell(mCellStaying);
                rightMove.setTargetCell(jumpTarget);
                rightMove.setMiddle(rightFrontCell);
            }
        }
    }

    public Cell leftFrontCell() {
        if(mCellStaying == null) throw new RuntimeException("Piece is off board");
        return mCellStaying.getLeftFrontCellFromDirection(isBlackPiece());
    }

    public Cell rightFrontCell() {
        if(mCellStaying == null) throw new RuntimeException("Piece is off board");
        return mCellStaying.getRightFrontCellFromDirection(isBlackPiece());
    }

    @Override
    public String toString() {
        return isBlackPiece() ? "B" : "W";
    }
}
