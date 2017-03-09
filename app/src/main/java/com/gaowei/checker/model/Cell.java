package com.gaowei.checker.model;

import com.gaowei.checker.view.CellView;

public class Cell {
    private CellView view;
    private Piece mPiece;
    private final int mColor;
    private Move hint;
    private final int x;
    private final int y;
    private Cell leftFrontCell;
    private Cell rightFrontCell;
    private Cell leftBackCell;
    private Cell rightBackCell;
    Cell(final int color, final int x, final int y) {
        mColor = color;
        this.x = x;
        this.y = y;
    }
    void setLeftFrontCell(Cell cell) {
        leftFrontCell = cell;
    }

    void setRightFrontCell(Cell cell) {
        rightFrontCell = cell;
    }

    void setLeftBackCell(Cell cell) {
        leftBackCell = cell;
    }

    void setRightBackCell(Cell cell) {
        rightBackCell = cell;
    }

    Cell getLeftFrontCellFromDirection(boolean fromBlackPlayer) {
        if(fromBlackPlayer) {
            return getLeftFrontCell();
        } else {
            return getRightBackCell();
        }
    }

    Cell getRightFrontCellFromDirection(boolean fromBlackPlayer) {
        if(fromBlackPlayer) {
            return getRightFrontCell();
        } else {
            return getLeftBackCell();
        }
    }

    private Cell getLeftFrontCell() {
        return leftFrontCell;
    }

    private Cell getRightFrontCell() {
        return rightFrontCell;
    }

    private Cell getLeftBackCell() {
        return leftBackCell;
    }

    private Cell getRightBackCell() {
        return rightBackCell;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void setView(CellView view) {
        this.view = view;
    }

    public boolean isOccupied() {
        return mPiece != null;
    }

    void setHint(Move hint) {
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

    boolean implementHint() {
        if(hint != null) {
            boolean isJump = hint.isJump();
            hint.implement();
            return isJump;
        } else {
            throw new RuntimeException("hint is null");
        }
    }

    void invalidate() {
        if(view != null) view.invalidate();
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)" + (isOccupied() ? getPiece().toString() : "E" ), x, y);
    }
}
