package pacman.gui;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pacman.core.Cell;
import pacman.core.Direction;
import pacman.core.Ghost;
import pacman.core.Maze;
import pacman.core.Pacman;
import pacman.database.HighScore;
import pacman.database.HighScoreDAO;
import pacman.database.HighScoreDAOImpl;
import pacman.util.ImageContainer;
import pacman.util.PacmanAnimation;

public class GamePanel extends JPanel implements ActionListener{
private static Logger	logger = LoggerFactory.getLogger(GamePanel.class);
	
	/**
	 * Constant serialized ID used for compatibility.
	 */
	private static final long serialVersionUID = -776734057833294253L;
	
	/**
	 * Timer for udpating the game's state. In every timer tick the game's state gets updated.
	 */
	private Timer timer;
	
	/**
	 * Timer for delaying the start of a level.
	 */
	private Timer levelStartTimer;

	/**
	 * Loads and contains images.
	 */
	private ImageContainer images;
	
	/**
	 * Represents Pacman in the game.
	 */
	private Pacman pacman;
	
	/**
	 * The 6 ghost in the game. They are the enemies of Pacman.
	 */
	private Ghost ghosts[] = new Ghost[6];
	
	/**
	 * Stores the player's score.
	 */
	private HighScore highScore;
	
	/**
	 * Animation for Pacman.
	 */
	private PacmanAnimation pacmanAnimation;
	
	/**
	 * Font used for drawing the players score.
	 */
	private final Font highscoreFont = new Font("Helvetica", Font.BOLD, 16);
	
	private final Font levelFont = new Font("Helvetica", Font.BOLD, 20);
	
	private Maze maze = new Maze("maze.xml");
	
	private int actualLevel;

	/**
	 * Invoked when an action is performed.
	 */
	public void actionPerformed(ActionEvent e) {
		updateGame();
	}
	
	/**
	 * Initializes the ghosts with their name and current cell in the maze.
	 */
	private void initGhosts()
	{
		ghosts[0] = new Ghost(maze, "pinky", Cell.positionOfCell(4, 1));
		ghosts[1] = new Ghost(maze, "blinky", Cell.positionOfCell(4, 26));
		ghosts[2] = new Ghost(maze, "clyde", Cell.positionOfCell(32, 1));
		ghosts[3] = new Ghost(maze, "inky", Cell.positionOfCell(32, 26));
		ghosts[4] = new Ghost(maze, "orson", Cell.positionOfCell(11, 1));
		ghosts[5] = new Ghost(maze, "spooky", Cell.positionOfCell(11, 26));
	}
	
	/**
	 * Initializes a new level. Called at the start of the game, and after every completed level.
	 */
	private void initNewLevel()
	{
		maze = new Maze("maze.xml");
		Point position = Cell.positionOfCell(26, 13);
		position.x += 7;
		if (highScore.getScore() == 0)
			pacman = new Pacman(maze, position, maze.cellAt(26, 13), Direction.RIGHT, 3);
		else
			pacman = new Pacman(maze, position, maze.cellAt(26, 13), 
					Direction.RIGHT, pacman.getNumberOfLives());
		initGhosts();
		
		timer.stop();
		levelStartTimer.restart();
	}
	
	/**
	 * Initializes the level after pacman died. The ghosts and Pacman are returning to their initial cells.
	 * The player's score reamains unchanged.
	 */
	private void initLevelAfterDeath()
	{
		Point position = Cell.positionOfCell(26, 13);
		position.x += 7;
		pacman = new Pacman(maze, position, maze.cellAt(26, 13),
				Direction.RIGHT, pacman.getNumberOfLives() - 1);
		pacmanAnimation.reset();
		initGhosts();
		repaint();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		timer.stop();
		levelStartTimer.restart();
	}
	
