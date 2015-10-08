package vChess;

import chesspresso.Chess;

public class Coefficients {
	/**
	 * Coefficient of material
	 */
	public static final double kMaterial = 0.22;
	public static final double kFreedom = 0.16;
	/**
	 * Returns the quality of specified stone
	 * @param stone the stone to check for quality
	 * @return double-precision value
	 */
	public static double valueOf(int stone) {
		switch(stone) {
		case Chess.WHITE_ROOK:
			return 5 * valueOf(Chess.WHITE_PAWN);
		case Chess.WHITE_KNIGHT:
		case Chess.WHITE_BISHOP:
			return 3 * valueOf(Chess.WHITE_PAWN);
		case Chess.WHITE_QUEEN:
			return 9.5 * valueOf(Chess.WHITE_PAWN);
		case Chess.WHITE_KING:
			return 1000 * valueOf(Chess.WHITE_PAWN);
		case Chess.WHITE_PAWN:
			return +1;
		case Chess.BLACK_ROOK:
			return 5 * valueOf(Chess.BLACK_PAWN);
		case Chess.BLACK_KNIGHT:
		case Chess.BLACK_BISHOP:
			return 3 * valueOf(Chess.BLACK_PAWN);
		case Chess.BLACK_QUEEN:
			return 9.5 * valueOf(Chess.BLACK_PAWN);
		case Chess.BLACK_KING:
			return 1000 * valueOf(Chess.BLACK_PAWN);
		case Chess.BLACK_PAWN:
			return -1;
		default:
				return 0;
		}
	}
}
