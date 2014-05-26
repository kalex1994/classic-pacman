package pacman.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pacman.database.HighScoreDAOImpl;

/**
 * Class representing the main window of the game. The window uses a
 * {@link CardLayout} layout for switching efficiently between panels.
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {
	/**
	 * Used for switching to the {@link GamePanel} on the {@link CardLayout}.
	 */
	private final String GAME_PANEL = "GAME_PANEL";

	/**
	 * Used for switching to the {@link MenuPanel} on the {@link CardLayout}.
	 */
	private final String MENU_PANEL = "MENU_PANEL";

	/**
	 * Used for switching to the {@link HighScorePanel} on the
	 * {@link CardLayout}.
	 */
	private final String HIGHSCORE_PANEL = "HIGHSCORE_PANEL";

	/**
	 * For arranging panels in a {@link CardLayout}.
	 */
	JPanel cards;

	/**
	 * The menu of the game.
	 */
	MenuPanel menuPanel = new MenuPanel(this);

	/**
	 * Custom panel, where the game takes place on.
	 */
	GamePanel gamePanel = new GamePanel(this);

	/**
	 * Custom panel where the high scores displayed.
	 */
	HighScorePanel highscorePanel = new HighScorePanel(this);

	/**
	 * Constructor for creating a {@code MainFrame} object.
	 */
	public MainFrame() {
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

	/**
	 * Used for processing user interaction. The panels of the
	 * {@link CardLayout} are switched here.
	 * 
	 * @param e
	 *            an event that has been fired
	 */
	public void actionPerformed(ActionEvent e) {
		CardLayout cardLayout = (CardLayout) (cards.getLayout());
		if (e.getActionCommand().equals("play")) {
			gamePanel.startGame();
			cardLayout.show(cards, GAME_PANEL);
			gamePanel.requestFocus();
		} else if (e.getActionCommand().equals("exit")) {
			HighScoreDAOImpl highScoreDAO = HighScoreDAOImpl.getInstance();
			if (highScoreDAO.isConnected())
				highScoreDAO.closeConnection();
			dispose();
		} else if (e.getActionCommand().equals("endgame")) {
			cardLayout.show(cards, MENU_PANEL);
		} else if (e.getActionCommand().equals("highscores")) {
			highscorePanel.refresh();
			cardLayout.show(cards, HIGHSCORE_PANEL);
		} else if (e.getActionCommand().equals("back")) {
			cardLayout.show(cards, MENU_PANEL);
		}
	}

	/**
	 * Entry point of the application.
	 * 
	 * @param args
	 *            array of command line arguments
	 */
	public static void main(String args[]) {
		new MainFrame();
	}
}
