package model;

import java.util.Arrays;


public class Tabuleiro {

	protected int[][] tabuleiro;
	protected int valorHeuristicaTabuleiro;

	public int[][] getTabuleiro() {
		return this.tabuleiro;
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		//this.tabuleiro = estadoInicial;
		int[][] novoEstado = new int[estadoInicial.length][];
		for (int i = 0; i < estadoInicial.length; i++) {
			novoEstado[i] = Arrays.copyOf(estadoInicial[i], estadoInicial[i].length);
		}
		this.tabuleiro = novoEstado;
	}


	public void setEstado(int[][] estado){
		final int[][] novoEstado = new int[estado.length][];
		for (int i = 0; i < estado.length; i++) {
			System.arraycopy(estado[i], 0, novoEstado[i], 0, estado[i].length);
		}
		this.tabuleiro = novoEstado;
	}

	public int calcularHeuristicaTabuleiro() {

		int valorHeuristica = 0;
		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				valorHeuristica += this.calcularDistanciaItemOrigem(
						tabuleiro[i][j], i, j);
			}
		}

		return valorHeuristica;
	}

	protected int calcularDistanciaItemOrigem(int item, int linha, int coluna) {

		int[] posObjetivo = this.getPosicalObjetivo(item);

		int numeroPassosAteObjetivo = Math.abs((posObjetivo[0]) - (linha))
				+ Math.abs((posObjetivo[1]) - (coluna));

		return numeroPassosAteObjetivo;
	}

	public static int[][] getEstadoObjetivo() {
		return new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	}



	public int[] getPosicalObjetivo(int valor) {
		int[] error = new int[] { -1, -1 };

		int[] objetivo;
		switch (valor) {

		case 1:
			objetivo = new int[] { 0, 0 };
			break;
		case 2:
			objetivo = new int[] { 0, 1 };
			break;
		case 3:
			objetivo = new int[] { 0, 2 };
			break;

		case 4:
			objetivo = new int[] { 1, 0 };
			break;
		case 5:
			objetivo = new int[] { 1, 1 };
			break;
		case 6:
			objetivo = new int[] { 1, 2 };
			break;

		case 7:
			objetivo = new int[] { 2, 0 };
			break;
		case 8:
			objetivo = new int[] { 2, 1 };
			break;
		case 0:
			objetivo = new int[] { 2, 2 };
			break;

		default:
			objetivo = error;
			break;
		}

		return objetivo;

	}

	static void debug(String s) {
		System.out.println(s);
	}

	public static boolean compareTabuleiros(int[][] umTab, int[][] outroTab) {

		for (int i = 0; i < umTab.length; i++) {
			for (int j = 0; j < umTab[0].length; j++) {
				int atualUmtab = umTab[i][j];
				int atualOutroTab = outroTab[i][j];

				if (atualUmtab != atualOutroTab) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(tabuleiro);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tabuleiro other = (Tabuleiro) obj;

		if (!Arrays.deepEquals(tabuleiro, other.tabuleiro))
			return false;
		if(!(Tabuleiro.compareTabuleiros(this.getTabuleiro(), other.getTabuleiro()))){
			return false;
		}

		System.out.println("Objeto igual encontrado na lista");
		return true;
	}


}
