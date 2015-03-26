package controller;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import model.Tabuleiro;

// http://heuristicswiki.wikispaces.com/N+-+Puzzle
public class Heuristic {

	public static SortedMap<Integer, Tabuleiro> ABERTO = new TreeMap<Integer, Tabuleiro>();
	public static SortedMap<Integer, Tabuleiro> FECHADO = new TreeMap<Integer, Tabuleiro>();
	public static boolean ESTADO_FINAL = false;

	public void solveOne(Tabuleiro tabuleiro) {

		Tabuleiro X = null;
		Tabuleiro temp = null;
		int heuristica = 0;
		ABERTO.put(0, tabuleiro);

		int custoNivel = 0;
		while (ABERTO.isEmpty() == false && ESTADO_FINAL == false) {

			// busca o proximo que entrou na lista
			// deve buscar a menor heuristica
			Map.Entry<Integer, Tabuleiro> entry = ABERTO.entrySet().iterator().next();
			int key = entry.getKey();

			X = ABERTO.get(key);

			ABERTO.remove(key);

			if (verificaSeGanhou()) {
				System.out.println("SUCESSO");
				ESTADO_FINAL = true;
			} else {
				// gera filho
				FECHADO.put(custoNivel + heuristica, X);

				temp = X.moveVazioParaCima(X);
				if (temp != null) {
					// verificar se esse estado ja existe na fronteira (Aberto)
					// ou se ja foi percorrido(Fechado)se nao entao insere no
					// aberto
					// calcula heuristica, soma o nivel e popula
					heuristica = temp.calcularHeuristicaTabuleiro();
					ABERTO.put(custoNivel + heuristica, temp);
				}
				temp = X.moveVazioParaEsquerda(X);
				if (temp != null)
					// verificar se esse estado ja existe na fronteira (Aberto)
					// ou se ja foi percorrido(Fechado)se nao entao insere no
					// aberto
					// calcula heuristica, soma o nivel e popula
					heuristica = temp.calcularHeuristicaTabuleiro();
				ABERTO.put(custoNivel + heuristica, temp);
				temp = X.moveVazioParaBaixo(X);
				if (temp != null)
					// verificar se esse estado ja existe na fronteira (Aberto)
					// ou se ja foi percorrido(Fechado)se nao entao insere no
					// aberto
					// calcula heuristica, soma o nivel e popula
					heuristica = temp.calcularHeuristicaTabuleiro();
				ABERTO.put(custoNivel + heuristica, temp);
				temp = X.moveVazioParaDireita(X);
				if (temp != null)
					// verificar se esse estado ja existe na fronteira (Aberto)
					// ou se ja foi percorrido(Fechado)se nao entao insere no
					// aberto
					// calcula heuristica, soma o nivel e popula
					heuristica = temp.calcularHeuristicaTabuleiro();
				ABERTO.put(custoNivel + heuristica, temp);
			}

			custoNivel++;

		}

	}

	private boolean verificaSeGanhou() {
		// TODO Auto-generated method stub
		return false;
	}

}
