package hu.unideb.inf.maven;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * Class for animating Pacman.
 */
public class PacmanAnimation {
	/**
	 * Images for the animation. All image faces to the right.
	 */
	private Image images[];
	
	/**
	 * Images for the animation. All image faces downwards.
	 */
	private Image down[];
	
	/** 
	 * Images for the animation. All image faces to the left.
	 */
	private Image left[];
	
	/**
	 * Images for the animation. All image faces upwards.
	 */
	private Image up[];
	
	/**
	 * The images to be drawn.
	 */
	private Image[] current = images;
	
	/**
	 * The order of the images to be drawn.
	 */
	private int order[];
	
	/**
	 * Index of the actual image.
	 */
	private int index;
	
	/**
	 * Constructor for creating a {@code PacmanAnimation} object.
	 * Creates rotations of the images upwards, downwards and to the left.
	 * 
	 * @param images images for the animation, all image faces to the right
	 * @param order order of the images to be drawn
	 */
	public PacmanAnimation(Image[] images, int order[])
	{
		this.images = images;
		this.order = order;
		index = 0;	
		
		down = new Image[images.length];
		createRotation(images, down, Direction.DOWN);
		left = new Image[images.length];
		createRotation(images, left, Direction.LEFT);
		up = new Image[images.length];
		createRotation(images, up, Direction.UP);
	}
	
	/**
	 * Creates rotations of the images in the source array and places them in the array specified.
	 * The rotated images will face to the direction specified.
	 * 
	 * @param source the source array of images, all image faces to the right
	 * @param destination array where the rotated images will be placed
	 * @param direction after the rotation, all image will face to the direction specified
	 */
	void createRotation(Image source[], Image destination[], Direction direction)
	{
		for(int i = 0; i < source.length; ++i)
		{
			int width = source[i].getWidth(null);
			int height = source[i].getHeight(null);
			
			BufferedImage copyOfImage = new BufferedImage(width+1, height+1, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gr = copyOfImage.createGraphics();
			
			int angle = 0;
			if (direction == Direction.LEFT) angle = 180;
			if (direction == Direction.UP) angle = 270;
			if (direction == Direction.DOWN) angle = 90;
			if (direction == Direction.RIGHT) angle = 0;
			
			gr.rotate(Math.toRadians(angle), width / 2 + 1, height / 2 + 1);
			gr.drawImage(source[i], 0, 0, null);
			
			destination[i] = (Image)copyOfImage;
		}
	}
			
	/**
	 * Loops the animation.
	 */
	public void goToNextImage()
	{
		index = (index + 1) % order.length;
	}
	
	/**
	 * Draws Pacman according to its current direction
	 * 
	 * @param g {@link java.awt.Graphics Graphics} object used for drawing
	 * @param pacman represents Pacman in the game
	 */
	void draw(Graphics2D g, Pacman pacman)
	{	
		if (pacman.getCurrentDirection() == Direction.RIGHT)
			current = images;
		if (pacman.getCurrentDirection() == Direction.DOWN)
			current = down;
		else if (pacman.getCurrentDirection() == Direction.LEFT)
			current = left;
		else if (pacman.getCurrentDirection() == Direction.UP)
			current = up;
		
		if (current == null)
			current = images;
		
		g.drawImage(current[order[index]], pacman.position.x - 3, pacman.position.y - 3, null);
	}
	
	/**
	 * Resets the animation.
	 */
	void reset() {
		index = 0;
	}
}
