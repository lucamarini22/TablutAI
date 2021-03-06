package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import java.util.List;

public interface Board {

    void initializeBoard();

    State.Pawn getCell(int row, int col);

    BoardImpl.SquareType getSquareType(int row, int col);

    void setCell(int row, int col, State.Pawn p);

    State.Pawn[][] getBoard();

    void setBoard(State.Pawn[][] newBoard);

    List<Pair<Integer, Integer>> getHorizontalLeftCells(int row, int col);

    List<Pair<Integer, Integer>> getHorizontalRightCells(int row, int col);

    List<Pair<Integer, Integer>> getVerticalUpCells(int row, int col);

    List<Pair<Integer, Integer>> getVerticalDownCells(int row, int col);

    List<Pair<Integer, Integer>> getWhitePositions();

    void setWhitePositions(List<Pair<Integer, Integer>> whitePositions);

    List<Pair<Integer, Integer>> getBlackPositions();

    void setBlackPositions(List<Pair<Integer, Integer>> blackPositions);

    List<Pair<Integer, Integer>> getWhitePositionsFromBoard(State.Pawn[][] board);

    List<Pair<Integer, Integer>> getBlackPositionsFromBoard(State.Pawn[][] board);

    boolean isCamp(int row, int col);

    boolean isEscape(int row, int col);

    boolean isCastle(int row, int col);

    boolean isThereAPawn(int row, int col);

    String fromIntToLetter(int i);

    void updateWhitePos(int rowFrom, int colFrom, int rowTo, int colTo);

    void updateBlackPos(int rowFrom, int colFrom, int rowTo, int colTo);

    void printBoard();

    int getNumOf(State.Pawn pawnType);

    Pair<Integer, Integer> getKingPosition();

    boolean isKingOnEscape();

    boolean isKingDead();

    boolean isKingInCastle();

    boolean isKingAdjacentToCamp();

    boolean isKingAdjacentToCastle();

    int getMinManhattanDistanceKingEscape();

    Integer getNumOfOpponentsAdjacentToTheKingOf(State.Turn playerType);

    int getNumOfBlackPawnsAndCampsNextToKing();

    int manhattanDistance(Pair<Integer, Integer> start, Pair<Integer, Integer> end);

    int freePathsFromKingToEscape();
}