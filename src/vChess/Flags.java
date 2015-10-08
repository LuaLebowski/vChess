package vChess;

public class Flags {
	/**
	 * Enables debugging features (additional info shown)
	 */
	public static Boolean debug = false;
	/**
	 * Enables the engine's own CLI instead of UCI
	 */
	public static Boolean useowncli = false;
	/**
	 * Less mistakes in FEN strings are forgiven
	 */
	public static Boolean usestrictfen = false;
	public static int depth = 3;
	public static int nDeepMoves = 3;
}
