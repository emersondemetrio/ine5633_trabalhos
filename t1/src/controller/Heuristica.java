package controller;

import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import model.Movimentos;
import model.Tabuleiro;

public class Heuristica {

	SortedMap<Integer, Tabuleiro> fronteirasExpandidas = new TreeMap<Integer, Tabuleiro>();
	SortedMap<Integer, Tabuleiro> fronteirasVisitadas = new TreeMap<Integer, Tabuleiro>();

	boolean fimDeJogo = false;

	public boolean resolverRecursao(int nivel, Tabuleiro estado) {

		if (nivel == 0) {
			fronteirasVisitadas.put(Integer.MAX_VALUE, estado);
		}

		int nivelLocal = nivel;
		Tabuleiro tabAtual = null;

		nivelLocal++;
		expandirFronteiras(nivelLocal, estado);

		// busca o menor na lista
		Map.Entry<Integer, Tabuleiro> entry = fronteirasExpandidas.entrySet().iterator().next();
		int key = entry.getKey();
		tabAtual = fronteirasExpandidas.get(key); // menor C + H

		fronteirasExpandidas.remove(key, tabAtual);
		fronteirasVisitadas.put(key, tabAtual);


		if (Tabuleiro.compareTabuleiros(tabAtual.getTabuleiro(), Tabuleiro.getEstadoObjetivo())) {
			//Puzzle.showStatus("Nivel " + nivelLocal, tabAtual.getTabuleiro());
			//System.out.println("Parabens, voce ganhou o jogo!");
			fimDeJogo = true;
			mostraCaminho(tabAtual);
			return true;
		} else {
			if (resolverRecursao(nivelLocal, tabAtual)) {
				//Puzzle.showStatus("Nivel " + nivelLocal, tabAtual.getTabuleiro());
				return true;
			} else {
				return false;
			}

		}

	}

	private void mostraCaminho(Tabuleiro tabAtual) {
		Tabuleiro tab = tabAtual;
		Puzzle.showStatus("Caminho", tab.getTabuleiro());
		while(tab.getPai() != null){
			Puzzle.showStatus("Caminho", tab.getPai().getTabuleiro());
			tab = tab.getPai();
		}

	}

