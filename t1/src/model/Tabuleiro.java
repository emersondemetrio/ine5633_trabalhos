package model;

public class Tabuleiro {

	protected Item[][] tabuleiro;

	public Item[][] getTabuleiro() {
		return this.tabuleiro;
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		this.tabuleiro = new Item[estadoInicial.length][estadoInicial[0].length];
		
		for (int i = 0; i < estadoInicial.length; i++) {
			for (int j = 0; j < estadoInicial[0].length; j++) {
				this.tabuleiro[i][j] = new Item(new int[]{i,j}, estadoInicial[i][j]);
			}
		}
	}

	public int[][] getEstadoObjetivo() {
		int[][] objetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		return objetivo;
	}
}
