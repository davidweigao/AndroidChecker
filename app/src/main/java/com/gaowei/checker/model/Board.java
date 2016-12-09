package com.gaowei.checker.model;

public class Board {
    public static int LENGTH = 8;
    public static int PIECE_AMOUNT = LENGTH / 2 * (LENGTH / 2 - 1);
    public static int DEFAULT_CELL_COLOR_2 = 0xff614037;
    public static int DEFAULT_CELL_COLOR_1 = 0xffdecab1;
    public static int DEFAULT_PIECE_COLOR_1 = 0xffffffff;
    public static int DEFAULT_PIECE_COLOR_2 = 0xff000000;

    Cell[][] mCells = new Cell[LENGTH][LENGTH];
    Piece[] mWhitePieces = new Piece[PIECE_AMOUNT];
    Piece[] mBlackPieces = new Piece[PIECE_AMOUNT];

    public Board() {
        for(int i = 0; i < LENGTH; i++) {
            mCells[i] = new Cell[LENGTH];
            for (int j = 0; j < LENGTH; j++) {
                mCells[i][j] =
                        new Cell((i+j) % 2 == 0 ? DEFAULT_CELL_COLOR_1 : DEFAULT_CELL_COLOR_2,
                                j,
                                i);
            }
        }
        for(int i = 0; i < PIECE_AMOUNT; i++) {
            mWhitePieces[i] = new Piece(DEFAULT_PIECE_COLOR_1);
            mBlackPieces[i] = new Piece(DEFAULT_PIECE_COLOR_2);
        }
        buildCellGraph();
    }

    public void reset() {
        int l = LENGTH / 2 - 1;
        int count = 0;
        for(int i = 0 ; i < LENGTH; i++) {
            for(int j = 0; j < LENGTH; j++) {
                mCells[i][j].setPiece(null);
            }
        }

        for(int i = 0 ; i < l; i++) {
            for(int j = (i+1) % 2; j < LENGTH; j = j + 2) {
                mCells[i][j].setPiece(mWhitePieces[count]);
                mCells[LENGTH-1-i][LENGTH-1-j].setPiece(mBlackPieces[count]);
                count++;
            }
        }

        updateNextSteps(mWhitePieces);
        updateNextSteps(mBlackPieces);
    }

    private void buildCellGraph() {
        int x;
        int y;
        for(Cell[] row : mCells) {
            for(Cell cell : row) {
                x = cell.getX();
                y = cell.getY();
                if (y-1 >= 0) {
                    if(x + 1 < LENGTH) {
                        cell.setRightFrontCell(mCells[y-1][x+1]);
                    }
                    if(x - 1 >= 0) {
                        cell.setLeftFrontCell(mCells[y-1][x-1]);
                    }
                }
                if (y+1 < LENGTH) {
                    if(x + 1 < LENGTH) {
                        cell.setRightBackCell(mCells[y+1][x+1]);
                    }
                    if(x - 1 >= 0) {
                        cell.setLeftBackCell(mCells[y+1][x-1]);
                    }
                }
            }
        }
    }

    private void updateNextSteps(Piece[] piecesToUpdate) {
        for(Piece p : piecesToUpdate) {
            if(p.isOnCell())
                p.updateMoves();
        }
    }

    void updateNextSteps(Player player) {
        Piece[] piecesToUpdate = player.isBlacePiecePlayer() ? mBlackPieces : mWhitePieces;
        updateNextSteps(piecesToUpdate);
    }

    public int getSize() {
        return LENGTH * LENGTH;
    }

    public Cell getCell(int row, int column) {
        return mCells[row][column];
    }


}
