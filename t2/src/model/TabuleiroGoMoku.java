package model;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.util.Arrays;


public class TabuleiroGoMoku extends Canvas {

	/**
	 * representacao do tabuleiro com seus estados
	 */
	int casas[][];
	static final int VAZIO = 0;
	static final int XIS = 1;
	static final int ZERO = 2;
	static final int BORDA = 3;

	// Tamanho do tabuleiro por numero de casas
	int nColunas;
	int nLinhas;

	// Estado do jogo
	int pecadDoAdversario = XIS;
	int numVez;
	int ganhador;

	// Quando qualquer lado vence eh armazendo as coordenadas dos 5 em linha
	int venceLinha1;
	int venceCol1;
	int venceLinha2;
	int venceCol2;

	// Vitorias pra cada lado
	int pontosX = 0;
	int pontosZero = 0;

	// informacoes de redimensionamento
	int x0;
	int y0;
	int larguraCasa;
	int alturaCasa;
	Color bgColor;
	Color fgColor;
	Dimension dimensaoMinima;

	// Local atual do cursor
	int colunaAtual;
	int linhaAtual;

	/**
	 * Cria o tabuleiro
	 * 
	 * @param nColunas
	 *            o numero de colunas do tabuleiro
	 * @param nLinhas
	 *            o numero de linhas do tabuleiro
	 * @param larguraCasa
	 *            largura individual de cada casa
	 * @param alturaCasa
	 *            altura individual de cada casa
	 */
	public TabuleiroGoMoku(int nColunas, int nLinhas, int larguraCasa, int alturaCasa) {


		this.nColunas = nColunas;
		this.nLinhas = nLinhas;
		this.larguraCasa = larguraCasa;
		this.alturaCasa = alturaCasa;
		dimensaoMinima = new Dimension(nColunas * larguraCasa + 1, nLinhas * alturaCasa
				+ 1);

		bgColor = (getBackground() == null) ? Color.white : getBackground();
		fgColor = (getForeground() == null) ? Color.black : getForeground();


		newGame();
	}

	/**
	 * Começa o jogo
	 */
	public void newGame() {
		casas = new int[nColunas][nLinhas];
		numVez = 0;
		ganhador = VAZIO;

		colunaAtual = nColunas / 2;
		linhaAtual = nLinhas / 2;

		if (pecadDoAdversario == ZERO) {
			nextTurn();
		}
		repaint();
	}

	/**
	 * Inverte posicoes
	 */
	public void invertePosicoes() {
		pecadDoAdversario = (pecadDoAdversario == XIS) ? ZERO : XIS;
		pontosX = pontosZero = 0;
		nextTurn();
		repaint();
	}

	/**
	 * vez do computador
	 */
	public void nextTurn() {
		if (ganhador != VAZIO)
			return; // jogo acabou

		int melhorLinha = -1;
		int melhorColuna = -1;
		int melhorNota = -1;

		if (numVez == 0) {
			// start in the center
			melhorLinha = nLinhas / 2;
			melhorColuna = nColunas / 2;
		} else {
			for (int i = nColunas - 1; i >= 0; i--)
				for (int j = nLinhas - 1; j >= 0; j--)
					if (casas[i][j] == VAZIO) {
						int avaliacao = calculaValorDaCasa(i, j);
						if (avaliacao > melhorNota) {
							melhorNota = avaliacao;
							melhorColuna = i;
							melhorLinha = j;
						}
					}
		}

		setaCasa(melhorColuna, melhorLinha, pecadDoAdversario == ZERO ? XIS : ZERO);
	}

	/**
	 * Recupera evento do mouse
	 */
	@Override
	public boolean mouseDown(Event event, int x, int y) {
		if (ganhador != VAZIO)
			return false; // game is already over

		int i = x / larguraCasa;
		int j = y / alturaCasa;

		if (casas[i][j] == VAZIO) {
			setaCasa(i, j, pecadDoAdversario);
			nextTurn();
		}

		return false;
	}



