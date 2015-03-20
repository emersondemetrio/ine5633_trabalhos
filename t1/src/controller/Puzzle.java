package controller;

import view.PuzzleGui;
import model.Item;
import model.Tabuleiro;

public class Puzzle {

	protected Tabuleiro tab;

	public Puzzle(int slots) {
		this.tab = new Tabuleiro();
		new PuzzleGui("8 Puzzle Game", tab);
	}

	public void iniciar() {

	}
}

/*
 * public void showStatus(Tabuleiro tab) { Item[][] local = tab.getTabuleiro();
 * String output = "";
 * 
 * for (int i = 0; i < local.length; i++) {
 * 
 * for (int j = 0; j < local.length; j++) { output += " [" +
 * local[i][j].getValor() + "] "; }
 * 
 * output += "\n"; }
 * 
 * System.out.println("\n  ---TABLE---\n" + output); }
 */
