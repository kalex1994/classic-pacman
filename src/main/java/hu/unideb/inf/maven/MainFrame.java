package hu.unideb.inf.maven;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
	
	private final String GAME_PANEL = "GAME_PANEL";
	private final String MENU_PANEL = "MENU_PANEL";
	
	JPanel cards;
	MenuPanel menuPanel = new MenuPanel(this);
	GamePanel gamePanel = new GamePanel(this);
	
	public MainFrame()
	{	
		super("Pacman");
		
        cards = new JPanel(new CardLayout());
        cards.add(menuPanel, MENU_PANEL);
        cards.add(gamePanel, GAME_PANEL);    
        getContentPane().add(cards, BorderLayout.CENTER);
			
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);	
	}
	
	public static void main(String args[])
	{
		new MainFrame();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("play"))
		{
			CardLayout cardLayout = (CardLayout)(cards.getLayout());
			gamePanel.startGame();
			cardLayout.show(cards, GAME_PANEL);
	        gamePanel.requestFocus();
		}
		else if (e.getActionCommand().equals("exit")) {
			dispose();
		}
		else if (e.getActionCommand().equals("endgame"))
		{
			CardLayout cardLayout = (CardLayout)(cards.getLayout());
			cardLayout.show(cards, MENU_PANEL);
		}
			
	}

}