	/**
	 * If Pacman reaches a cell that contains food, the player's score is updated.
	 */
	private void updateHighScore()
	{
		if (pacman.getCurrentCell().getContainsFood() == true) {
			pacman.getCurrentCell().setContainsFood(false);
			highScore.update(10);
		}
		if (highScore.getScore() / 2440 == actualLevel)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			initNewLevel();
			++actualLevel;
		}
	}
	
	/**
	 * Updates pacman's animation.
	 */
	private void updatePacmansAnimation()
	{
		Direction direction = pacman.getCurrentDirection();
		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			if (pacman.position.x % 3 == 0)
				pacmanAnimation.goToNextImage();
		} else if (direction == Direction.UP || direction == Direction.DOWN) {
			if (pacman.position.y % 3 == 0)
				pacmanAnimation.goToNextImage();
		}
	}
	
	/**
	 * Updates the state of the game. Pacman, the ghosts, the player's score, and Pacman's animation
	 * are all updated.
	 */
	private void updateGame()
	{
		if (levelStartTimer.isRunning() && !timer.isRunning())
			timer.start();		
		if (timer.isRunning())
		{
			pacman.update();
			for (Ghost ghost : ghosts)
				ghost.update();	
			updateHighScore();					
			updatePacmansAnimation();
			
			for(Ghost ghost : ghosts)
				if (ghost.isCollidedWithPacman(pacman))
				{
					if (pacman.getNumberOfLives() > 0)
					{
						initLevelAfterDeath();
						logger.info("Pacman has died");
					}
					else
						gameEnded();
					return;
				}
		}	
		repaint();
	}
	
	public void startGame()
	{
		images = new ImageContainer();		
		timer = new Timer(15, this);
		timer.setRepeats(true);		
		levelStartTimer = new Timer(2000, this);
		highScore = new HighScore();
		pacmanAnimation = new PacmanAnimation(images.pacman, new int[] { 3, 2, 0, 1, 2 });
		actualLevel = 1;
		initNewLevel();
	}
	
	ActionListener listener;
	
	/**
	 * Constructor for creating a {@code GameFrame} object.
	 * All object initialization happens here.
	 */
	public GamePanel(ActionListener listener) {
		super();
		addKeyListener(new MyKeyAdapter());
		setPreferredSize(new Dimension(420, 540));
		setFocusable(true);	
		this.listener = listener;
	}
	
	private void gameEnded()
	{
		timer.stop();
		levelStartTimer.stop();

		final String name = JOptionPane.showInputDialog(null, "Please enter you name: ", 
				"The game has ended.", 1);
		if (name != null)
		{
			Thread thread = new Thread(){
			    public void run(){
			    	HighScoreDAO highScoreDAO = HighScoreDAOImpl.getInstance();
			    	
			    	List<HighScore> highScores = highScoreDAO.getAllHighScore();
			    	
					highScore.setName(name);
					highScore.setDate(new Date());
					highScores.add(highScore);
					Collections.sort(highScores);
					
					highScoreDAO.deleteAllHighScore();
					
					if (highScores.size() > 10)
						for(int i = 0; i < 10; ++i)
							highScoreDAO.addHighScore(highScores.get(i));
					else
						for(HighScore hs : highScores)
							highScoreDAO.addHighScore(hs);			
			    }
			  };
			  thread.start();
		}

		listener.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, "endgame"));
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.drawImage(images.background, 0, 0, null);
		drawFood(g);
		drawHighScore(g);
		drawLevelInformation(g);
		drawPacmansLives(g);
		for (Ghost ghost : ghosts)
			drawGhost(g, ghost);
		pacmanAnimation.draw(graphics, pacman);
	}

	/**
	 * Draws how many life Pacman has.
	 * 
	 * @param g {@link java.awt.Graphics Graphics} object used for drawing
	 */
	private void drawPacmansLives(Graphics g) {
		Point position = Cell.positionOfCell(maze.HEIGHT - 1, maze.WIDTH - 6);
		for (int i = 0; i < pacman.getNumberOfLives(); ++i) {
			g.drawImage(images.pacmanLife, position.x - 3, position.y - 9, null);
			position.x += 2 * Cell.WIDTH;
		}
	}

	/**
	 * Draws a ghost. A ghost has 4 different images according to what direction it is moving.
	 * 
	 * @param g {@link java.awt.Graphics Graphics} object used for drawing
	 * @param ghost the ghost to draw
	 */
	private void drawGhost(Graphics g, Ghost ghost) {

		if (ghost.getName().equals("pinky"))
			g.drawImage(
					images.pinky[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
		else if (ghost.getName().equals("blinky"))
			g.drawImage(
					images.blinky[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
		else if (ghost.getName().equals("inky"))
			g.drawImage(images.inky[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
		else if (ghost.getName().equals("clyde"))
			g.drawImage(
					images.clyde[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
		else if (ghost.getName().equals("orson"))
			g.drawImage(
					images.orson[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
		else
			g.drawImage(
					images.spooky[ghost.getDirection().ordinal()],
					ghost.position.x - 3, ghost.position.y - 3, null);
	}

	/**
	 * For every cell in the maze, draws a little rectangle at the center of that cell, if the cell
	 * contains food.
	 * 
	 * @param g {@link java.awt.Graphics Graphics } object used for drawing
	 */
	private void drawFood(Graphics g) {
		for (int i = 0; i < maze.HEIGHT; ++i)
			for (int j = 0; j < maze.WIDTH; ++j)
				if (maze.cellAt(i, j).getContainsFood() == true) {
					g.setColor(Color.WHITE);
					Point position = Cell.positionOfCell(maze.cellAt(i, j));
					int centerX = position.x + Cell.WIDTH / 2 + 1;
					int centerY = position.y + Cell.HEIGHT / 2 + 1;
					g.fillRect(centerX - 1, centerY - 1, 2, 2);
				}
	}

	/**
	 * Draws the player's score.
	 * 
	 * @param g {@link java.awt.Graphics Graphics} object used for drawing
	 */
	private void drawHighScore(Graphics g) {
		g.setFont(highscoreFont);
		g.setColor(Color.RED);
		String s = "Score: " + highScore.getScore();
		Point position = Cell.positionOfCell(maze.HEIGHT - 1, 0);
		g.drawString(s, position.x + 10, position.y + 5);
	}
	
	private void drawLevelInformation(Graphics g)
	{
		g.setFont(highscoreFont);
		g.setColor(Color.RED);
		String s = "Level " + actualLevel;
		Point position = Cell.positionOfCell(2, 12);
		g.drawString(s, position.x, position.y - 5);
	}
	
	class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				pacman.setNextDirection(Direction.LEFT);
				logger.info("Key pressed: LEFT");
			} else if (key == KeyEvent.VK_RIGHT) {
				pacman.setNextDirection(Direction.RIGHT);
				logger.info("Key pressed: RIGHT");
			} else if (key == KeyEvent.VK_UP) {
				pacman.setNextDirection(Direction.UP);
				logger.info("Key pressed: UP");
			} else if (key == KeyEvent.VK_DOWN) {
				pacman.setNextDirection(Direction.DOWN);
				logger.info("Key pressed: DOWN");
			}
		}
	}

}
