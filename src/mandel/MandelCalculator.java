package mandel;

import complex.*;
import processing.core.PApplet;
import processing.core.PVector;

class MandelCalculator extends Thread {
	
	private int yStart;
	private int yEnd;
	private MandelRunner runner;
	
	public static float mod(float dend, float dsor) {
		return (dend % dsor + dsor) % dsor;
	}
	
	public MandelCalculator(int yStart, int yEnd, MandelRunner runner) {
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.runner = runner;
	}
	
	public void run() {
		PApplet app = runner.app();
		
		for (int y = yStart; y < yEnd; y++) {
			for (int x = 0; x < app.width; x++) {
				PVector point = runner.pointFromPixel(x, y);
				
				ComplexNumber init = new ComplexNumber(point.x, point.y);
				ComplexNumber varier = new ComplexNumber(point.x, point.y); // z(0)
				
//				int nIterations;
//				for (nIterations = 0; nIterations < runner.maxIterations() && varier.getModulus() < runner.maxModulus(); nIterations++) {
//					varier = varier.pow(runner.exponent()).plus(init);
//				}
//				
//				app.pixels[y * app.width + x] = app.colorFromNIterations(nIterations);
				
				for (int i = 1; i < runner.maxIterations() && Double.isFinite(varier.getModulus()); i++) {
					varier = varier.pow(runner.exponent()).plus(init);
				}
				
				app.pixels[y * app.width + x] = app.color(mod((float)varier.getArgument(), PApplet.TAU), 1, (float)varier.getModulus());
			}
		}
		
		runner.onThreadComplete();
	}
	
}
