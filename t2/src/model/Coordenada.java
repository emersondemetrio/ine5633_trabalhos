package model;

public class Coordenada {

	protected int ZERO = 0;

	public boolean ehExpansivel(int tabuleiro[][], int linha, int coluna) {
		return jogadaPossivelEsquerda(tabuleiro, linha, coluna)
				|| jogadaPossivelDireita(tabuleiro, linha, coluna)
				|| jogadaPossivelCima(tabuleiro, linha, coluna)
				|| jogadaPossivelDiagonalDirBaixo(tabuleiro, linha, coluna)
				|| jogadaPossivelDiagonalEsqCima(tabuleiro, linha, coluna)
				|| jogadaPossivelDiagonalEsqBaixo(tabuleiro, linha, coluna)
				|| jogadaPossivelDiagonalDirCima(tabuleiro, linha, coluna)
				|| jogadaPossivelDiagonalDirBaixo(tabuleiro, linha, coluna);
	}

	public boolean jogadaPossivelDireita(int[][] tabuleiro, int linha,
			int coluna) {
		System.out.println("entrei");
		try {
			System.out.println("try");
			return tabuleiro[linha][coluna + 1] == ZERO ? true : false;
		} catch (Exception npe) {
			System.out.println("catch" + npe);
			return false;
		}
	}

	public boolean jogadaPossivelEsquerda(int[][] tabuleiro, int linha,
			int coluna) {
		try {
			return tabuleiro[linha][coluna - 1] == ZERO ? true : false;
		} catch (Exception npe) {
			return false;
		}
	}

	public boolean jogadaPossivelCima(int[][] tabuleiro, int linha, int coluna) {
		try {
			return tabuleiro[linha - 1][coluna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false;
		}
	}

	public boolean jogadaPossivelBaixo(int[][] tabuleiro, int linha, int coluna) {
		try {
			return tabuleiro[linha + 1][coluna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false;
		}
	}

	public boolean jogadaPossivelDiagonalEsqCima(int[][] tabuleiro, int linha,
			int coluna) {
		try {
			int novaLinha = linha - 1;
			int novaColuna = coluna - 1;

			return tabuleiro[novaLinha][novaColuna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false; // borda
		}
	}

	public boolean jogadaPossivelDiagonalDirCima(int[][] tabuleiro, int linha,
			int coluna) {
		try {
			int novaLinha = linha - 1;
			int novaColuna = coluna + 1;

			return tabuleiro[novaLinha][novaColuna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false; // borda
		}
	}

	public boolean jogadaPossivelDiagonalEsqBaixo(int[][] tabuleiro, int linha,
			int coluna) {
		try {
			int novaLinha = linha + 1;
			int novaColuna = coluna - 1;

			return tabuleiro[novaLinha][novaColuna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false; // borda
		}
	}

	public boolean jogadaPossivelDiagonalDirBaixo(int[][] tabuleiro, int linha,
			int coluna) {
		try {

			int novaLinha = linha + 1;
			int novaColuna = coluna + 1;

			return tabuleiro[novaLinha][novaColuna] == ZERO ? true : false;
		} catch (Exception npe) {
			return false; // borda
		}
	}
}
