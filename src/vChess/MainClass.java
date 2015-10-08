package vChess;

import java.io.IOException;
import java.util.Scanner;

import chesspresso.move.IllegalMoveException;

public class MainClass {
	public static ICli cli;
	private static Boolean run = true;
	public static void main(String[] args) throws IOException, IllegalMoveException {
		if(HandyUtil.strContains(args, "--debug")) {
			Flags.debug = true;
		}
		if(HandyUtil.strContains(args, "--useowncli")) {
			Flags.useowncli = true;
		}
		if(HandyUtil.strContains(args, "--usestrictfen")) {
			Flags.usestrictfen = true;
		}
		SharedData.init();
		/*if(Flags.useowncli)*/ { //Uncomment when UCI is implemented
			cli = new OwnCli();
		} /*else {
			cli = new UCI(); //Uncomment when UCI is implemented
		}*/
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		cli.init();
		while(run) {
			cli.runCmd(sc.nextLine());
		}
	}
	public static void stop() {
		run = false;
	}
}
