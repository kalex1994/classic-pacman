package hu.unideb.inf.maven;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Class for loading and storing images.
 */
public class ImageContainer {
	/**
	 * Images for the ghost named "blinky"
	 */
	Image blinky[] = new Image[4];
	
	/**
	 * Images for the ghost named "pinky"
	 */
	Image pinky[] = new Image[4];
	
	/**
	 * Images for the ghost named "inky"
	 */
	Image inky[] = new Image[4];
	
	/**
	 * Images for the ghost named "clyde"
	 */
	Image clyde[] = new Image[4];
	
	/**
	 * Images for the ghost named "orson"
	 */
	Image orson[] = new Image[4];
	
	/**
	 * Images for the ghost named "spooky"
	 */
	Image spooky[] = new Image[4];
	
	/**
	 * Images for Pacman
	 */
	Image pacman[] = new Image[4];
	
	/**
	 * The background of the game.
	 */
	Image background;
	
	/**
	 * Used for drawing how many lives Pacman has.
	 */
	Image pacmanLife;
	
	/**
	 * Constructor for creating an {@code ImageContainer} object.
	 * Loads all images needed.
	 */
	public ImageContainer()
	{
		loadGhostImages("blinky", blinky);
		loadGhostImages("pinky", pinky);
		loadGhostImages("inky", inky);
		loadGhostImages("clyde", clyde);
		loadGhostImages("orson", orson);
		loadGhostImages("spooky", spooky);
		loadPacmanImages();
		
		background = new ImageIcon(getClass().getResource("/background.png").getPath()).getImage();
		pacmanLife = pacman[3];
	}
	
	/**
	 * Loads images for a ghost specified.
	 * 
	 * @param name the name of a ghost
	 * @param arr an array of images
	 */
	private void loadGhostImages(String name, Image[] arr)
	{
		for(Direction direction : Direction.values())
			if (direction != Direction.NONE)
				arr[direction.ordinal()] = new ImageIcon(getClass().getResource
						(String.format("/%s_%s.png", name, 
								direction.toString().toLowerCase())).getPath()).getImage();		
	}
	
	/**
	 * Loads images for Pacman.
	 */
	private void loadPacmanImages()
	{
		for(int i = 0; i < 4; ++i)
			pacman[i] = new ImageIcon(getClass().getResource(String.format("/pacman%s.png",
					String.valueOf(i)))).getImage();
	}
}
