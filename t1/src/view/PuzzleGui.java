package view;

import javax.swing.*;
import model.Tabuleiro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PuzzleGui {
	protected boolean inTest = true;
	protected JTextField[] entradas;
	protected JFrame janelaPrincipal;
	protected Tabuleiro tabuleiro;

	public PuzzleGui(String nome, Tabuleiro tab) {
		this.tabuleiro = tab;
		JPanel panel = new JPanel(new SpringLayout());
		panel.add(new JLabel("Insira um estado inical."));
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));

		this.entradas = new JTextField[9];
		int[] aleatorio = new int[] { 8, 7, 6, 5, 4, 3, 2, 1, 0 };
		for (int i = 0; i < entradas.length; i++) {

			entradas[i] = new JTextField();
			entradas[i].setSize(30, 20);
			entradas[i].setText(Integer.toString(aleatorio[i]));
			panel.add(entradas[i]);
		}

		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		JButton botaoIniciar = new JButton("Iniciar");
		panel.add(botaoIniciar);

		SpringUtilities.makeGrid(panel, 5, 3, 5, 5, 5, 5);

		janelaPrincipal = new JFrame(nome);
		janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel.setOpaque(true);
		janelaPrincipal.setContentPane(panel);

		janelaPrincipal.setLocation(400, 200);
		janelaPrincipal.setResizable(false);
		janelaPrincipal.pack();
		janelaPrincipal.setVisible(true);

		msg("Observação: Para iniciar o programa, você deve inserir valores de 0 a 8 sem repetição.");

		botaoIniciar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int[][] inicial = new int[3][3];
				int[] arrayTemporario = new int[9];
				int index = 0;
				int estadoValido = 0;

				for (JTextField atual : entradas) {

					try {
						int valorAtual = Integer.parseInt(atual.getText());
						if (valorAtual > 8 || valorAtual < 0) {
							atual.setText("0");
						} else {
							arrayTemporario[index] = valorAtual;
							index++;
						}
					} catch (Exception numberFormatError) {
						atual.setText("0");
					}
				}

				for (int i : arrayTemporario) {
					if (i == 0) {
						estadoValido++;
					}
				}

				if (estadoValido > 1) {
					msg("Impossível inicar estado! Existem valores fora do intervalo estabelecido. [ 0 - 8 ] ou não são números!");
				} else {

					for (int i = 0; i < 3; i++) {
						System.arraycopy(arrayTemporario, (i * 3), inicial[i],
								0, 3);
					}
					
					tabuleiro.setEstadoInicial(inicial);
					showStatus(inicial);
					
					if( !inTest){
						janelaPrincipal.setVisible(false);
					}
				}
			}
		});
	}

	public void showStatus(int[][] estado) {
		String output = "";
		for (int i = 0; i < estado.length; i++) {
			for (int j = 0; j < estado.length; j++) {
				output += " [" + estado[i][j] + "] ";
			}
			output += "\n";
		}
		System.out.println("\n  ---TABLE---\n" + output);
	}

	public void msg(String s) {
		if (inTest) {
			System.out.println(s + "\n");
		} else {
			JOptionPane.showMessageDialog(janelaPrincipal, s);
		}
	}
}