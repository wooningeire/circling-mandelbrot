

import complex.*;

import mandel.*;
import processing.core.*;

public class Main extends PApplet {
	
	private static MandelRunner runner;
	
	private static final int N_FRAMES = 240;
	private static final double MODULUS = 2;
	private static int frame = -1;
	
	public static void main(String[] args) {
		PApplet.main("Main");
	}
	
	public void settings() {
		size(720, 480);
	}
	
	public void setup() {
		colorMode(HSB, TAU, 1, 1);
		
		runner = new MandelRunner()
				.app(this)
				
				.nThreads(Math.min(height, 8))
				
				.maxIterations(128)
//				.maxModulus(300)
				
				.origin(new PVector(0, 0))
				.zoom(144)
				
				.onThreadsAllComplete(() -> {
					saveFrame("output/mandel-######.png");
					redraw();
				});
		
		noLoop();
	}
	
	public void draw() {
		if (prepareNextFrame()) {
			runner.run();
		}
	}
	
//	public void mouseClicked() {
//		if (!runner.threadsAllComplete()) return;
//		
//		runner
//				.zoom(2 * runner.zoom())
//				.origin(runner.pointFromPixel(mouseX, mouseY));
//
//		redraw();
//	}
	
	// returns true if not yet done
	private boolean prepareNextFrame() {
		frame++;
		
		if (frame >= N_FRAMES) {
			System.out.println("and we are done");
			return false;
		}
		
		System.out.printf("frame number %s\n", frame);
		
		double theta = 2 * Math.PI * (double)frame / N_FRAMES;
		runner.exponent(new ComplexNumber(MODULUS * Math.cos(theta), MODULUS * Math.sin(theta)));
		
		return true;
	}
	
}
