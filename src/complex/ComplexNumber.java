package complex;

/**
 * Represents a complex number using rectangular coordinates.
 */
public class ComplexNumber {
	
	private double real;
	private double imag;
	
	private static double EPSILON = 1e-8;
	
	/**
	 * Creates a new complex number with the given real and imaginary parts.
	 * @param realInit the real part.
	 * @param imagInit the imaginary part.
	 */
	public ComplexNumber(double realInit, double imagInit) {
		real = realInit;
		imag = imagInit;
	}
	/**
	 * Creates a new complex number valued at 0+0i.
	 */
	public ComplexNumber() {
		this(0, 0);
	}
	
	/**
	 * Gets the real part of this complex number.
	 * @return the real part.
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Gets the imaginary part of this complex number.
	 * @return the imaginary part.
	 */
	public double getImaginary() {
		return imag;
	}
	
	/**
	 * Represents this complex number as a string.
	 * @return a string of the form "A±Bi".
	 */
	public String toString() {
		if (imag > 0) {
			return real + "+" + imag + "i";
		} else {
			return real + "-" + -imag + "i";
		}
	}
	
	/**
	 * Adds this complex number and some complex addend.
	 * @param complex the addend.
	 * @return a new complex number valued at the sum of the two numbers.
	 */
	public ComplexNumber plus(ComplexNumber complex) {
		return new ComplexNumber(real + complex.real, imag + complex.imag);
	}

	/**
	 * Multiplies this complex number by some complex factor.
	 * @param complex the factor.
	 * @return a new complex number valued at the product of the two numbers.
	 */
	public ComplexNumber times(ComplexNumber complex) {
		return new ComplexNumber(real * complex.real - imag * complex.imag, real * complex.imag + imag * complex.real);
	}
	
	/**
	 * Multiples this complex number by some real factor.
	 * @param n the scalar.
	 * @return a new complex number valued at this number scaled.
	 */
	public ComplexNumber times(double n) {
		return new ComplexNumber(real * n, imag * n);
	}

	/**
	 * Raises this complex number to some complex power.
	 * @param x the exponent.
	 * @return a new complex number valued at the result of the exponentiation.
	 */
	public ComplexNumber pow(ComplexNumber other) {
		double mod = getModulus();
		double arg = getArgument();
		
		double modNew = Math.pow(mod, other.real) * Math.exp(-other.imag * arg);
		double argNew = other.real * arg + other.imag * Math.log(mod);
		
		return new ComplexNumber(Math.cos(argNew), Math.sin(argNew)).times(modNew);
	}
	
	/**
	 * Raises this complex number to some real power.
	 * @param x the exponent.
	 * @return a new complex number valued at the result of the exponentiation.
	 */
	public ComplexNumber pow(double x) {
		double mod = getModulus();
		double arg = getArgument();
		
		return new ComplexNumber(Math.cos(arg * x), Math.sin(arg * x)).times(Math.pow(mod, x));
	}
	
	/**
	 * Calculates the modulus of this complex number.
	 * @return the modulus.
	 */
	public double getModulus() {
		return Math.sqrt(real * real + imag * imag);
	}

	/**
	 * Calculates the argument of this complex number.
	 * @return the argument.
	 */
	public double getArgument() {
		return Math.atan2(imag, real);
	}
	
	/**
	 * Calculates the conjugate of this complex number.
	 * @return a new complex number that represents the conjugate of this number.
	 */
	public ComplexNumber getConjugate() {
		return new ComplexNumber(real, -imag);
	}
	
	/**
	 * Tests if this complex number is equivalent to another.
	 * @param x the object to compare against.
	 * @return whether they are equivalent.
	 */
	public boolean equals(Object x) {
		if (!(x instanceof ComplexNumber)) return false;
		
		ComplexNumber other = (ComplexNumber)x;
		return Math.abs(real - other.real) < EPSILON && Math.abs(imag - other.imag) < EPSILON;
	}
	
}