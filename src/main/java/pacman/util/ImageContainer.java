package pacman.util;

import java.awt.Image;

import javax.swing.ImageIcon;

import pacman.core.Direction;

/**
 * Class for loading and storing images.
 */
public class ImageContainer {
	/**
	 * Images for the ghost named "blinky".
	 */
	public Image blinky[] = new Image[4];

	/**
	 * Images for the ghost named "pinky".
	 */
	public Image pinky[] = new Image[4];

	/**
	 * Images for the ghost named "inky".
	 */
	public Image inky[] = new Image[4];

	/**
	 * Images for the ghost named "clyde".
	 */
	public Image clyde[] = new Image[4];

	/**
	 * Images for the ghost named "orson".
	 */
	public Image orson[] = new Image[4];

	/**
	 * Images for the ghost named "spooky".
	 */
	public Image spooky[] = new Image[4];

	/**
	 * Images for Pacman.
	 */
	public Image pacman[] = new Image[4];

	/**
	 * The background of the game.
	 */
	public Image background;

	/**
	 * Used for drawing how many lives Pacman has.
	 */
	public Image pacmanLife;

	/**
	 * Constructor for creating an {@code ImageContainer} object. Loads all
	 * images needed.
	 */
	public ImageContainer() {
		loadGhostImages("blinky", blinky);
		loadGhostImages("pinky", pinky);
		loadGhostImages("inky", inky);
		loadGhostImages("clyde", clyde);
		loadGhostImages("orson", orson);
		loadGhostImages("spooky", spooky);
		loadPacmanImages();
		background = new ImageIcon(getClass().getResource("/background.png"))
				.getImage();
		pacmanLife = pacman[3];
	}

	/**
	 * Loads images for a ghost specified.
	 * 
	 * @param name
	 *            the name of a ghost
	 * @param arr
	 *            an array of images
	 */
	private void loadGhostImages(String name, Image[] arr) {
		for (Direction direction : Direction.values())
			if (direction != Direction.NONE) {
				arr[direction.ordinal()] = new ImageIcon(getClass()
						.getResource(
								String.format("/%s_%s.png", name, direction
										.toString().toLowerCase()))).getImage();
			}
	}

	/**
	 * Loads images for Pacman.
	 */
	private void loadPacmanImages() {
		for (int i = 0; i < 4; ++i)
			pacman[i] = new ImageIcon(getClass().getResource(
					String.format("/pacman%s.png", String.valueOf(i))))
					.getImage();
	}
}
