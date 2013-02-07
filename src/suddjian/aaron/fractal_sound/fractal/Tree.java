package suddjian.aaron.fractal_sound.fractal;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;

import suddjian.aaron.fractal_sound.Main;
import suddjian.aaron.fractal_sound.Vector2D;

public class Tree implements Fractal {
	
	@Override
	public void draw(Graphics2D g, Dimension bounds, int iterations, double custom1, double custom2, double custom3) {
		double width = custom1;
		double angle = custom2;
		double length = custom3;
		if (width < 0.1) width = 0.1; // prevent this from getting too small
		if (width > length * 2 / 3) width = length * 2 / 3;
		g.setStroke(new BasicStroke((float) width, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		iterate(g, iterations, angle, bounds.getWidth() / 2, bounds.getHeight()  * 2 / 3, Vector2D.fromAngle(Vector2D.UP, length), Main.renderInaccuracy());
	}

	// angles are in radians
	protected void iterate(Graphics2D g, int iterations, double angle, double x, double y, Vector2D parent, double inaccuracy) {
		double mag = parent.getMagnitude() * 2 / 3;
		if (mag < inaccuracy) return;
		Vector2D v1 = Vector2D.fromAngle(parent.getAngle() + angle, mag);
		Vector2D v2 = Vector2D.fromAngle(parent.getAngle() - angle, mag);
		
		g.drawLine((int) x,  (int) y, (int) (x + v1.xLength()), (int) (y + v1.yLength()));
		g.drawLine((int) x, (int) y, (int) (x + v2.xLength()), (int) (y + v2.yLength()));
		
		if (iterations > 0) {
			BasicStroke stroke;
			{ // make the stroke 2/3 what it is now
				BasicStroke currentStroke = (BasicStroke) g.getStroke();
				stroke = new BasicStroke(currentStroke.getLineWidth() * 3 / 4, currentStroke.getEndCap(), currentStroke.getLineJoin());
			}
			// draw the next segment
			g.setStroke(stroke);
			iterate(g, iterations - 1, angle, x + v1.xLength(), y + v1.yLength(), v1, inaccuracy);
			// the stroke has to be set again because the last iteration call messed with it
			g.setStroke(stroke);
			iterate(g, iterations - 1, angle, x + v2.xLength(), y + v2.yLength(), v2, inaccuracy);
		}
	}
}
