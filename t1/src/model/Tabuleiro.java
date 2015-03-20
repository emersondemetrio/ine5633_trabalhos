package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Tabuleiro {

	protected Item[][] tabuleiro;
	protected Fronteiras memoria;
	protected Map<Integer, Integer> memoriaLocal = new HashMap<Integer, Integer>();

	public Item[][] getTabuleiro() {
		return this.tabuleiro;
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		this.tabuleiro = new Item[estadoInicial.length][estadoInicial[0].length];

		for (int i = 0; i < estadoInicial.length; i++) {
			for (int j = 0; j < estadoInicial[0].length; j++) {
				Item temp = new Item(new int[] { i, j }, estadoInicial[i][j]);
				this.tabuleiro[i][j] = temp;
			}
		}

		calulcarDistancias();
	}

	public void calulcarDistancias() {
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				this.calcularDistanciaItemOrigem(tabuleiro[i][j]);
			}
		}

		showMemory();
	}

	protected void calcularDistanciaItemOrigem(Item item) {

		int[] posObjetivo = item.getPosicalObjetivo();
		int[] posAtual = item.getPosicaoAtual();

		int somaObjetivo = posObjetivo[0] + posObjetivo[1];
		int somaAtual = posAtual[0] + posAtual[1];
		int numeroPassosAteObjetivo = Math.abs((somaObjetivo) - (somaAtual));

		memoriaLocal.put(item.getValor(), numeroPassosAteObjetivo);
		log("OBS: Calculo errado! " + item.getValor() + " Passos: " + numeroPassosAteObjetivo);

	}

	public void showMemory() {
		System.out.println("\n");
		Set<Integer> keys = memoriaLocal.keySet();
		for (Integer i : keys) {
			log(i + " [ " + memoriaLocal.get(i) + " ]");
		}
	}

	public int[][] getEstadoObjetivo() {
		int[][] objetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		return objetivo;
	}

	public static void log(String s) {
		System.out.println(s);
	}
}
