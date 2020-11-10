package it.unibo.ai.didattica.competition.tablut.maren.game;

import aima.core.util.datastructure.Pair;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class MyStateImpl implements MyState {

    private static final int WIDTH = 9;
    private static final int KING_X = 4;
    private static final int KING_Y = 4;
    private static final int NUM_OF_WHITES_PER_ROW = 4;

    private static final LinkedList<Integer> WHITE_POS = new LinkedList<>(Arrays.asList(2, 3, 5, 6));
    private static final LinkedList<Integer> BLACK_EDGES_1 = new LinkedList<>(Arrays.asList(3, 4, 5));
    private static final LinkedList<Integer> BLACK_EDGES_2 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> INTERNAL_BLACK_1 = new LinkedList<>(Arrays.asList(1, 7));
    private static final int INTERNAL_BLACK_2 = 4;

    private static final LinkedList<Integer> CAMP_EDGES_1 = new LinkedList<>(Arrays.asList(3, 4, 5));
    private static final LinkedList<Integer> CAMP_EDGES_2 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> INTERNAL_CAMP_1 = new LinkedList<>(Arrays.asList(1, 7));
    private static final int INTERNAL_CAMP_2 = 4;

    private static final LinkedList<Integer> ESCAPES_1 = new LinkedList<>(Arrays.asList(0, 8));
    private static final LinkedList<Integer> ESCAPES_2 = new LinkedList<>(Arrays.asList(1, 2, 6, 7));


    private enum SquareType {
        CASTLE,
        CAMP,
        ESCAPE
    }

    private State.Turn turn;
    private int currentDepth;
    private final HashMap<Pair<Integer, Integer>, SquareType> specialSquares = new HashMap<>();

    State.Pawn[][] board;

    public MyStateImpl(int depth) {
        this.turn = State.Turn.WHITE;
        this.initializeBoard();
        this.setCurrentDepth(depth);
    }

    public void setCurrentDepth(int currentDepth) {
        this.currentDepth = currentDepth;
    }

    private void setBoard(State.Pawn[][] newBoard) {
        this.board = newBoard;
    }

    @Override
    public void updateState(State currentState) {
        this.setBoard(currentState.getBoard());
    }


    @Override
    public State.Turn getTurn() {
        return this.turn;
    }


    private State.Pawn getBoardCell(int row, int col) {
        if (!(row < 0 || col < 0 || row > WIDTH || col > WIDTH)) {
            return this.board[row][col];
        }
        return null;
    }

    @Override
    public List<MyAction> getPossibleActions() {




        List l = new LinkedList<>();
        MyAction a = new MyActionImpl("e3", "f3", this.getTurn());
        MyAction b = new MyActionImpl("e4", "f4", this.getTurn());
        l.add(a);
        l.add(b);
        return l;
    }

    @Override
    public int getCurrentDepth() {
        return this.currentDepth;
    }

    private void setBoardCell(int row, int col, State.Pawn p) {
        if (!(row < 0 || col < 0 || row > WIDTH || col > WIDTH)) {
            this.board[row][col] = p;
        }
    }

    private void setWhitePositions() {
        WHITE_POS.forEach((pos) -> {
            this.setBoardCell(pos, NUM_OF_WHITES_PER_ROW, State.Pawn.WHITE);
            this.setBoardCell(NUM_OF_WHITES_PER_ROW, pos, State.Pawn.WHITE);
        });
    }

    private void setBlackPositions() {
        BLACK_EDGES_1.forEach((b_edge_1) ->
                BLACK_EDGES_2.forEach((b_edge_2) -> {
                    this.setBoardCell(b_edge_1, b_edge_2, State.Pawn.BLACK);
                    this.setBoardCell(b_edge_2, b_edge_1, State.Pawn.BLACK);
                }));
        INTERNAL_BLACK_1.forEach((i_b_1) -> {
            this.setBoardCell(i_b_1, INTERNAL_BLACK_2, State.Pawn.BLACK);
            this.setBoardCell(INTERNAL_BLACK_2, i_b_1, State.Pawn.BLACK);
        });
    }

    private void setSpecialSquares() {
        // Set escapes
        ESCAPES_1.forEach((e1) -> {
            ESCAPES_2.forEach((e2 -> {
                this.specialSquares.put(new Pair<>(e1, e2), SquareType.ESCAPE);
                this.specialSquares.put(new Pair<>(e2, e1), SquareType.ESCAPE);
            }));
        });

        // Set camps
        CAMP_EDGES_1.forEach((c_edge_1) ->
                CAMP_EDGES_2.forEach((c_edge_2) -> {
                    this.specialSquares.put(new Pair<>(c_edge_1, c_edge_2), SquareType.CAMP);
                    this.specialSquares.put(new Pair<>(c_edge_2, c_edge_1), SquareType.CAMP);
                }));
        INTERNAL_CAMP_1.forEach((i_c_1) -> {
            this.specialSquares.put(new Pair<>(i_c_1, INTERNAL_CAMP_2), SquareType.CAMP);
            this.specialSquares.put(new Pair<>(INTERNAL_CAMP_2, i_c_1), SquareType.CAMP);
        });

        // Set the castle
        this.specialSquares.put(new Pair<>(KING_X, KING_Y), SquareType.CASTLE);
    }

    private void initializeBoard() {
        this.board = new State.Pawn[WIDTH][WIDTH];

        IntStream.range(0, WIDTH).forEach((row) -> {
            IntStream.range(0, WIDTH).forEach((col) -> {
                this.setBoardCell(row, col, State.Pawn.EMPTY);
            });
        });

        this.setBoardCell(KING_X, KING_Y, State.Pawn.THRONE);

        // this.turn = State.Turn.BLACK;

        this.setBoardCell(KING_X, KING_Y, State.Pawn.KING);
        this.setWhitePositions();
        this.setBlackPositions();
        this.setSpecialSquares();

        /*this.board[2][4] = State.Pawn.WHITE;
        this.board[3][4] = State.Pawn.WHITE;
        this.board[5][4] = State.Pawn.WHITE;
        this.board[6][4] = State.Pawn.WHITE;
        this.board[4][2] = State.Pawn.WHITE;
        this.board[4][3] = State.Pawn.WHITE;
        this.board[4][5] = State.Pawn.WHITE;
        this.board[4][6] = State.Pawn.WHITE;*/
        /*this.board[0][3] = State.Pawn.BLACK;
        this.board[0][4] = State.Pawn.BLACK;
        this.board[0][5] = State.Pawn.BLACK;
        this.board[1][4] = State.Pawn.BLACK;
        this.board[8][3] = State.Pawn.BLACK;
        this.board[8][4] = State.Pawn.BLACK;
        this.board[8][5] = State.Pawn.BLACK;
        this.board[7][4] = State.Pawn.BLACK;
        this.board[3][0] = State.Pawn.BLACK;
        this.board[4][0] = State.Pawn.BLACK;
        this.board[5][0] = State.Pawn.BLACK;
        this.board[4][1] = State.Pawn.BLACK;
        this.board[3][8] = State.Pawn.BLACK;
        this.board[4][8] = State.Pawn.BLACK;
        this.board[5][8] = State.Pawn.BLACK;
        this.board[4][7] = State.Pawn.BLACK;*/
    }



}
