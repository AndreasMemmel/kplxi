package main;

import java.util.ArrayList;

public class Board {
    private boolean solved;
    private Field[][] board;

    public Board(int size) {
        solved = false;
        board = new Field[size][size];
        reset();
    }

    public void setNumberField(int x, int y, int[] numbers) {
        if (board[y][x].isFilled())
            board[y][x].setFilled(false);
        board[y][x].setNumberField(true);
        board[y][x].setNumbers(numbers);
    }

    public ArrayList<Field> findPossibleFields() {
        ArrayList<Field> accessibleFields = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x].isFilled()) {
                    for (int i = y - 1; i <= y + 1; i++) {
                        //check for out of Bounds            //check for self
                        if (!(i < 0 || i >= board.length) && (i != y)) {
                            if (!board[i][x].isNumberField() && !board[i][x].isFilled()) {
                                accessibleFields.add(board[i][x]);
                            }
                        }
                    }
                    for (int j = x - 1; j <= x + 1; j++) {
                        //check for out of Bounds               //check for self
                        if (!(j < 0 || j >= board[y].length) && (j != x)) {
                            if (!board[y][j].isNumberField() && !board[y][j].isFilled()) {
                                accessibleFields.add(board[y][j]);
                            }
                        }
                    }
                }
            }
        }
        return accessibleFields;
    }

    public ArrayList<Boolean> getCurrentBoardState() {
        ArrayList<Boolean> boardState = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                boardState.add(board[y][x].isFilled());
            }
        }
        return boardState;
    }

    public ArrayList<Integer> blackRegionsSurroundingField(int x, int y) {
        boolean firstFieldFilled = false;
        ArrayList<Integer> blackRegions = new ArrayList<>();
        int scannY = y - 1;
        int scannX = x - 1;
        int unbroken = 0;
        if (!(scannX < 0 || scannY < 0)) {
            firstFieldFilled = board[scannY][scannX].isFilled();
        }
        for (int circleSegment = 0; circleSegment < 4; circleSegment++) {
            for (int i = 0; i < 2; i++) {
                if (!(scannY < 0 || scannX < 0 || scannY >= board.length || scannX >= board[scannY].length)) {
                    if (board[scannY][scannX].isFilled()) {
                        unbroken++;
                    } else {
                        if (unbroken != 0) {
                            blackRegions.add(unbroken);
                            unbroken = 0;
                        }
                    }
                } else {
                    if (unbroken != 0) {
                        blackRegions.add(unbroken);
                        unbroken = 0;
                    }
                }
                switch (circleSegment) {
                    case 0:
                        scannX++;
                        break;
                    case 1:
                        scannY++;
                        break;
                    case 2:
                        scannX--;
                        break;
                    case 3:
                        scannY--;
                }
            }
        }
        if (unbroken != 0) {
            blackRegions.add(unbroken);
        }
        if (x - 1 >= 0 && firstFieldFilled && board[y][x - 1].isFilled()) {
            int firstEntry = blackRegions.remove(0);
            if (blackRegions.size() > 0) {
                firstEntry += blackRegions.remove(blackRegions.size() - 1);
            }
            blackRegions.add(firstEntry);
        }
        return blackRegions;
    }

    public void resetProbabilities() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x].setProbability(0);
            }
        }
    }

    public ArrayList<Field> getNumberFields() {
        ArrayList<Field> numberFields = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x].isNumberField()) {
                    numberFields.add(board[y][x]);
                }
            }
        }
        return numberFields;
    }

    public Field[][] getBoard() {
        return board;
    }

    public void reset() {
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                board[y][x] = new Field(x, y);
            }
        }
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
