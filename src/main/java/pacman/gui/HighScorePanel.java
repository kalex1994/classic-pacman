package pacman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import pacman.database.HighScore;
import pacman.database.HighScoreDAO;
import pacman.database.HighScoreDAOImpl;

public class HighScorePanel extends JPanel{
	private Image background;
	private List<HighScore> highScores;
	private final Font highscoreFont = new Font("Helvetica", Font.BOLD, 13);
	
	public HighScorePanel(ActionListener actionListener)
	{
		super();
		setPreferredSize(new Dimension(420, 540));
		
		JButton btnBack = new JButton("Back");
		btnBack.setActionCommand("back");
		btnBack.setFocusPainted(false);
		btnBack.addActionListener(actionListener);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(150)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(149, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(477, Short.MAX_VALUE)
					.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		
		background = new ImageIcon(getClass().getResource("/highscore_background.jpg")).getImage();
	}
	
	public void refresh()
	{
		HighScoreDAO highScoreDAO = HighScoreDAOImpl.getInstance();
		highScores = highScoreDAO.getAllHighScore();
		Collections.sort(highScores);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		g.setFont(highscoreFont);
		g.setColor(Color.RED);
		int i = 0;
		for(HighScore highScore : highScores)
		{					
			String date = new SimpleDateFormat("yyyy-MM-dd").format(highScore.getDate());
			String name = highScore.getName();
			
			String column1 = String.format("%2d. Name: %s", i + 1,
					name.length() > 8 ? name.substring(0, 9) : name);
			String column2 = String.format("Date: %s", date);
			String column3 = String.format("Score: %d", highScore.getScore());
			
			g.drawString(column1, 10, 200 + i * 20);
			g.drawString(column2, 160, 200 + i * 20);
			g.drawString(column3, 310, 200 + i * 20);
			++i;
		}
	}
}