	/**
	 * Desenha o tabuleiro
	 */
	@Override
	public void paint(Graphics g) {
		int largura = nColunas * larguraCasa;
		int altura = nLinhas * alturaCasa;

		// Draw grid
		g.setColor(Color.blue);
		for (int i = nColunas; i >= 0; i--)
			g.drawLine(x0 + i * larguraCasa, y0, x0 + i * larguraCasa, y0 + altura);
		for (int j = nLinhas; j >= 0; j--)
			g.drawLine(x0, y0 + j * alturaCasa, x0 + largura, y0 + j * alturaCasa);

		// Draw pieces
		g.setColor(fgColor);
		for (int i = nColunas - 1; i >= 0; i--)
			for (int j = nLinhas - 1; j >= 0; j--)
				switch (casas[i][j]) {
				case VAZIO:
					// int eval = evaluate( i,j );
					// g.drawString( ""+eval, i*cellWidth + 1, j*cellHeight -9
					// );
					break;

				case XIS:
					g.drawLine(i * larguraCasa + 2 + x0, j * alturaCasa + 2 + y0,
							(i + 1) * larguraCasa - 2 + x0, (j + 1) * alturaCasa
							- 2 + y0);
					g.drawLine((i + 1) * larguraCasa - 2 + x0, j * alturaCasa + 2
							+ y0, i * larguraCasa + 2 + x0, (j + 1) * alturaCasa
							- 2 + y0);
					break;

				case ZERO:
					g.drawOval(i * larguraCasa + 2 + x0, j * alturaCasa + 2 + y0,
							larguraCasa - 4, alturaCasa - 4);
					break;

				default:
					// error!
					g.fillRect(i * larguraCasa + 1 + x0, j * alturaCasa + 1 + y0,
							larguraCasa - 1, alturaCasa - 1);
				}

		// desenha linha do ganhador
		if (ganhador != VAZIO) {
			g.setColor(Color.red);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2, venceLinha1
					* alturaCasa + alturaCasa / 2, venceCol2 * larguraCasa
					+ larguraCasa / 2, venceLinha2 * alturaCasa + alturaCasa / 2);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2 + 1, venceLinha1
					* alturaCasa + alturaCasa / 2, venceCol2 * larguraCasa
					+ larguraCasa / 2 + 1, venceLinha2 * alturaCasa + alturaCasa / 2);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2, venceLinha1
					* alturaCasa + alturaCasa / 2 + 1, venceCol2 * larguraCasa
					+ larguraCasa / 2, venceLinha2 * alturaCasa + alturaCasa / 2 + 1);

		}
	}

	/**
	 * Coloca peça em determinada casa (linha e coluna)
	 */
	protected void setaCasa(int col, int row, int val) {
		numVez++;
		casas[col][row] = val;
		if (verificaVencedor(col, row)) {
			repaint(); 
		} else {
			repaint(col * larguraCasa + 1, row * alturaCasa + 1, larguraCasa - 1,
					alturaCasa - 1);
		}
	}

	// Delta coordinates for S, SE, E, and SW directions.
	int dx[] = { 0, -1, -1, -1 };
	int dy[] = { -1, -1, 0, 1 };

	/**
	 * Obtém os valores da casa para uma linha de dez peças - cinco de cada lado da casa
	 *em determinada linha e coluna, orientado em uma determinada direção. Existem quatro
	 *possíveis direções: horizontal, vertical, e duas diagonais. para
	 *distinguir entre diagonais, vamos chamar direções S, SE, E, e SW
	 *(Pense em um azimute).
	 * 
	 */
	int[] buscaLinha(int col, int row, int direction) {
		int line[] = new int[10];

		// Primeiro busca a parte sul da linha
		int i = col;
		int j = row;

		for (int k = 4; k >= 0; k--) {
			i += dx[direction];
			j += dy[direction];

			if (i >= 0 && j >= 0 && i < nColunas && j < nLinhas) // are we on board?
				line[k] = casas[i][j];
			else
				line[k] = BORDA;
		}

		// Depois busca a parte norte
		i = col;
		j = row;

		for (int k = 5; k <= 9; k++) {
			i -= dx[direction];
			j -= dy[direction];

			if (i >= 0 && j >= 0 && i < nColunas && j < nLinhas) // are we on board?
				line[k] = casas[i][j];
			else
				line[k] = BORDA;
		}

		return line;
	}

	/**
	 * Calcula o valor de uma linha para determinado jogador
	 * 
	 * @param linha
	 *            a linha retornada pelo getLinha
	 * @param peca
	 *            se estamos calculando para cruzes ou zeros
	 * @return valor nota
	 */
	int getValorLinha(int[] linha, int peca) {
		int melhor = 0;
		int primeiro = 0;
		int ultimo = 0;
		int espacos = 0;
		boolean perto = false;

		for (int i = 1; i < 6; i++) {
			primeiro = 0;
			ultimo = 0;
			espacos = 0;
			perto = false;
			int s = i;

			while (espacos < 4 && s < i + 4) {
				if (linha[s] == VAZIO) {
					espacos++;
				} else if (linha[s] == peca) {
					ultimo = s;
					if (primeiro == 0)
						primeiro = s;
					perto |= (s == 4 || s == 5);
				} else {
					espacos = 4;
				}

				s++;

			} // enquanto

			int dg = 4 - espacos;
			int eval = dg * dg;

			if (perto) // proxima celula eh ocupada com uma peca igual
				eval++;

			if (ultimo - primeiro < dg) // linha nao tem buracos
				eval++;

			if (linha[i - 1] != pecadDoAdversario
					&& // nao bloqueado
					linha[i + 4] != pecadDoAdversario && linha[i - 1] != BORDA
					&& linha[i + 4] != BORDA)
				eval++;

			if (eval > melhor)
				melhor = eval;

		} // for

		return melhor;
	}

	/**
	 * 
	 * Aloca um array para guardar os pesos das diferentes direcoes no metodo 
	 * de avaliacao para nao ter que realocar toda hora. 
	 * 
	 */
	int pesos[] = new int[4];

	/**
	 * Calcula um valor para uma dada casa
	 * HEURISTICA TA AQUI!
	 */
	int calculaValorDaCasa(int col, int lin) {
		for (int dir = 0; dir < 4; dir++) {
			int[] linha = buscaLinha(col, lin, dir);
			int meuPeso = getValorLinha(linha, pecadDoAdversario == ZERO ? XIS : ZERO) + 2;
			int seuPeso = getValorLinha(linha, pecadDoAdversario);
			pesos[dir] = Math.max(meuPeso, seuPeso) - 2;
		}
		Arrays.sort(pesos);
		return pesos[3] * 64 + pesos[2] * 16 + pesos[1] * 4 + pesos[0];
	}

	/**
	 * Verifica se o ultimo jogador ganhou o jogo e atualiza o placar
	 * 
	 * @param coluna
	 *            ultima coluna jogada
	 * @param linha
	 *            ultima linha jogada
	 */
	boolean verificaVencedor(int coluna, int linha) {
		int ultimoJogado = casas[coluna][linha];
		int dir = 0;
		int k;

		do {
			int line[] = buscaLinha(coluna, linha, dir);
			venceCol1 = coluna;
			venceLinha1 = linha;
			venceCol2 = coluna;
			venceLinha2 = linha;
			k = 1;

			for (int p = 4; p >= 0; p--)
				if (line[p] == ultimoJogado) {
					venceCol2 += dx[dir];
					venceLinha2 += dy[dir];
					k++;
				} else
					break;

			for (int p = 5; p <= 9; p++)
				if (line[p] == ultimoJogado) {
					venceCol1 -= dx[dir];
					venceLinha1 -= dy[dir];
					k++;
				} else
					break;

			dir++;
		} while (dir < 4 && k < 5);

		if (k < 5)
			return false; // no winner

		ganhador = ultimoJogado;

		if (ganhador == XIS)
			pontosX++;
		else
			pontosZero++;

		return true;
	}

	/**
	 * Recupera cadeia do placar
	 */
	String getScore() {
		return "Placar: Voce (" + (pecadDoAdversario == XIS ? 'X' : 'O') + "): "
				+ (pecadDoAdversario == XIS ? pontosX : pontosZero)
				+ ", Computer (" + (pecadDoAdversario != XIS ? 'X' : 'O') + "): "
				+ (pecadDoAdversario != XIS ? pontosX : pontosZero);
	}
};