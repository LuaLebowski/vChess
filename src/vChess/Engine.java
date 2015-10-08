package vChess;

import chesspresso.Chess;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;

import java.util.ArrayList;

public class Engine {

	/**
	 *<h1><font color='red'>TO BE REFACTORED</font></h1><br />
	 * Represents the chance of whites to win in the specified position
	 * @param before the position to evaluate (before the move is done)
	 * @param move move to evaluate
	 * @param depth how many move layers are evaluated
	 * @return double-precision value representing the whites' chance to win. 
	 * @throws IllegalMoveException 
	 */
	public static double eval(Position before, short move, int depth) throws IllegalMoveException {
		//if(Flags.debug) System.out.println("eval(" + depth + ")");
		Position after = new Position();
		after.set(before);
		after.doMove(move);
		double res = fastEval(before, move);
		short opponentsMove = Move.NO_MOVE;
		if(depth > 1) {
			opponentsMove = bestMove(after, depth - 1);
			for(int i = 0; i < Flags.depth - depth; i++) {
				System.out.print("\t");
			}
			System.out.println("[" + Move.getString(opponentsMove) + "]");
		}
		for(int i = 0; i < Flags.depth - depth; i++) {
			System.out.print("\t");
		}
		System.out.println("/" + Move.getString(move) + "/");
		return depth <= 1 ? res : eval(after, opponentsMove, depth - 1);
	}
	public static double fastEval(Position before, short move) throws IllegalMoveException {
		double res = 0;
		Position after = new Position();
		after.set(before);
		if (move != Move.NO_MOVE) {
			after.doMove(move);
		}
		if(HandyUtil.containsTwice(SharedData.history, after)) {
			return 0;
		}
		for(int f = Chess.A1; f <= Chess.H8; f++) {
			res += Coefficients.valueOf(after.getStone(f)) * Coefficients.kMaterial;
		}
		for(int f : Engine.fieldsWithMainStonesOf(after, Chess.WHITE)) {
			res += legalMovesFrom(after, f).length * Coefficients.kFreedom;
		}
		for(int f : Engine.fieldsWithMainStonesOf(after, Chess.BLACK)) {
			res -= legalMovesFrom(after, f).length * Coefficients.kFreedom;
		}
		return res;
	}
	/**
	 *<h1><font color='red'>TO BE REFACTORED</font></h1><br />
	 * Finds the move which provides the best <code>eval()</code> value for the specified color
	 * @param pos position to find the move for
	 * @param depth how many move layers are evaluated
	 * @return 
	 */
	public static short bestMove(Position pos, int depth) {
		//if(Flags.debug) System.out.println("bestMove(" + depth + ")");
		short[] top = new short[Flags.nDeepMoves];
		short[] all = pos.getAllMoves();
		if(all.length == 0) {
			return Move.NO_MOVE;
		}
		short best = all[0];
		int color = pos.getToPlay();
		double bestFastEval = 0, bestEval = 0;
		try {
			bestFastEval = Engine.fastEval(pos, best);
			bestEval = Engine.eval(pos, all[0], depth);
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		}
		for(short move : all) {
			double eval = 0;
			try {
				eval = Engine.eval(pos, move, depth);
			} catch (IllegalMoveException e) {
				e.printStackTrace();
			}
			switch(color) {
			case Chess.WHITE:
				if(eval > bestEval) {
					best = move;
					bestFastEval = eval;
				}
				break;
			case Chess.BLACK:
				if(eval < bestEval) {
					best = move;
					bestFastEval = eval;
				}
			}
		}
		return best;
	}
	/**
	 * Finds all possible moves from the specified field
	 * @param pos the position to find moves on
	 * @param src the field to find moves from
	 * @return the array of possible moves (short)
	 */
	
	public static short[] legalMovesFrom(Position pos, int src) {
		short[] all = pos.getAllMoves();
		ArrayList<Short> res = new ArrayList<Short>();
		for(short move : all) {
			if(Move.getFromSqi(move) == src) {
				res.add(move);
			}
		}
		short[] endResArr = new short[res.size()];
		for(int i = 0; i < res.size(); i++) {
			endResArr[i] = res.get(i);
		}
		return endResArr;
	}
	/**
	 * Searches for the fields containing specified stone
	 * @param pos the position to search for stone in
	 * @param stone to search for
	 * @return array of all fields, containing <code>stone</code>
	 */
	public static int[] fieldsWithStone(Position pos, int stone) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for(int f = Chess.A1; f <= Chess.H8; f++) {
			if(pos.getStone(f) == stone) {
				a.add(f);
			}
		}
		int[] r = new int[a.size()];
		for(int i = 0; i < a.size(); i++) {
			r[i] = a.get(i);
		}
		return r;
	}
	/**
	 * Searches for fields with knights or bishops
	 * @param pos the position to search for light stones in
	 * @param color the color of stones searched for
	 * @return array of all fields, containing light stones
	 */
	public static int[] fieldsWithLightStonesOf(Position pos, int color) {
		return color == Chess.WHITE ?
				HandyUtil.intArrConcat(
						fieldsWithStone(pos, Chess.WHITE_KNIGHT),
						fieldsWithStone(pos, Chess.WHITE_BISHOP)) :
				HandyUtil.intArrConcat(
						fieldsWithStone(pos, Chess.BLACK_KNIGHT),
						fieldsWithStone(pos, Chess.BLACK_BISHOP));
	}
	/**
	 * Searches for fields with rooks or queens
	 * @param pos the position to search for heavy stones in
	 * @param color the color of stones searched for
	 * @return array of all fields, containing heavy stones
	 */
	public static int[] fieldsWithHeavyStonesOf(Position pos, int color) {
		return color == Chess.WHITE ?
				HandyUtil.intArrConcat(
						fieldsWithStone(pos, Chess.WHITE_ROOK),
						fieldsWithStone(pos, Chess.WHITE_QUEEN)) :
				HandyUtil.intArrConcat(
						fieldsWithStone(pos, Chess.BLACK_ROOK),
						fieldsWithStone(pos, Chess.BLACK_QUEEN));
	}
	/**
	 * Searches for fields with knights, bishops, rooks or queens
	 * @param pos the position to search for main stones in
	 * @param color the color of stones searched for
	 * @return array of all fields, containing main stones
	 */
	public static int[] fieldsWithMainStonesOf(Position pos, int color) {
		return HandyUtil.intArrConcat(fieldsWithLightStonesOf(pos, color), fieldsWithHeavyStonesOf(pos, color));
	}
}
