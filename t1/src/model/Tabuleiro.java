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

	public static void log(String s) {
		System.out.println(s);
	}

	public int[] getPosicalObjetivo(int valor) {
		int[] error = new int[] { -1, -1 };

		int[] objetivo;
		switch (valor) {
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

	public Tabuleiro moveVazioParaCima(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		// achar a posicao[linha, coluna] do espaco vazio (valor = 0)
		int[] posicaoVazio = this.encontraPosicao(this.tabuleiro, 0);

		// se posicaoVazio not linha zero então move
		if (verificaSeMovimentoDoVazioEhValido(Movimentos.CIMA, posicaoVazio)) {
			// cria novo tabuleiro
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			// guarda valor acima do vazio
			int valorAcimaDoVazio = novoTab.getTabuleiro()[posicaoVazio[0] + 1][posicaoVazio[1]];
			// colocar o valor acima dentro da posicao vazia
			novoTab.trocaPecas(posicaoVazio, valorAcimaDoVazio);

		}
		// senhao retorna tabuleiro invalido

		return novoTab;
	}

	public Tabuleiro moveVazioParaBaixo() {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = this.encontraPosicao(novoTab.tabuleiro, 0);

		if (verificaSeMovimentoDoVazioEhValido(Movimentos.BAIXO, posicaoVazio)) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(this.getTabuleiro());
			int valorAbaixoDoVazio = novoTab.getTabuleiro()[posicaoVazio[0] - 1][posicaoVazio[1]];
			novoTab.trocaPecas(posicaoVazio, valorAbaixoDoVazio);
		}

		return novoTab;
	}

	private void trocaPecas(int[] posicaoVazio, int valorAcimaDoVazio) {

		this.tabuleiro[posicaoVazio[0]][posicaoVazio[1]] = valorAcimaDoVazio;
		// move o espaço vazio para cima
		this.tabuleiro[posicaoVazio[0] + 1][posicaoVazio[1]] = 0;
	}

	public boolean verificaSeMovimentoDoVazioEhValido(int movimento,
			int[] posicaoVazio) {

		switch (movimento) {
		case Movimentos.CIMA:
			return posicaoVazio[0] != 0;
		case Movimentos.BAIXO:
			return posicaoVazio[0] != 2;
		case Movimentos.DIREITA:
			return posicaoVazio[1] != 2;
		case Movimentos.ESQUERDA:
			return posicaoVazio[1] != 0;

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
}
