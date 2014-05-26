package pacman.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pacman.database.HighScoreDAOImpl;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener{
	
	private final String GAME_PANEL = "GAME_PANEL";
	private final String MENU_PANEL = "MENU_PANEL";
	private final String HIGHSCORE_PANEL = "HIGHSCORE_PANEL";
	
	JPanel cards;
	MenuPanel menuPanel = new MenuPanel(this);
	GamePanel gamePanel = new GamePanel(this);
	HighScorePanel highscorePanel = new HighScorePanel(this);
	
	public MainFrame()
	{	
		super("Pacman");
		
        cards = new JPanel(new CardLayout());
        cards.add(menuPanel, MENU_PANEL);
        cards.add(gamePanel, GAME_PANEL);
        cards.add(highscorePanel, HIGHSCORE_PANEL);
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
		CardLayout cardLayout = (CardLayout)(cards.getLayout());
		if (e.getActionCommand().equals("play"))
		{		
			gamePanel.startGame();
			cardLayout.show(cards, GAME_PANEL);
	        gamePanel.requestFocus();
		}
		else if (e.getActionCommand().equals("exit")) {
			HighScoreDAOImpl highScoreDAO = HighScoreDAOImpl.getInstance();
			if (highScoreDAO.isConnected())
				highScoreDAO.closeConnection();
			dispose();
		}
		else if (e.getActionCommand().equals("endgame"))
		{
			cardLayout.show(cards, MENU_PANEL);
		}
		else if (e.getActionCommand().equals("highscores"))
		{
			highscorePanel.refresh();
			cardLayout.show(cards, HIGHSCORE_PANEL);
		}
		else if (e.getActionCommand().equals("back"))
		{
			cardLayout.show(cards, MENU_PANEL);
		}		
	}
}
