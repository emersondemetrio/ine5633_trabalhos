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

}