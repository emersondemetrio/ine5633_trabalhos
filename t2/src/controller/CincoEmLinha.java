package controller;

import java.util.Random;

import javax.swing.JOptionPane;

import model.Tabuleiro;


public class CincoEmLinha {


	public static void main(String[] args) {
		Tabuleiro tab = new Tabuleiro();
		boolean vezDoComputador = true;
		int computador = 1;
		int jogador = 2;

		int countDie = 10;

		while(countDie > 0){
			if(vezDoComputador){
				Random r = new Random();
				int linha = r.nextInt(15);
				int coluna = r.nextInt(15);
				tab.setaPeao(linha, coluna, computador);

			} else {
				String stgLinha = JOptionPane.showInputDialog("Escolha uma linha");
				int linha = Integer.parseInt(stgLinha);
				String stgColuna = JOptionPane.showInputDialog("Escolha uma coluna");
				int coluna = Integer.parseInt(stgColuna);
				tab.setaPeao(linha, coluna, jogador);
			}

			countDie--;
			tab.showStatus("Teste");
		}



	}


}
