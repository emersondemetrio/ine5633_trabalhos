package controller;

import model.Tabuleiro;
import view.PuzzleGui;

public class Puzzle {

	protected Tabuleiro tab;

	public Puzzle(int slots) {
		this.tab = new Tabuleiro();
		new PuzzleGui("8 Puzzle Game", tab);
	}

}