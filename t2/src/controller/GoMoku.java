package controller;



import java.applet.Applet;
import java.awt.Color;

import model.TabuleiroGoMoku;

public class GoMoku extends Applet {


	@Override
	public void init() {

		setLayout(null);
		setBackground(new Color(0, 150, 0)); // verde escuro

		/* Cria os componentes e adiciona na applets. */

		TabuleiroGoMoku board = new TabuleiroGoMoku(15, 15, 12, 12);
		add(board);


		board.setBounds(5, 5, 900, 900); 

	}

} 