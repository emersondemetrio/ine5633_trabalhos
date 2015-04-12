package controller;

/*
       This applet lets two uses play GoMoku (a.k.a Pente) against each 
       other.  Black always starts the game.  When a player gets five-in-a-row,
       that player wins.  The game ends in a draw if the board is filled
       before either player wins.

       This file defines two classes: the main applet class, GoMuku,
       and a canvas class, GoMokuCanvas.

       It is assumed that this applet is 330 pixels wide and 240 pixels high!

 */

import java.applet.Applet;
import java.awt.Color;

import model.GoMokuBoard;

public class GoMoku extends Applet {

	/*
	 * The main applet class only lays out the applet. The work of the game is
	 * all done in the GoMokuCanvas object. Note that the Buttons and Label used
	 * in the applet are defined as instance variables in the GoMokuCanvas
	 * class. The applet class gives them their visual appearance and sets their
	 * size and positions.
	 */

	@Override
	public void init() {

		setLayout(null); // I will do the layout myself.

		setBackground(new Color(0, 150, 0)); // Dark green background.

		/* Create the components and add them to the applet. */

		GoMokuBoard board = new GoMokuBoard(15, 15, 10, 10);
		// Note: The constructor creates the buttons board.resignButton
		// and board.newGameButton and the Label board.message.
		add(board);





		/*
		 * Set the position and size of each component by calling its
		 * setBounds() method.
		 */

		board.setBounds(16, 16, 300, 300); // Note: size MUST be 172-by-172 !

	}

} // end class GoMoku