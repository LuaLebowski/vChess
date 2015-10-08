package vChess;

import org.junit.Assert;
import org.junit.Test;

import chesspresso.Chess;
import chesspresso.move.IllegalMoveException;
import chesspresso.move.Move;
import chesspresso.position.Position;

public class UTEngine {

	@Test
	public void fieldsWithStonesTest() {
		Position pos = new Position();
		pos.setStart();
		int[][] expected = {{Chess.A1, Chess.H1},
							{Chess.B1, Chess.G1},
							{Chess.C1, Chess.F1},
							{Chess.D1},
							{Chess.E1},
							{Chess.A2, Chess.B2, Chess.C2, Chess.D2, Chess.E2, Chess.F2, Chess.G2, Chess.H2},
							{Chess.A7, Chess.B7, Chess.C7, Chess.D7, Chess.E7, Chess.F7, Chess.G7, Chess.H7},
							{Chess.A8, Chess.H8},
							{Chess.B8, Chess.G8},
							{Chess.C8, Chess.F8},
							{Chess.D8},
							{Chess.E8}};
		int[][] actual   = {Engine.fieldsWithStone(pos, Chess.WHITE_ROOK),
							Engine.fieldsWithStone(pos, Chess.WHITE_KNIGHT),
							Engine.fieldsWithStone(pos, Chess.WHITE_BISHOP),
							Engine.fieldsWithStone(pos, Chess.WHITE_QUEEN),
							Engine.fieldsWithStone(pos, Chess.WHITE_KING),
							Engine.fieldsWithStone(pos, Chess.WHITE_PAWN),
							Engine.fieldsWithStone(pos, Chess.BLACK_PAWN),
							Engine.fieldsWithStone(pos, Chess.BLACK_ROOK),
							Engine.fieldsWithStone(pos, Chess.BLACK_KNIGHT),
							Engine.fieldsWithStone(pos, Chess.BLACK_BISHOP),
							Engine.fieldsWithStone(pos, Chess.BLACK_QUEEN),
							Engine.fieldsWithStone(pos, Chess.BLACK_KING),
												};
		for(int i = 0; i < expected.length; i++) {
			Assert.assertArrayEquals(expected[i], actual[i]);
		}
	}
	@Test
	public void legalMovesFromTest() {
		Position pos = new Position();
		pos.setStart();
		short e2e3 = Move.getPawnMove(Chess.E2, Chess.E3, false, Chess.NO_PIECE);
		short e2e4 = Move.getPawnMove(Chess.E2, Chess.E4, false, Chess.NO_PIECE);
		short[] fromE2 = {e2e3, e2e4};
		Assert.assertArrayEquals(fromE2, Engine.legalMovesFrom(pos, Chess.E2));
		try {
			pos.doMove(e2e4);
			pos.doMove(Move.getPawnMove(Chess.E7, Chess.E5, false, Chess.NO_PIECE));
		} catch (IllegalMoveException e) {
			e.printStackTrace();
		}
		short g1e2 = Move.getRegularMove(Chess.G1, Chess.E2, false);
		short g1f3 = Move.getRegularMove(Chess.G1, Chess.F3, false);
		short g1h3 = Move.getRegularMove(Chess.G1, Chess.H3, false);
		short[] fromG1 = {g1e2, g1f3, g1h3};
		Assert.assertArrayEquals(fromG1, Engine.legalMovesFrom(pos, Chess.G1));
	}
	@Test
	public void fieldsWithLightStonesOfTest() {
		Position pos = new Position();
		pos.setStart();
		int[] whiteLightsExpected = {Chess.B1, Chess.G1, Chess.C1, Chess.F1};
		int[] whiteLightsActual = Engine.fieldsWithLightStonesOf(pos, Chess.WHITE);
		Assert.assertArrayEquals(whiteLightsExpected, whiteLightsActual);
		int[] blackLightsExpected = {Chess.B8, Chess.G8, Chess.C8, Chess.F8};
		int[] blackLightsActual = Engine.fieldsWithLightStonesOf(pos, Chess.BLACK);
		Assert.assertArrayEquals(blackLightsExpected, blackLightsActual);
	}
	@Test
	public void fieldsWithHeavyStonesOfTest() {
		Position pos = new Position();
		pos.setStart();
		int[] whiteHeaviesExpected = {Chess.A1, Chess.H1, Chess.D1};
		int[] whiteHeaviesActual = Engine.fieldsWithHeavyStonesOf(pos, Chess.WHITE);
		Assert.assertArrayEquals(whiteHeaviesExpected, whiteHeaviesActual);
		int[] blackHeaviesExpected = {Chess.A8, Chess.H8, Chess.D8};
		int[] blackHeaviesActual = Engine.fieldsWithHeavyStonesOf(pos, Chess.BLACK);
		Assert.assertArrayEquals(blackHeaviesExpected, blackHeaviesActual);
	}
	@Test
	public void fieldsWithMainStonesOfTest() {
		Position pos = new Position();
		pos.setStart();
		int[] whiteMainsExpected = {Chess.B1, Chess.G1, Chess.C1, Chess.F1, Chess.A1, Chess.H1, Chess.D1};
		int[] whiteMainsActual = Engine.fieldsWithMainStonesOf(pos, Chess.WHITE);
		Assert.assertArrayEquals(whiteMainsExpected, whiteMainsActual);
		int[] blackMainsExpected = {Chess.B8, Chess.G8, Chess.C8, Chess.F8, Chess.A8, Chess.H8, Chess.D8};
		int[] blackMainsActual = Engine.fieldsWithMainStonesOf(pos, Chess.BLACK);
		Assert.assertArrayEquals(blackMainsExpected, blackMainsActual);
	}

}
