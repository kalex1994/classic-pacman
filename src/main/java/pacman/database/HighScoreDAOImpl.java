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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pacman.core.Maze;

/**
 * Implementation of the DAO desing pattern for accessing {@link HighScore}
 * objects from the database.
 */
public class HighScoreDAOImpl implements HighScoreDAO {
	/**
	 * For logging useful informations.
	 */
	private static Logger logger = LoggerFactory.getLogger(Maze.class);

	/**
	 * Object instance for the singletone desing pattern.
	 */
	private static HighScoreDAOImpl highScoreDAOImpl = null;

	/**
	 * Connection to the database.
	 */
	private static Connection connection = null;

	/**
	 * Returns the {@code HighScoreDAOImpl} instance.
	 * 
	 * @return the {@code HighScoreDAOImpl} instance
	 */
	public static HighScoreDAOImpl getInstance() {
		if (highScoreDAOImpl == null)
			highScoreDAOImpl = new HighScoreDAOImpl();
		return highScoreDAOImpl;
	}

	/**
	 * Connect to the database.
	 * 
	 * @return whether the connection attempt was successfull
	 */
	public boolean connect() {
		try {
			if (connection == null) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
						"H_T6PRK5", "kassai");
			} else if (connection.isClosed()) {
				connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@db.inf.unideb.hu:1521:ora11g",
						"H_T6PRK5", "kassai");
			}
		} catch (SQLException e) {
			logger.error("Cannot connect to the database!");
			return false;
		} catch (ClassNotFoundException e) {
			logger.error("JDBC driver not found!");
			return false;
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
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
			logger.error("An error occured during the query");
			e.printStackTrace();
		}
		return highScores;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addHighScore(HighScore highScore) {
		if (!isConnected())
			connect();

		String insertHighScore = "INSERT INTO HIGHSCORES VALUES (?,?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(insertHighScore);
			preparedStatement.setString(1, highScore.getName());
			preparedStatement.setDate(2, new java.sql.Date(highScore.getDate()
					.getTime()));
			preparedStatement.setInt(3, highScore.getScore());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("An error occured during the insert");
			return false;
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				logger.error("Cannot close the PreparedStatement!");
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean deleteAllHighScore() {
		if (!isConnected())
			connect();

		String deleteAll = "DELETE FROM HIGHSCORES";

		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(deleteAll);
			statement.close();
		} catch (SQLException e) {
			logger.error("An error occured during the deletion");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Returns whether a connection exists to the database.
	 * 
	 * @return whether a connection exists to the database
	 */
	public boolean isConnected() {

		if (connection == null)
			return false;
		try {
			if (!connection.isValid(1))
				return false;
		} catch (SQLException e) {
			logger.error("An error occurred while checking the database connection.");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Closes the connection to the database.
	 * 
	 * @return whether the closing operation was successfull
	 */
	public boolean closeConnection() {

		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			logger.error("An error occured closing the database connection");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