	private void expandirFronteiras(int nivel, Tabuleiro estado) {
		Tabuleiro temp = null;
		Tabuleiro fachada = new Tabuleiro();
		fachada.setEstadoInicial(estado.getTabuleiro());
		int heuristica = 0;
		int total = 0;

		temp = moveVazioParaCima(fachada);
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			total = nivel + heuristica;
			if (!fronteirasExpandidas.containsValue(temp) && fronteirasVisitadas.containsValue(temp) == false) {
				temp.setPai(estado);
				// Puzzle.showStatus("Cima: "+total, temp.getTabuleiro());
				fronteirasExpandidas.put(total, temp);
			}
		}
		fachada.setEstadoInicial(estado.getTabuleiro());
		temp = moveVazioParaEsquerda(fachada);
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			total = nivel + heuristica;
			if (!fronteirasExpandidas.containsValue(temp) && fronteirasVisitadas.containsValue(temp) == false) {
				temp.setPai(estado);
				// Puzzle.showStatus("Esquerda: "+total, temp.getTabuleiro());
				fronteirasExpandidas.put(total, temp);
			}
		}
		fachada.setEstadoInicial(estado.getTabuleiro());
		temp = moveVazioParaBaixo(fachada);
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			total = nivel + heuristica;
			if (!fronteirasExpandidas.containsValue(temp) && fronteirasVisitadas.containsValue(temp) == false) {
				temp.setPai(estado);
				// Puzzle.showStatus("Baixo: "+total, temp.getTabuleiro());
				fronteirasExpandidas.put(total, temp);
			}
		}
		fachada.setEstadoInicial(estado.getTabuleiro());
		temp = moveVazioParaDireita(fachada);
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			total = nivel + heuristica;
			if (!fronteirasExpandidas.containsValue(temp) && fronteirasVisitadas.containsValue(temp) == false) {
				temp.setPai(estado);
				// Puzzle.showStatus("Direita: "+total, temp.getTabuleiro());
				fronteirasExpandidas.put(total, temp);
			}
		}
	}

	public Tabuleiro moveVazioParaCima(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		// achar a posicao[linha, coluna] do espaco vazio (valor = 0)
		int[] posicaoVazio = encontraPosicao(tabuleiro.getTabuleiro(), 0);

		// se posicaoVazio not linha zero ent�o move
		if (verificaSeMovimentoDoVazioEhValido(Movimentos.CIMA, posicaoVazio)) {
			// cria novo tabuleiro
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(tabuleiro.getTabuleiro());
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
			trocaPecas(novoTab, Movimentos.CIMA, posicaoVazio, valorAcimaDoVazio);

		}
		// senhao retorna tabuleiro invalido

		return novoTab;
	}

	public Tabuleiro moveVazioParaBaixo(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = encontraPosicao(tabuleiro.getTabuleiro(), 0);

		if (verificaSeMovimentoDoVazioEhValido(Movimentos.BAIXO, posicaoVazio)) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(tabuleiro.getTabuleiro());
			int valorAbaixoDoVazio = novoTab.getTabuleiro()[posicaoVazio[0] + 1][posicaoVazio[1]];

			trocaPecas(novoTab, Movimentos.BAIXO, posicaoVazio, valorAbaixoDoVazio);
		}

		return novoTab;
	}

	public Tabuleiro moveVazioParaEsquerda(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = encontraPosicao(tabuleiro.getTabuleiro(), 0);
		if (verificaSeMovimentoDoVazioEhValido(Movimentos.ESQUERDA, posicaoVazio)) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(tabuleiro.getTabuleiro());
			int linha = posicaoVazio[0];
			int coluna = posicaoVazio[1];

			int valorAEsquerdaDoVazio = novoTab.getTabuleiro()[linha][coluna - 1];
			trocaPecas(novoTab, Movimentos.ESQUERDA, posicaoVazio, valorAEsquerdaDoVazio);
		}

		return novoTab;
	}

	public Tabuleiro moveVazioParaDireita(Tabuleiro tabuleiro) {
		Tabuleiro novoTab = null;

		int[] posicaoVazio = this.encontraPosicao(tabuleiro.getTabuleiro(), 0);

		boolean jogadaValida = verificaSeMovimentoDoVazioEhValido(Movimentos.DIREITA, posicaoVazio);

		if (jogadaValida) {
			novoTab = new Tabuleiro();
			novoTab.setEstadoInicial(tabuleiro.getTabuleiro());
			int linha = posicaoVazio[0];
			int coluna = posicaoVazio[1];

			int valorADireitaDoVazio = novoTab.getTabuleiro()[linha][coluna + 1];

			trocaPecas(novoTab, Movimentos.DIREITA, posicaoVazio, valorADireitaDoVazio);
		}

		return novoTab;
	}

	private void trocaPecas(Tabuleiro tabuleiro, int direcao, int[] posicaoVazio, int valorSubstituto) {
		switch (direcao) {

		case Movimentos.CIMA:
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			tabuleiro.getTabuleiro()[posicaoVazio[0] - 1][posicaoVazio[1]] = 0;
			break;

		case Movimentos.BAIXO:
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			tabuleiro.getTabuleiro()[posicaoVazio[0] + 1][posicaoVazio[1]] = 0;
			break;

		case Movimentos.ESQUERDA:
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1] - 1] = 0;
			break;

		case Movimentos.DIREITA:
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1]] = valorSubstituto;
			tabuleiro.getTabuleiro()[posicaoVazio[0]][posicaoVazio[1] + 1] = 0;
			break;

		default:
			break;
		}
	}

	public boolean verificaSeMovimentoDoVazioEhValido(int movimento, int[] posicaoVazio) {

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

	static void showList(String s, SortedMap<Integer, Tabuleiro> list) {
		for (Entry<Integer, Tabuleiro> entry : list.entrySet()) {
			Integer key = entry.getKey();
			Tabuleiro value = entry.getValue();
			Puzzle.showStatus(s + " [ " + key + " ]", value.getTabuleiro());
		}
	}

}
