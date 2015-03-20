package model;

import java.util.Arrays;

public class Item {
	protected int[] posicaoObjetivo;
	protected int[] posicaoAtual;
	protected int valor;

	public Item(int posicaoAtual[], int novoValor) {
		this.posicaoObjetivo = new int[] { -1, -1 };
		this.posicaoAtual = posicaoAtual;
		this.valor = novoValor;
	}

	public int[] getPosicalObjetivo() {
		int[] error = new int[] { -1, -1 };

		if (!Arrays.equals(error, this.posicaoObjetivo)) {
			return this.posicaoObjetivo;
		} else {
			int[] objetivo;
			switch (this.valor) {
			case 0:
				objetivo = new int[] { 0, 0 };
				break;
			case 1:
				objetivo = new int[] { 0, 1 };
				break;
			case 2:
				objetivo = new int[] { 0, 2 };
				break;

			case 3:
				objetivo = new int[] { 1, 0 };
				break;
			case 4:
				objetivo = new int[] { 1, 1 };
				break;
			case 5:
				objetivo = new int[] { 1, 2 };
				break;

			case 6:
				objetivo = new int[] { 2, 0 };
				break;
			case 7:
				objetivo = new int[] { 2, 1 };
				break;
			case 8:
				objetivo = new int[] { 2, 2 };
				break;

			default:
				objetivo = error;
				break;
			}
			
			return objetivo;
		}
	}

	public void setPosicaoAtual(int[] p) {
		this.posicaoAtual = p;
	}

	public int[] getPosicaoAtual() {
		return posicaoAtual;
	}

	public void setPosicaoObjetivo(int[] posicao) {
		this.posicaoObjetivo = posicao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
