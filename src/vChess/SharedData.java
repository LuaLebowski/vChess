package vChess;

import java.util.ArrayList;

import chesspresso.move.IllegalMoveException;
import chesspresso.position.Position;

public class SharedData {
	
	/**
	 * The position that is currently on the board
	 */
	public static Position mainPosition = new Position();
	public static ArrayList<Position> history = new ArrayList<Position>();
	public static void init() {
		history.add(mainPosition);
		mainPosition.setStart();
	}
	public static void addMove(short move) throws IllegalMoveException {
		mainPosition.doMove(move);
		history.add(mainPosition);
	}

}
