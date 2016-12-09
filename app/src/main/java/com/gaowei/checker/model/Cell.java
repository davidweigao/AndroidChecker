package com.gaowei.checker.model;

import com.gaowei.checker.view.CellView;

public class Cell {
    private CellView view;
    private Piece mPiece;
    private int mColor;
    private Move hint;
    private int x;
    private int y;
    private Cell leftFrontCell;
    private Cell rightFrontCell;
    private Cell leftBackCell;
    private Cell rightBackCell;
    public Cell(int color, int x, int y) {
        mColor = color;
        this.x = x;
        this.y = y;
    }
    public void setLeftFrontCell(Cell cell) {
        leftFrontCell = cell;
    }

    public void setRightFrontCell(Cell cell) {
        rightFrontCell = cell;
    }

    public void setLeftBackCell(Cell cell) {
        leftBackCell = cell;
    }

    public void setRightBackCell(Cell cell) {
        rightBackCell = cell;
    }

    public Cell getLeftFrontCellFromDirection(boolean fromBlackPlayer) {
        if(fromBlackPlayer) {
            return getLeftFrontCell();
        } else {
            return getRightBackCell();
        }
    }

    public Cell getRightFrontCellFromDirection(boolean fromBlackPlayer) {
        if(fromBlackPlayer) {
            return getRightFrontCell();
        } else {
            return getLeftBackCell();
        }
    }

    public Cell getLeftFrontCell() {
        return leftFrontCell;
    }

    public Cell getRightFrontCell() {
        return rightFrontCell;
    }

    public Cell getLeftBackCell() {
        return leftBackCell;
    }

    public Cell getRightBackCell() {
        return rightBackCell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setView(CellView view) {
        this.view = view;
    }

    public boolean isOccupied() {
        return mPiece != null;
    }

    public void setHint(Move hint) {
        this.hint = hint;
        invalidate();
    }

    public boolean hasHint() {
        return hint != null;
    }

    void setPiece(Piece piece) {
        if(piece != null) {
            piece.setCellStaying(this);
        }
        mPiece = piece;

        invalidate();
    }

    public int getColor() {
        return mColor;
    }

    public int getPieceColor() {
        return mPiece.getColor();
    }

    public int getHintColor() {
        return 0xffff0000;
    }

    public Piece getPiece() {
        return mPiece;
    }


    public boolean implementHint() {
        if(hint != null) {
            boolean isJump = hint.isJump();
            hint.implement();
            return isJump;
        } else {
            throw new RuntimeException("hint is null");
        }
    }

    public void invalidate() {
        if(view != null) view.invalidate();
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)" + (isOccupied() ? getPiece().toString() : "E" ), x, y);
    }
}
