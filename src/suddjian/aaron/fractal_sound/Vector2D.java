package suddjian.aaron.fractal_sound;

public class Vector2D {

	/**
	 * Useful constant to set vector angles
	 */
	public static final double LEFT = Math.PI, DOWN = Math.PI / 2.0, UP = -DOWN, RIGHT = 0.0;

	/**
	 * Useful constants to rotate vector angles. A positive rotation will occur in the clockwise direction. These values can be divided and multiplied for the desired effect.
	 */
	public static final double QUARTER_TURN = Math.PI / 2, EIGHTH_TURN = QUARTER_TURN / 2.0, THIRD_TURN = (2.0 * Math.PI) / 3.0, SIXTH_TURN = THIRD_TURN / 2.0;

	private double xLength;
	private double yLength;

	/**
	 * Creates a Vector2D that has the given length along the X and Y cartesian coordinates.
	 * 
	 * @param xLength
	 * @param yLength
	 */
	public Vector2D(double xLength, double yLength) {
		this.xLength = xLength;
		this.yLength = yLength;
	}

	/**
	 * Creates a new Vector2D using the given angle and length. If getLength() or getAngle() are called later, the returned numbers will be close, but not exactly the same as provided because of
	 * floating-point arithmetic. This method is not as fast as using a constructor.
	 * 
	 * The angle variable is assumed to be in radians, not degrees.
	 * 
	 * @param angle the angle, in radians
	 * @param magnitude the length of the vector
	 * @return a new Vector2D with the given properties
	 */
	public static Vector2D fromAngle(double angle, double magnitude) {
		return new Vector2D(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Creates a vector running from point A to point B.
	 * 
	 * While Vector2D does not store coordinate variables, the returned vector will have the correct xLength and yLength to get from A to B. This method is not as fast as using a constructor.
	 * 
	 * @param xA the X value of point A
	 * @param yA the Y value of point A
	 * @param xB the X value of point B
	 * @param yB the Y value of point B
	 * @return A new Vector running from point A to point B
	 */
	public static Vector2D fromAtoB(double xA, double yA, double xB, double yB) {
		return new Vector2D(xB - xA, yB - yA);
	}

	/**
	 * Returns a new Vector2D with the same values as this Vector2D
	 */
	public Vector2D clone() {
		return new Vector2D(xLength, yLength);
	}

	/**
	 * Adds another Vector2D to this one
	 * 
	 * @param other the Vector2D to add
	 */
	public void add(Vector2D other) {
		xLength += other.xLength;
		yLength += other.yLength;
	}

	/**
	 * Subtracts another Vector2D from this one
	 * 
	 * @param other the Vector2D to subtract
	 */
	public void subtract(Vector2D other) {
		xLength -= other.xLength;
		yLength -= other.yLength;
	}

	/**
	 * Multiplies this Vector2D by the given scalar
	 * 
	 * @param scalar the amount to scale (if 1 no change, if 2 magnitude is doubled, if 0 magnitude is 0)
	 */
	public void scale(double scalar) {
		xLength *= scalar;
		yLength *= scalar;
	}

	/**
	 * Sets the length, or magnitude, of this Vector2D to the given amount.
	 * 
	 * @param m the new magnitude
	 */
	public void setMagnitude(double m) {
		scale(m / getMagnitude());
	}

	/**
	 * Rotates this Vector2D around its origin by the given amount.
	 * 
	 * The angle should be given in radians.The method will still work if it isn't, but you won't get the intended result. It is important to note that if your coordinate system has y increasing from
	 * top to bottom, then the effect will be a clockwise rotation. Trigonometrically, however, the rotation is counter-clockwise.
	 * 
	 * @param amount the amount to rotate
	 */
	public void rotate(double amount) {
		double m = getMagnitude();
		amount += getAngle();
		xLength = m * Math.cos(amount);
		yLength = m * Math.sin(amount);
	}

	/**
	 * Sets the angle of this Vector2D to the given angle in radians. This angle will only be used to set the underlying data of the vector and will not itself be stored in memory, so a call to the
	 * getAngle() method will return an equivalent angle, but not necessarily the same number given here. For example, after a call to setAngle(3*pi), getAngle() will return pi.
	 * 
	 * @param angle the new angle, in radians
	 */
	public void setAngle(double angle) {
		// magnitude can be thought of as the radius, so to get x and y to the right lengths, you just multiply the sin and cos by m
		double m = getMagnitude();
		xLength = m * Math.cos(angle);
		yLength = m * Math.sin(angle);
	}

	/**
	 * Returns true if the values of the given vector equal those of this one. If the two vectors are not literally the same object, this is unlikely to happen because of floating point arithmetic.
	 * Use approximatelyEquals to detect when 2 vectors are about the same.
	 * 
	 * @param other the vector to compare with this one
	 * @return whether the xLength and yLength variables of the two vectors are equal
	 */
	public boolean equals(Vector2D other) {
		return this.xLength == other.xLength && this.yLength == other.yLength;
	}

	/**
	 * Returns whether this vector is approximately equal to another using integer arithmetic instead of double precision.
	 * 
	 * @see #approximatelyEquals(Vector2D, double)
	 * @param other the vector to test
	 * @return whether the given Vector2D can be considered "equal" to this one
	 */
	public boolean approximatelyEquals(Vector2D other) {
		return (int) this.xLength == (int) other.xLength && (int) this.yLength == (int) other.yLength;
	}

	/**
	 * Returns true if the given vector's values are within plus or minus the precision value, else returns false.
	 * 
	 * @param other the vector to test
	 * @param precision how close equal is (in both the positive and negative direction)
	 * @return whether the vectors are close enough to be considered equal
	 */
	public boolean approximatelyEquals(Vector2D other, double precision) {
		return (xLength >= other.xLength - precision && xLength <= other.xLength + precision) && (yLength >= other.yLength - precision && yLength <= other.yLength + precision);
	}

	/**
	 * @return the polar angle of this Vector2D in radians
	 */
	public double getAngle() {
		// acos will only ever return angles between pi and 0, so angles greater than pi have to be manually adjusted. Fortunately, we know whether yLength is positive or negative, so that is easy.
		if (yLength >= 0) {
			return Math.acos(xLength / getMagnitude());
		}
		return Math.PI * 2 - Math.acos(xLength / getMagnitude());
	}

	/**
	 * @return the polar magnitude of this Vector2D
	 */
	public double getMagnitude() {
		// pythagorean theorem at work, bitches!
		return Math.sqrt(xLength * xLength + yLength * yLength);
	}

	/**
	 * @return the cartesian X length of this Vector2D
	 */
	public double xLength() {
		return xLength;
	}

	/**
	 * @return the cartesian Y length of this Vector2D
	 */
	public double yLength() {
		return yLength;
	}
}
