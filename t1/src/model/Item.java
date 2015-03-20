package model;

public class Item {
	protected int[] posicao;
	protected int valor;
	
	public Item(int novaPos[], int novoValor){
		this.posicao = novaPos;
		this.valor = novoValor;
	}

	public int[] getPosicao() {
		return posicao;
	}

	public void setPosicao(int[] posicao) {
		this.posicao = posicao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
