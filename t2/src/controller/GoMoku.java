package controller;



import java.applet.Applet;
import java.awt.Color;

import model.TabuleiroGoMoku;
import model.Utilidade;

public class GoMoku extends Applet {


	@Override
	public void init() {

		setLayout(null);
		setBackground(new Color(255, 255, 153)); // verde escuro

		/* Cria os componentes e adiciona na applets. */

		TabuleiroGoMoku board = new TabuleiroGoMoku(5, 5, 12, 12);
		add(board);


		board.setBounds(5, 5, 900, 900); 
new Utilidade();
	}

} 