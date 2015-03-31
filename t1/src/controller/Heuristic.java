package controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import model.Tabuleiro;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

// http://heuristicswiki.wikispaces.com/N+-+Puzzle
public class Heuristic {

	// private static SortedMap<Integer, Tabuleiro> ABERTO = new
	// TreeMap<Integer, Tabuleiro>();
	// private static SortedMap<Integer, Tabuleiro> FECHADO = new
	// TreeMap<Integer, Tabuleiro>();
	// private boolean ESTADO_FINAL = false;
	//
	// public void resolve(Tabuleiro tabuleiro) {
	//
	// Tabuleiro estadoLocal = null;
	// Tabuleiro temp = null;
	// int heuristica = 0;
	//
	// ABERTO.put(0, tabuleiro);
	//
	// int nivelAtual = 0;
	// while (ESTADO_FINAL == false) {
	//
	// // busca o proximo que entrou na lista
	// // deve buscar a menor heuristica
	// Map.Entry<Integer, Tabuleiro> entry = ABERTO.entrySet().iterator()
	// .next();
	// int key = entry.getKey();
	//
	// estadoLocal = ABERTO.get(key);
	//
	// ABERTO.remove(key);
	//
	// if (verificaSeGanhou(estadoLocal)) {
	// System.out.println("SUCESSO");
	// ESTADO_FINAL = true;
	// } else {
	// // gera filhos
	// FECHADO.put(nivelAtual + heuristica, estadoLocal);
	//
	// // inicio da expansao dos estados
	// temp = estadoLocal.moveVazioParaCima(estadoLocal);
	// if (temp != null) {
	//
	// // calcula heuristica, soma o nivel e popula
	// heuristica = temp.calcularHeuristicaTabuleiro();
	// ABERTO.put(nivelAtual + heuristica, temp);
	// }
	// temp = estadoLocal.moveVazioParaEsquerda(estadoLocal);
	// if (temp != null)
	// // verificar se esse estado ja existe na fronteira (Aberto)
	// // ou se ja foi percorrido(Fechado)se nao entao insere no
	// // aberto
	// // calcula heuristica, soma o nivel e popula
	// heuristica = temp.calcularHeuristicaTabuleiro();
	// ABERTO.put(nivelAtual + heuristica, temp);
	// temp = estadoLocal.moveVazioParaBaixo(estadoLocal);
	// if (temp != null)
	// // verificar se esse estado ja existe na fronteira (Aberto)
	// // ou se ja foi percorrido(Fechado)se nao entao insere no
	// // aberto
	// // calcula heuristica, soma o nivel e popula
	// heuristica = temp.calcularHeuristicaTabuleiro();
	// ABERTO.put(nivelAtual + heuristica, temp);
	// temp = estadoLocal.moveVazioParaDireita(estadoLocal);
	// if (temp != null)
	// // verificar se esse estado ja existe na fronteira (Aberto)
	// // ou se ja foi percorrido(Fechado)se nao entao insere no
	// // aberto
	// // calcula heuristica, soma o nivel e popula
	// heuristica = temp.calcularHeuristicaTabuleiro();
	// ABERTO.put(nivelAtual + heuristica, temp);
	// }
	//
	// nivelAtual++;
	//
	// }
	//
	// }

	SortedMap<Integer, Tabuleiro> fronteirasExpandidas = new TreeMap<Integer, Tabuleiro>();
	SortedMap<Integer, Tabuleiro> fronteirasVisitadas = new TreeMap<Integer, Tabuleiro>();

	int contadordie = 0;

	public boolean resolverRecursao(int nivel, Tabuleiro estado) {

		if (contadordie == 10) {
			System.exit(0);
		}

		int nivelLocal = nivel;
		Tabuleiro tabAtual = null;
		expandirFronteiras(nivel, estado);

		Map.Entry<Integer, Tabuleiro> entry = fronteirasExpandidas.entrySet()
				.iterator().next();
		int key = entry.getKey();

		System.out.println("Nivel atual: " + nivel + " custo: " + key);

		nivelLocal++;
		tabAtual = fronteirasExpandidas.get(key); // menor C + H

		Puzzle.showStatus("Primeiro: ", tabAtual.getTabuleiro());
		System.exit(0);

		fronteirasExpandidas.remove(key);
		fronteirasVisitadas.put(key, tabAtual);

		Puzzle.showStatus("Tab atual: ", tabAtual.getTabuleiro());

		if (Tabuleiro.compareTabuleiros(tabAtual.getTabuleiro(),
				Tabuleiro.getEstadoObjetivo())) {

		} else {
			contadordie++;
			resolverRecursao(nivelLocal, tabAtual);

		}

		return false;
	}

	private void expandirFronteiras(int nivel, Tabuleiro estado) {
		Tabuleiro temp = null;
		Tabuleiro fachada = estado;
		int heuristica = 0;

		temp = fachada.moveVazioParaCima();
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			fronteirasExpandidas.put(nivel + heuristica, temp);
		}
		fachada = estado;
		temp = fachada.moveVazioParaEsquerda();
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			fronteirasExpandidas.put(nivel + heuristica, temp);
		}
		fachada = estado;
		temp = fachada.moveVazioParaBaixo();
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			fronteirasExpandidas.put(nivel + heuristica, temp);
		}
		fachada = estado;
		temp = fachada.moveVazioParaDireita();
		if (temp != null) {
			heuristica = temp.calcularHeuristicaTabuleiro();
			fronteirasExpandidas.put(nivel + heuristica, temp);
		}
	}

	private boolean verificaSeGanhou(Tabuleiro tabuleiro) {
		// TODO Auto-generated method stub
		return false;
	}

}
