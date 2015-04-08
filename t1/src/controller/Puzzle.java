package controller;

import model.Tabuleiro;
import view.PuzzleGui;

public class Puzzle {

	protected Tabuleiro tab;

	public Puzzle() {
		tab = new Tabuleiro();
		new PuzzleGui("Entre com um estado inicial", tab);		
	}

	public static void showStatus(String msg, int[][] estado) {

		String output = "";
		for (int i = 0; i < estado.length; i++) {
			for (int j = 0; j < estado.length; j++) {
				output += " [" + estado[i][j] + "] ";
			}
			output += "\n";
		}
		System.out.println("\n  ---" + msg + "---\n" + output);
	}

}