package suddjian.aaron.fractal_sound;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

import suddjian.aaron.fractal_sound.fractal.Fractal;
import suddjian.aaron.fractal_sound.fractal.Tree;

@SuppressWarnings("serial")
public class Main extends Canvas {

	private String title = "Visualize sounds with fractals!";
	private int width = 800;
	private int height = width;
	private boolean running = false;

	// graphical stuff
	private BufferedImage image;
	private Graphics2D graphics;
	private RenderingHints renderQuality;
	private RenderingHints renderSpeed;
	
	private static double renderInaccuracy = 0.01;
	
	private Fractal fractal;
	private double fractalAngle = 4.14;
	private double fractalLength = 0.1;
	private double fractalWidth = 1;

	public static final Random RAND = new Random((long) Math.toDegrees((double) (System.currentTimeMillis() << new Random(System.nanoTime()).nextLong()) + 340626000562045234.00004367445454234003234032));

	public Main() {
		setPreferredSize(new Dimension(width, height));
	}

	public void run() {
		createBufferStrategy(3);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		graphics = image.createGraphics();
		
		renderQuality = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderQuality.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		renderQuality.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderQuality.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		renderQuality.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		renderQuality.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		renderQuality.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		
		renderSpeed = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		renderSpeed.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		renderSpeed.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		renderSpeed.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		renderSpeed.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		renderSpeed.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		renderSpeed.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		
		graphics.addRenderingHints(renderQuality);
		graphics.setColor(new Color(255, 0, 0));
		
		fractal = new Tree();
		
		running = true;
		long frameCount = 0;
		long lastTime = System.currentTimeMillis();
		while (running) {
			render();
			frameCount++;

			{
				long time = System.currentTimeMillis();
				if (time - lastTime >= 1000) {
					System.out.println("fps: " + frameCount);
					frameCount = 0;
					lastTime = time;
				}
			}
		}
	}

	public void render() {
		graphics.clearRect(0, 0, width, height);
		fractal.draw(graphics, new Dimension(width, height), 12, fractalWidth, fractalAngle, fractalLength);
		fractalAngle += 0.008;
		fractalLength += 0.03;
		fractalWidth += 0.001;

		BufferStrategy strategy = getBufferStrategy();
		Graphics g = strategy.getDrawGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		strategy.show();
	}

	public void stop() {
		running = false;
	}

	public static void main(String[] args) {
		Main main = new Main();
		JFrame frame = new JFrame();
		frame.setResizable(false);
		frame.add(main);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		frame.setTitle(main.title);
		main.run();
	}
	
	/**
	 * @return an inaccuracy value that can be used by other objects to enable more efficient (but less accurate) rendering. A value closer to 0 will be more accurate, with accuracy decrasing away from 0
	 */
	public static double renderInaccuracy() {
		return renderInaccuracy;
	}
}
