package model;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	// geracao de filhos limitada
	// subtracao do calculo do adversario
	// resumir o relatorio

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
		dimensaoMinima = new Dimension(nColunas * larguraCasa + 1, nLinhas * alturaCasa + 1);

		bgColor = (getBackground() == null) ? Color.white : getBackground();
		fgColor = (getForeground() == null) ? Color.black : getForeground();

		newGame();
	}

	/**
	 * Comeca o jogo
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

		if (numVez == 0) {
			melhorLinha = nLinhas / 2;
			melhorColuna = nColunas / 2;
		} else {

			// MINIMAX
			int[] temp = melhorJogada(casas, numVez);
			melhorColuna = temp[0];
			melhorLinha = temp[1];

			// for (int i = nColunas - 1; i >= 0; i--)
			// for (int j = nLinhas - 1; j >= 0; j--)
			// if (casas[i][j] == VAZIO) {
			// int avaliacao = calculaValorDaCasa(i, j);
			// if (avaliacao > melhorNota){
			// melhorNota = avaliacao; melhorColuna = i; melhorLinha = j;
			// }
			// }

		}

		setaCasa(melhorColuna, melhorLinha, ZERO);
	}




	private int[] melhorJogada(int[][] casas, int numVez) {
		int nivel = numVez;
		int[] melhorColunaElinha;
		Tabuleiro tabInicial = new Tabuleiro(casas);

		// retorna o melhor tabuleiro
		Tabuleiro melhorTabuleirosNivel5 = minimax(tabInicial);

		// recupera jogada feita para chegar ao melhor tabuleiro
		Tabuleiro aux = melhorTabuleirosNivel5;
		for (int i = 3; i > 1; i--) {
			aux = melhorTabuleirosNivel5.getPai();
			melhorTabuleirosNivel5 = aux;
		}

		melhorColunaElinha = aux.getJogadaOrigem();

		return melhorColunaElinha;

	}

	private Tabuleiro minimax(Tabuleiro tabInicial) {

		List<int[]> posicoesFronteira = getExpandiveis(tabInicial);

		// minimax(player,board)
		// if(game over in current board position)
		// return winner
		// children = all legal moves for player from this board

		tabInicial.setPosicoesFronteira(posicoesFronteira);
		tabInicial.setFilhos(criaFilhos(tabInicial, posicoesFronteira));

		for (Tabuleiro tab : tabInicial.getFilhos()) {
			if(tabInicial.getNivel() < 4){
				Tabuleiro teste = minimax(tab);
			} else {
				tabInicial.setValor(getMinMax(tabInicial));
			}

		}

		return null;
	}

	private float getMinMax(Tabuleiro teste) {
		if (teste.getNivel() % 2 != 0) { // max
			// return maximal score of calling minimax on all the
			// children
			Tabuleiro testeMaior = teste.getFilhos().get(1);
			for (Tabuleiro t : teste.getFilhos()) {
				calculaUtilidade(t);
				if (t.getValor() > testeMaior.getValor())
					testeMaior = t;
			}
			return testeMaior.getValor();
		} else { // min
			// return minimal score of calling minimax on all
			// the children
			Tabuleiro testeMenor = teste.getFilhos().get(1);
			for (Tabuleiro t : teste.getFilhos()) {
				calculaUtilidade(t);
				t.setValor(t.getValor() * -1);
				if (t.getValor() < testeMenor.getValor())
					testeMenor = t;
			}

			return testeMenor.getValor();
		}
	}

	// correcao para retornar os filhos gerados apenas a partir da jogada
	// anterior
	private List<int[]> getExpandiveisDosFilhos(Tabuleiro tabuleiro) {
		ArrayList<int[]> posicoesExpandiveis = new ArrayList<int[]>();
		int[] jogadaOrigem = tabuleiro.getJogadaOrigem();
		posicoesExpandiveis.addAll(ehExpansivel(jogadaOrigem[0], jogadaOrigem[1], tabuleiro.getTabuleiro()));

		return posicoesExpandiveis;
	}

	private void calculaUtilidade(Tabuleiro tab) {
		float utilidade = 1f;
		int[][] tabuleiro = tab.getTabuleiro();

		for (int i = 0; i < tabuleiro.length; i++) {
			for (int j = 0; j < tabuleiro[0].length; j++) {
				float valor = calculaValorDaCasa(i, j);
				if (valor != 0.0)
					utilidade += valor;

			}
		}

		tab.setValor(utilidade);
	}

	private List<Tabuleiro> criaFilhos(Tabuleiro tabuleiro, List<int[]> posicoesFronteira) {
		List<Tabuleiro> filhos = new ArrayList<Tabuleiro>();
		int[][] aux = copiaTabuleiro(tabuleiro.getTabuleiro());
		for (int[] fronteira : posicoesFronteira) {
			if (tabuleiro.getNivel() % 2 == 0) {
				aux[fronteira[0]][fronteira[1]] = ZERO;
			} else {
				aux[fronteira[0]][fronteira[1]] = XIS;
			}
			Tabuleiro tabFilho = new Tabuleiro(aux);
			tabFilho.setNivel(tabuleiro.getNivel() + 1);
			tabFilho.setPai(tabuleiro);
			tabFilho.setJogadaOrigem(new int[] { fronteira[0], fronteira[1] });
			filhos.add(tabFilho);
			aux = copiaTabuleiro(tabuleiro.getTabuleiro());

		}

		return filhos;
	}

	public int[][] copiaTabuleiro(int[][] estadoInicial) {
		// this.tabuleiro = estadoInicial;
		int[][] copiaTabuleiro = new int[estadoInicial.length][];
		for (int i = 0; i < estadoInicial.length; i++) {
			copiaTabuleiro[i] = Arrays.copyOf(estadoInicial[i], estadoInicial[i].length);
		}
		return copiaTabuleiro;
	}

	public List<int[]> getExpandiveis(Tabuleiro tab) {
		ArrayList<int[]> posicoesExpandiveis = new ArrayList<int[]>();

		if (tab.getNivel() == 0) {

			int[][] tabuleiro = tab.getTabuleiro();

			for (int i = 0; i < tabuleiro.length; i++) {
				for (int j = 0; j < tabuleiro[0].length; j++) {
					if (tabuleiro[i][j] != VAZIO) {
						posicoesExpandiveis.addAll(ehExpansivel(i, j, tabuleiro));
					}
				}
			}

		} else {
			int[] jogadaOrigem = tab.getJogadaOrigem();
			posicoesExpandiveis.addAll(ehExpansivel(jogadaOrigem[0], jogadaOrigem[1], tab.getTabuleiro()));
		}

		return posicoesExpandiveis;
	}

	public List<int[]> ehExpansivel(int linha, int coluna, int[][] tabuleiro) {
		ArrayList<int[]> posicoesExpandiveis = new ArrayList<int[]>();

		int[] moveParaDireita = moveParaDireita(linha, coluna, tabuleiro);
		if (moveParaDireita != null)
			posicoesExpandiveis.add(moveParaDireita);

		int[] moveParaEsquerda = moveParaEsquerda(linha, coluna, tabuleiro);
		if (moveParaEsquerda != null)
			posicoesExpandiveis.add(moveParaEsquerda);

		int[] moveParaCima = moveParaCima(linha, coluna, tabuleiro);
		if (moveParaCima != null)
			posicoesExpandiveis.add(moveParaCima);

		int[] moveParaBaixo = moveParaBaixo(linha, coluna, tabuleiro);
		if (moveParaBaixo != null)
			posicoesExpandiveis.add(moveParaBaixo);

		int[] moveParaDiagDirCima = moveParaDiagDirCima(linha, coluna, tabuleiro);
		if (moveParaDiagDirCima != null)
			posicoesExpandiveis.add(moveParaDiagDirCima);

		int[] moveParaDiagDirBaixo = moveParaDiagDirBaixo(linha, coluna, tabuleiro);
		if (moveParaDiagDirBaixo != null)
			posicoesExpandiveis.add(moveParaDiagDirBaixo);

		int[] moveParaDiagEsqCima = moveParaDiagEsqCima(linha, coluna, tabuleiro);
		if (moveParaDiagEsqCima != null)
			posicoesExpandiveis.add(moveParaDiagEsqCima);

		int[] moveParaDiagEsqBaixo = moveParaDiagEsqBaixo(linha, coluna, tabuleiro);
		if (moveParaDiagEsqBaixo != null)
			posicoesExpandiveis.add(moveParaDiagEsqBaixo);

		return posicoesExpandiveis;
	}

	private int[] moveParaDiagEsqBaixo(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha + 1][coluna - 1] == VAZIO) {
				posicaoExpandivel[0] = linha + 1;
				posicaoExpandivel[1] = coluna - 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaDiagDirBaixo(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha + 1][coluna + 1] == VAZIO) {
				posicaoExpandivel[0] = linha + 1;
				posicaoExpandivel[1] = coluna + 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaDiagDirCima(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha - 1][coluna + 1] == VAZIO) {
				posicaoExpandivel[0] = linha - 1;
				posicaoExpandivel[1] = coluna + 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaBaixo(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha + 1][coluna] == VAZIO) {
				posicaoExpandivel[0] = linha + 1;
				posicaoExpandivel[1] = coluna;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaCima(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha - 1][coluna] == VAZIO) {
				posicaoExpandivel[0] = linha - 1;
				posicaoExpandivel[1] = coluna;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaEsquerda(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha][coluna - 1] == VAZIO) {
				posicaoExpandivel[0] = linha;
				posicaoExpandivel[1] = coluna - 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaDireita(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha][coluna + 1] == VAZIO) {
				posicaoExpandivel[0] = linha;
				posicaoExpandivel[1] = coluna + 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	private int[] moveParaDiagEsqCima(int linha, int coluna, int[][] tabuleiro) {
		int[] posicaoExpandivel = new int[2];
		try {
			if (tabuleiro[linha - 1][coluna - 1] == VAZIO) {
				posicaoExpandivel[0] = linha - 1;
				posicaoExpandivel[1] = coluna - 1;
			}
		} catch (ArrayIndexOutOfBoundsException npe) {
			posicaoExpandivel = null;
		}
		return posicaoExpandivel;
	}

	/**
	 * Recupera evento do mouse
	 */
	@Override
	public boolean mouseDown(Event event, int x, int y) {
		if (ganhador != VAZIO)
			return false;

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

		g.setColor(Color.blue);
		for (int i = nColunas; i >= 0; i--)
			g.drawLine(x0 + i * larguraCasa, y0, x0 + i * larguraCasa, y0 + altura);
		for (int j = nLinhas; j >= 0; j--)
			g.drawLine(x0, y0 + j * alturaCasa, x0 + largura, y0 + j * alturaCasa);

		g.setColor(fgColor);
		for (int i = nColunas - 1; i >= 0; i--)
			for (int j = nLinhas - 1; j >= 0; j--)
				switch (casas[i][j]) {
				case VAZIO:
					break;

				case XIS:
					g.drawLine(i * larguraCasa + 2 + x0, j * alturaCasa + 2 + y0, (i + 1) * larguraCasa - 2 + x0,
							(j + 1) * alturaCasa - 2 + y0);
					g.drawLine((i + 1) * larguraCasa - 2 + x0, j * alturaCasa + 2 + y0, i * larguraCasa + 2 + x0,
							(j + 1) * alturaCasa - 2 + y0);
					break;

				case ZERO:
					g.drawOval(i * larguraCasa + 2 + x0, j * alturaCasa + 2 + y0, larguraCasa - 4, alturaCasa - 4);
					break;

				default:
					g.fillRect(i * larguraCasa + 1 + x0, j * alturaCasa + 1 + y0, larguraCasa - 1, alturaCasa - 1);
				}

		// desenha linha do ganhador
		if (ganhador != VAZIO) {
			g.setColor(Color.red);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2, venceLinha1 * alturaCasa + alturaCasa / 2, venceCol2
					* larguraCasa + larguraCasa / 2, venceLinha2 * alturaCasa + alturaCasa / 2);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2 + 1, venceLinha1 * alturaCasa + alturaCasa / 2,
					venceCol2 * larguraCasa + larguraCasa / 2 + 1, venceLinha2 * alturaCasa + alturaCasa / 2);
			g.drawLine(venceCol1 * larguraCasa + larguraCasa / 2, venceLinha1 * alturaCasa + alturaCasa / 2 + 1,
					venceCol2 * larguraCasa + larguraCasa / 2, venceLinha2 * alturaCasa + alturaCasa / 2 + 1);

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
			repaint(col * larguraCasa + 1, row * alturaCasa + 1, larguraCasa - 1, alturaCasa - 1);
		}
	}

	// Delta coordinates for S, SE, E, and SW directions.
	int dx[] = { 0, -1, -1, -1 };
	int dy[] = { -1, -1, 0, 1 };

	/**
	 * Obtém os valores da casa para uma linha de dez peças - cinco de cada lado
	 * da casa em determinada linha e coluna, orientado em uma determinada
	 * direção. Existem quatro possíveis direções: horizontal, vertical, e duas
	 * diagonais. para distinguir entre diagonais, vamos chamar direções S, SE,
	 * E, e SW (Pense em um azimute).
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

			if (i >= 0 && j >= 0 && i < nColunas && j < nLinhas) {
				// esta na
				// borda?
				line[k] = casas[i][j];
			} else
				line[k] = BORDA;
		}

		// Depois busca a parte norte
		i = col;
		j = row;

		for (int k = 5; k <= 9; k++) {
			i -= dx[direction];
			j -= dy[direction];

			if (i >= 0 && j >= 0 && i < nColunas && j < nLinhas) // esta na
				// borda?
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

			if (linha[i - 1] != pecadDoAdversario && // nao bloqueado
					linha[i + 4] != pecadDoAdversario && linha[i - 1] != BORDA && linha[i + 4] != BORDA)
				eval++;

			if (eval > melhor)
				melhor = eval;

		} // for

		return melhor;
	}

	/**
	 * 
	 * Aloca um array para guardar os pesos das diferentes direcoes no metodo de
	 * avaliacao para nao ter que realocar toda hora.
	 * 
	 */
	int pesos[] = new int[4];

	/**
	 * Calcula um valor para uma dada casa HEURISTICA TA AQUI!
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
				+ (pecadDoAdversario == XIS ? pontosX : pontosZero) + ", Computer ("
				+ (pecadDoAdversario != XIS ? 'X' : 'O') + "): " + (pecadDoAdversario != XIS ? pontosX : pontosZero);
	}
};