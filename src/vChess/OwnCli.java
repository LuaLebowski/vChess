package vChess;

import chesspresso.Chess;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;

public class OwnCli implements ICli {
	
	public short parseMove(String s) {
		short r;
		Boolean capturing;
		Boolean pawnMove;
		int fromSqi;
		int toSqi;
		int promotionPiece = Chess.NO_PIECE;
		
		if(s.charAt(2) == '-') {
			capturing = false;
		} else if(s.charAt(2) == 'x') {
			capturing = true;
		} else {
			throw new IllegalArgumentException(s);
		}
		
		if(s.length() == 5) {
			pawnMove = false;
		} else if(s.length() == 6) {
			pawnMove = true;
			promotionPiece = Chess.charToPiece(s.charAt(5));
		} else {
			throw new IllegalArgumentException();
		}
		
		String f = s.substring(0, 2);
		fromSqi = Chess.strToSqi(f);
		f = s.substring(3, 5);
		toSqi = Chess.strToSqi(f);
		if(pawnMove) {
			r = Move.getPawnMove(fromSqi, toSqi, capturing, promotionPiece);
		} else {
			r = Move.getRegularMove(fromSqi, toSqi, capturing);
		}
		return r;
	}

	public String moveToString(short move) {
		return Move.getString(move);
	}

	@Override
	public void runCmd(String cmd) {
		try {
			SharedData.addMove(parseMove(cmd));
		} catch (IllegalMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			short move = Engine.bestMove(SharedData.mainPosition, Flags.depth);
			System.out.println(moveToString(move));
			SharedData.addMove(move);
		} catch (IllegalMoveException e) {
			System.out.println(LocalizableStrings.Errors.invalidMove);
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
