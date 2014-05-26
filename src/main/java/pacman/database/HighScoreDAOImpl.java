package pacman.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HighScoreDAOImpl implements HighScoreDAO{

	private static HighScoreDAOImpl highScoreDAOImpl = null;
	
	private static Connection connection = null;
	
	public static HighScoreDAOImpl getInstance()
	{
		if (highScoreDAOImpl == null)
			highScoreDAOImpl = new HighScoreDAOImpl();
		return highScoreDAOImpl;
	}
	
	public boolean connect() {
		try {
			if (connection == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection("jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
						"H_T6PRK5", "kassai");
			} else if (connection.isClosed()) {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
						"H_T6PRK5", "kassai");
			}
		} catch (SQLException e) {
			return false;
		} catch (ClassNotFoundException e){
			return false;
		}
		return true;
	}
	
	public List<HighScore> getAllHighScore() {
		if (!isConnected())
			connect();
		
		List<HighScore> highScores = new ArrayList<HighScore>();
		final String selectAll = "SELECT * FROM HIGHSCORES";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(selectAll);
			while (rs.next()) {
				String name = rs.getString("NAME");
				Date date = new Date(rs.getDate("SCORE_DATE").getTime());
				int score = rs.getInt("SCORE");
				
				HighScore highScore = new HighScore();
				highScore.setName(name);
				highScore.setDate(date);
				highScore.update(score);
				highScores.add(highScore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return highScores;
	}

	public boolean addHighScore(HighScore highScore) {
		if (!isConnected())
			connect();
		
		String insertHighScore = "INSERT INTO HIGHSCORES VALUES (?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(insertHighScore);
			preparedStatement.setString(1, highScore.getName());
			preparedStatement.setDate(2, new java.sql.Date(highScore.getDate().getTime()));
			preparedStatement.setInt(3, highScore.getScore());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean deleteAllHighScore() {
		if (!isConnected())
			connect();

		String deleteAll = "DELETE FROM HIGHSCORES";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(deleteAll);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean isConnected() {

		if (connection == null)
			return false;
		try {
			if (!connection.isValid(1))
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean closeConnection() {

		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
