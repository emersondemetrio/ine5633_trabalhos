package controller;

import model.Tabuleiro;

public class Puzzle {

	protected Tabuleiro tab;

	public Puzzle(int slots) {
		this.tab = new Tabuleiro();
		// new PuzzleGui("Entre com um estado inicial", tab);

		tab.setEstadoInicial(tab.getEstadoObjetivo());
		Tabuleiro temp = tab;
		int[][] teste = { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 5 } };
		temp.setEstadoInicial(teste);
		this.showStatus("Estado inicial", temp.getTabuleiro());

		temp = tab.moveVazioParaDireita(tab);
		if (temp != null) {
			this.showStatus("Move para direita", tab.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para direita.");
		}

		temp = tab.moveVazioParaEsquerda(tab);
		if (temp != null) {
			this.showStatus("Move para esquerda", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para esquerda.");
		}

		temp = tab.moveVazioParaCima(tab);
		if (temp != null) {
			this.showStatus("Move para cima", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para cima.");
		}

		temp = tab.moveVazioParaBaixo(tab);
		if (temp != null) {
			this.showStatus("Move baixo", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para baixo.");
		}

		temp = tab.moveVazioParaBaixo(tab);
		if (temp != null) {
			this.showStatus("Move baixo", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para baixo.");
		}

		temp = tab.moveVazioParaEsquerda(tab);
		if (temp != null) {
			this.showStatus("Move para esquerda", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para esquerda.");
		}

		temp = tab.moveVazioParaEsquerda(tab);
		if (temp != null) {
			this.showStatus("Move para esquerda", temp.getTabuleiro());
		} else {
			System.out.println("Impossivel mover para esquerda.");
		}

	}

	public void showStatus(String msg, int[][] estado) {

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