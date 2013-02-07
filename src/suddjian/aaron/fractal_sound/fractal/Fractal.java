package suddjian.aaron.fractal_sound.fractal;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * An abstract class to define Fractals and their recursive nature
 * 
 * @author David Aaron Suddjian
 *
 */
public interface Fractal {
	
	/**
	 * @param g
	 * @param bounds The bounds to be considered when drawing. Fractals will not necessarily stay inside these bounds. This is for the Fractal's use only.
	 * @param iterations
	 * @param custom1
	 * @param custom2
	 * @param custom3
	 */
	public void draw(Graphics2D g, Dimension bounds, int iterations, double custom1, double custom2, double custom3);
}
