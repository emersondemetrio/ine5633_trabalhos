package model;

public class Tabuleiro {

	protected int[][] tabuleiro;
	protected int valorHeuristicaTabuleiro;

	public int[][] getTabuleiro() {
		return this.tabuleiro;
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		this.tabuleiro = estadoInicial;

		calcularHeuristicaTabuleiro();
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

	public int[][] getEstadoObjetivo() {
		int[][] objetivo = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		return objetivo;
	}

	public Tabuleiro moveVazioParaCima(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		// achar a posicao[linha, coluna] do espaco vazio (valor = 0)
		int[] posicaoVazio = this.encontraPosicao(this.tabuleiro, 0);

		// se posicaoVazio not linha zero ent�o move
		if (verificaSeMovimentoDoVazioEhValido(Movimentos.CIMA, posicaoVazio)) {
			// cria novo tabuleiro
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			// guarda valor acima do vazio
			int linha = posicaoVazio[0];
			int coluna = posicaoVazio[1];

			int valorAcimaDoVazio = novoTab.getTabuleiro()[linha - 1][coluna];
			// colocar o valor acima dentro da posicao vazia

			/*
			 * @Change Antes o metodo trocaPecas recebia apenas posicaoVazio e o
			 * valor. Eh necessario enviar tbm a direcao para que nao cause
			 * nullPointerException na hora da troca.
			 */
			novoTab.trocaPecas(Movimentos.CIMA, posicaoVazio, valorAcimaDoVazio);

		}
		// senhao retorna tabuleiro invalido

		return novoTab;
	}

	public Tabuleiro moveVazioParaBaixo(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = this.encontraPosicao(this.tabuleiro, 0);

		if (verificaSeMovimentoDoVazioEhValido(Movimentos.BAIXO, posicaoVazio)) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			int valorAbaixoDoVazio = novoTab.getTabuleiro()[posicaoVazio[0] + 1][posicaoVazio[1]];

			novoTab.trocaPecas(Movimentos.BAIXO, posicaoVazio,
					valorAbaixoDoVazio);
		}

		return novoTab;
	}

	public Tabuleiro moveVazioParaEsquerda(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = this.encontraPosicao(this.tabuleiro, 0);
		if (verificaSeMovimentoDoVazioEhValido(Movimentos.ESQUERDA,
				posicaoVazio)) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			int linha = posicaoVazio[0];
			int coluna = posicaoVazio[1];

			int valorAEsquerdaDoVazio = novoTab.getTabuleiro()[linha][coluna - 1];
			novoTab.trocaPecas(Movimentos.ESQUERDA, posicaoVazio,
					valorAEsquerdaDoVazio);
		}

		return novoTab;
	}

	public Tabuleiro moveVazioParaDireita(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = this.encontraPosicao(this.tabuleiro, 0);

		boolean jogadaValida = verificaSeMovimentoDoVazioEhValido(
				Movimentos.DIREITA, posicaoVazio);

		if (jogadaValida) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			int linha = posicaoVazio[0];
			int coluna = posicaoVazio[1];

			int valorADireitaDoVazio = novoTab.getTabuleiro()[linha][coluna + 1];
			
			novoTab.trocaPecas(Movimentos.DIREITA, posicaoVazio,
					valorADireitaDoVazio);
		}

		return novoTab;
	}

	private void trocaPecas(int direcao, int[] posicaoVazio,
			int valorSubstituto) {
		switch (direcao) {

		case Movimentos.CIMA:
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			this.tabuleiro[posicaoVazio[0] - 1][posicaoVazio[1]] = 0;
			break;

		case Movimentos.BAIXO:
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			this.tabuleiro[posicaoVazio[0] + 1][posicaoVazio[1]] = 0;
			break;

		case Movimentos.ESQUERDA:
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1] - 1] = 0;
			break;

		case Movimentos.DIREITA:
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			this.tabuleiro[posicaoVazio[0]][posicaoVazio[1] + 1] = 0;
			break;

		default:
			break;
		}
	}

	public boolean verificaSeMovimentoDoVazioEhValido(int movimento,
			int[] posicaoVazio) {

		int linha = posicaoVazio[0];
		int coluna = posicaoVazio[1];

		switch (movimento) {
		case Movimentos.CIMA:
			return linha != 0;

		case Movimentos.BAIXO:
			return linha != 2;

		case Movimentos.DIREITA:
			return coluna != 2;

		case Movimentos.ESQUERDA:
			return coluna != 0;

		default:
			return false;
		}
	}

	public int[] encontraPosicao(int[][] tabuleiro, int valor) {

		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {

				if (tabuleiro[i][j] == valor) {
					return new int[] { i, j };
				}
			}
		}
		return new int[] { -1, -1 };
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
}
