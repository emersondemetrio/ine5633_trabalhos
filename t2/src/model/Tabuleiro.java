package model;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {

	protected int[][] tabuleiro = new int[15][15];
	protected List<int[]> posicoesOcupadas = new ArrayList<>();


	public void setaPeao(int linha, int coluna, int jogador){
		tabuleiro[linha][coluna] = jogador;
	}

	public int[][] getVizinhos(int linha, int coluna ){
		return null;
	}

	public void showStatus(String msg) {

		String output = "";
		for (int i = 0; i < this.tabuleiro.length; i++) {
			for (int j = 0; j < this.tabuleiro.length; j++) {
				if(j == 0)
					output += "Linha  "+i + "   ";
				if(this.tabuleiro[i][j] == 0 ){
					output += " | ";
				}else{
					output += " |" + this.tabuleiro[i][j] + "";
				}
			}
			output += "\n";
		}
		System.out.println("\n  ---" + msg + "---\n" + output);
	}

}
